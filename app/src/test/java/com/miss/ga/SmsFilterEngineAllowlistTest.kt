package com.miss.ga

import com.miss.ga.data.model.FilterAction
import com.miss.ga.data.model.FilterRule
import com.miss.ga.data.model.RuleCategory
import com.miss.ga.data.model.RuleListType
import com.miss.ga.engine.SmsFilterEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Engine behavior the per-sender allowlist toggle relies on:
 * a sender-targeted ALLOWLIST rule must bypass every keyword blocklist
 * (custom global and predefined Iranian rules) and deliver as NORMAL.
 */
class SmsFilterEngineAllowlistTest {

    private val predefinedBlock = FilterRule(
        id = 1,
        name = "Persian Spam Keyword",
        pattern = "تخفیف|حراج",
        isRegex = true,
        action = FilterAction.SPAM,
        listType = RuleListType.BLOCKLIST,
        category = RuleCategory.SPAM_KEYWORD,
        isPredefined = true
    )

    private val globalKeywordBlock = FilterRule(
        id = 2,
        name = "Custom Keyword",
        pattern = "优惠",
        isRegex = false,
        action = FilterAction.SPAM,
        listType = RuleListType.BLOCKLIST,
        category = RuleCategory.CUSTOM
    )

    private fun allowlistRuleFor(sender: String) = FilterRule(
        id = 3,
        name = "Allowlist $sender",
        pattern = ".*",
        isRegex = true,
        action = FilterAction.NORMAL,
        listType = RuleListType.ALLOWLIST,
        category = RuleCategory.CUSTOM_ALLOWLIST,
        senderTarget = sender
    )

    @Test
    fun senderAllowlistRuleBypassesKeywordRules() {
        val rules = listOf(predefinedBlock, globalKeywordBlock, allowlistRuleFor("09121112233"))
        val result = SmsFilterEngine.evaluateMessage(
            sender = "09121112233",
            body = "تخفیف ویژه آخر هفته",
            rules = rules
        )
        assertEquals(FilterAction.NORMAL, result.action)
        assertTrue(result.isAllowlisted)
    }

    @Test
    fun allowlistOnlyAppliesToItsTargetedSender() {
        val rules = listOf(predefinedBlock, allowlistRuleFor("09121112233"))
        val result = SmsFilterEngine.evaluateMessage(
            sender = "09332221100",
            body = "تخفیف ویژه آخر هفته",
            rules = rules
        )
        assertEquals(FilterAction.SPAM, result.action)
        assertFalse(result.isAllowlisted)
    }

    @Test
    fun disabledAllowlistRuleDoesNotProtect() {
        val rules = listOf(predefinedBlock, allowlistRuleFor("09121112233").copy(isEnabled = false))
        val result = SmsFilterEngine.evaluateMessage(
            sender = "09121112233",
            body = "تخفیف ویژه آخر هفته",
            rules = rules
        )
        assertEquals(FilterAction.SPAM, result.action)
        assertFalse(result.isAllowlisted)
    }
}
