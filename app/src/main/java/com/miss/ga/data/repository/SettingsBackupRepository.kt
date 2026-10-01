package com.miss.ga.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.miss.ga.data.db.MisgaDatabaseHelper
import com.miss.ga.data.model.SettingsBackup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "SettingsBackupRepository"

class BackupFormatException(message: String) : Exception(message)

/** Exports and restores every hand-made setting as a single shareable JSON file. */
class SettingsBackupRepository(private val context: Context) {

    private val dbHelper = MisgaDatabaseHelper.getInstance(context)
    private val smsRepository = SmsRepository(context)

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun exportJson(): String = withContext(Dispatchers.IO) {
        val backup = SettingsBackup(
            exportedAt = System.currentTimeMillis(),
            customRules = dbHelper.exportCustomRules(),
            senderPreferences = dbHelper.exportSenderPreferences(),
            predefinedSettings = dbHelper.exportPredefinedSettings(),
            simPicks = smsRepository.allManualSimPicks()
        )
        json.encodeToString(backup)
    }

    /** Replaces all current manual settings with the backup's content. */
    suspend fun restoreJson(jsonText: String) = withContext(Dispatchers.IO) {
        val backup = try {
            json.decodeFromString<SettingsBackup>(jsonText)
        } catch (e: Exception) {
            throw BackupFormatException("This file is not a Misga settings backup.")
        }
        if (backup.formatVersion > SUPPORTED_FORMAT_VERSION) {
            throw BackupFormatException(
                "This backup was made by a newer version of Misga (format ${backup.formatVersion})."
            )
        }
        if (backup.itemCount == 0) {
            throw BackupFormatException("This backup file is empty, so there is nothing to restore.")
        }
        // SIM picks first: a failure here must not leave the database already wiped.
        smsRepository.replaceManualSimPicks(backup.simPicks)
        dbHelper.restoreManualSettings(
            customRules = backup.customRules,
            senderPreferences = backup.senderPreferences,
            predefinedSettings = backup.predefinedSettings
        )
        backup
    }

    /** Writes [jsonText] to app cache and returns a shareable content uri for it. */
    suspend fun writeBackupFile(jsonText: String): Uri = withContext(Dispatchers.IO) {
        val dir = File(context.cacheDir, "backups").apply { mkdirs() }
        // Keep only the newest export so phone numbers don't pile up in cache.
        dir.listFiles()?.forEach { it.delete() }
        val stamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val file = File(dir, "misga-settings-$stamp.json")
        file.writeText(jsonText)
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    fun buildShareIntent(uri: Uri): Intent {
        val send = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Misga settings backup")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        return Intent.createChooser(send, "Save settings backup").apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    suspend fun readText(uri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val stream = context.contentResolver.openInputStream(uri) ?: return@withContext null
            stream.use { input ->
                val bytes = ByteArrayOutputStream()
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var total = 0L
                while (true) {
                    val read = input.read(buffer)
                    if (read <= 0) break
                    total += read
                    if (total > MAX_BACKUP_BYTES) {
                        throw BackupFormatException("Backup file is too large to be valid.")
                    }
                    bytes.write(buffer, 0, read)
                }
                bytes.toString(Charsets.UTF_8.name())
            }
        } catch (e: BackupFormatException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Could not read backup file", e)
            null
        }
    }

    companion object {
        const val SUPPORTED_FORMAT_VERSION = 1
        private const val MAX_BACKUP_BYTES = 8L * 1024 * 1024
    }
}
