package com.miss.ga.data.model

/**
 * Pure decision logic for which SIM card a reply should be sent from.
 *
 * Rule: the default is the SIM that received the newest inbound message of the
 * conversation. A manual pick wins, but only until a newer inbound message
 * arrives (message provider ids increase monotonically, so comparing the id
 * captured at pick time against the newest inbound id is exact).
 *
 * A [manualPickedAtMessageId] of 0 means the pick was made when the conversation
 * had no inbound message yet; it stays valid until the first message arrives.
 *
 * An empty [activeSubscriptionIds] means "unknown" and disables validation.
 */
object ReplySimDefaults {

    fun resolve(
        manualSubscriptionId: Int?,
        manualPickedAtMessageId: Long,
        latestInboundMessageId: Long,
        latestInboundSubscriptionId: Int?,
        activeSubscriptionIds: Set<Int> = emptySet()
    ): Int? {
        val manual = manualSubscriptionId?.takeIf { id ->
            (latestInboundMessageId <= 0L || latestInboundMessageId <= manualPickedAtMessageId) &&
                isActive(id, activeSubscriptionIds)
        }
        if (manual != null) return manual

        return latestInboundSubscriptionId?.takeIf { isActive(it, activeSubscriptionIds) }
    }

    private fun isActive(id: Int, activeSubscriptionIds: Set<Int>): Boolean =
        activeSubscriptionIds.isEmpty() || id in activeSubscriptionIds
}
