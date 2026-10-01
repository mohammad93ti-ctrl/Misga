package com.miss.ga

import com.miss.ga.data.model.BackupPredefinedSetting
import com.miss.ga.data.model.BackupRule
import com.miss.ga.data.model.BackupSenderPreference
import com.miss.ga.data.model.SettingsBackup
import com.miss.ga.data.model.SimPick
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsBackupTest {

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    private val sample = SettingsBackup(
        formatVersion = 1,
        exportedAt = 1_700_000_000_000L,
        customRules = listOf(
            BackupRule(
                name = "My regex",
                pattern = "^1000.*",
                isRegex = true,
                action = "SPAM",
                listType = "BLOCKLIST",
                isEnabled = true,
                category = "CUSTOM",
                senderTarget = null,
                description = "short codes",
                sortOrder = 0,
                createdAt = 1_699_000_000_000L
            ),
            BackupRule(
                name = "Per sender",
                pattern = "09121234567",
                isRegex = false,
                action = "SPAM",
                listType = "BLOCKLIST",
                isEnabled = false,
                category = "CUSTOM",
                senderTarget = "989121234567",
                description = "",
                sortOrder = 1,
                createdAt = 1_699_000_000_001L
            )
        ),
        senderPreferences = listOf(
            BackupSenderPreference(
                address = "989121234567",
                displayName = "Spammer",
                defaultAction = "SPAM",
                isBlocked = true,
                notes = "keeps texting",
                updatedAt = 1_699_500_000_000L
            )
        ),
        predefinedSettings = listOf(
            BackupPredefinedSetting(
                ruleId = -3,
                action = "SPAM",
                isEnabled = false,
                isDeleted = true
            )
        ),
        simPicks = listOf(
            SimPick(address = "989121234567", subscriptionId = 2, pickedAtMessageId = 55L)
        )
    )

    @Test
    fun roundTripKeepsEveryManualSetting() {
        val restored = json.decodeFromString<SettingsBackup>(json.encodeToString(sample))

        assertEquals(sample.customRules, restored.customRules)
        assertEquals(sample.senderPreferences, restored.senderPreferences)
        assertEquals(sample.predefinedSettings, restored.predefinedSettings)
        assertEquals(sample.simPicks, restored.simPicks)
        assertEquals(sample.exportedAt, restored.exportedAt)
    }

    @Test
    fun itemCountCoversAllSections() {
        assertEquals(5, sample.itemCount)
    }

    @Test
    fun missingSectionsFallBackToEmptySoOlderBackupsStillLoad() {
        val minimal = json.decodeFromString<SettingsBackup>("""{"formatVersion":1}""")

        assertTrue(minimal.customRules.isEmpty())
        assertTrue(minimal.senderPreferences.isEmpty())
        assertTrue(minimal.predefinedSettings.isEmpty())
        assertTrue(minimal.simPicks.isEmpty())
    }

    @Test
    fun unknownFutureFieldsAreIgnored() {
        val text = """{"formatVersion":1,"somethingNew":true,"customRules":[]}"""

        assertEquals(1, json.decodeFromString<SettingsBackup>(text).formatVersion)
    }
}
