package com.miss.ga

import com.miss.ga.data.model.ReplySimDefaults
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ReplySimDefaultsTest {

    @Test
    fun usesReceivingSimWhenNoManualPickExists() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = null,
            manualPickedAtMessageId = 0L,
            latestInboundMessageId = 42L,
            latestInboundSubscriptionId = 2
        )

        assertEquals(2, resolved)
    }

    @Test
    fun keepsManualPickWhileNoNewerInboundArrived() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = 1,
            manualPickedAtMessageId = 42L,
            latestInboundMessageId = 42L,
            latestInboundSubscriptionId = 2
        )

        assertEquals(1, resolved)
    }

    @Test
    fun dropsManualPickWhenNewerInboundArrived() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = 1,
            manualPickedAtMessageId = 42L,
            latestInboundMessageId = 43L,
            latestInboundSubscriptionId = 2
        )

        assertEquals(2, resolved)
    }

    @Test
    fun keepsManualPickWhenLatestInboundIsUnknown() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = 1,
            manualPickedAtMessageId = 42L,
            latestInboundMessageId = 0L,
            latestInboundSubscriptionId = null
        )

        assertEquals(1, resolved)
    }

    @Test
    fun keepsPickMadeBeforeAnyInboundArrived() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = 1,
            manualPickedAtMessageId = 0L,
            latestInboundMessageId = 0L,
            latestInboundSubscriptionId = null
        )

        assertEquals(1, resolved)
    }

    @Test
    fun dropsPickMadeBeforeAnyInboundOnceFirstMessageArrives() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = 1,
            manualPickedAtMessageId = 0L,
            latestInboundMessageId = 7L,
            latestInboundSubscriptionId = 2
        )

        assertEquals(2, resolved)
    }

    @Test
    fun dropsPickPointingAtRemovedSim() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = 1,
            manualPickedAtMessageId = 42L,
            latestInboundMessageId = 42L,
            latestInboundSubscriptionId = 2,
            activeSubscriptionIds = setOf(2, 3)
        )

        assertEquals(2, resolved)
    }

    @Test
    fun fallsBackToSystemDefaultWhenNothingIsKnown() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = null,
            manualPickedAtMessageId = 0L,
            latestInboundMessageId = 0L,
            latestInboundSubscriptionId = null
        )

        assertNull(resolved)
    }

    @Test
    fun dropsReceivingSimThatIsNoLongerActive() {
        val resolved = ReplySimDefaults.resolve(
            manualSubscriptionId = null,
            manualPickedAtMessageId = 0L,
            latestInboundMessageId = 42L,
            latestInboundSubscriptionId = 9,
            activeSubscriptionIds = setOf(2)
        )

        assertNull(resolved)
    }
}
