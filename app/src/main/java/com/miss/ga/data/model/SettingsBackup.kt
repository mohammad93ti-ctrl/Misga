package com.miss.ga.data.model

import kotlinx.serialization.Serializable

/**
 * Complete snapshot of everything the user configured by hand, so a restore
 * reproduces the app's settings exactly as they were at export time.
 *
 * Shipped predefined rules are not listed: they live in code and are re-seeded
 * on first database open. Only the user's deviations from them
 * ([BackupPredefinedSetting]) are stored.
 */
@Serializable
data class SettingsBackup(
    val formatVersion: Int = 1,
    val exportedAt: Long = 0,
    val customRules: List<BackupRule> = emptyList(),
    val senderPreferences: List<BackupSenderPreference> = emptyList(),
    val predefinedSettings: List<BackupPredefinedSetting> = emptyList(),
    val simPicks: List<SimPick> = emptyList()
) {
    val itemCount: Int
        get() = customRules.size + senderPreferences.size + predefinedSettings.size + simPicks.size
}

/** A row of `filter_rules`: a rule the user wrote by hand, global or aimed at one sender. */
@Serializable
data class BackupRule(
    val name: String,
    val pattern: String,
    val isRegex: Boolean = true,
    val action: String,
    val listType: String,
    val isEnabled: Boolean = true,
    val category: String,
    val senderTarget: String? = null,
    val description: String = "",
    val sortOrder: Int = 0,
    val createdAt: Long = 0
)

/** A row of `sender_preferences`: a number the user manually marked as spam or blocked. */
@Serializable
data class BackupSenderPreference(
    val address: String,
    val displayName: String? = null,
    val defaultAction: String,
    val customSoundUri: String? = null,
    val isBlocked: Boolean = false,
    val notes: String = "",
    val updatedAt: Long = 0
)

/** A row of `predefined_rule_settings`: the user's on/off/edit/delete state of a shipped rule. */
@Serializable
data class BackupPredefinedSetting(
    val ruleId: Long,
    val name: String? = null,
    val pattern: String? = null,
    val isRegex: Boolean? = null,
    val action: String,
    val listType: String? = null,
    val isEnabled: Boolean = true,
    val isDeleted: Boolean = false,
    val description: String? = null,
    val isCustomized: Boolean = false
)
