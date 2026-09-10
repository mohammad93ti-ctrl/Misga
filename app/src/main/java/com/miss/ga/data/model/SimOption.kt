package com.miss.ga.data.model

/**
 * One send-capable SIM, resolved from SubscriptionManager.
 * subscriptionId is the stable Telephony subId; slotIndex is 0-based (shown as 1/2).
 */
data class SimOption(
    val subscriptionId: Int,
    val slotIndex: Int,
    val displayName: String,
    val carrierName: String? = null
) {
    val slotLabel: String get() = (slotIndex + 1).toString()
}
