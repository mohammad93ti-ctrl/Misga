package com.miss.ga.data.util

/**
 * Builds equivalent phone-number keys so Iranian numbers like
 * `+98912…`, `0912…`, and `912…` can be matched without a
 * ContactsContract lookup per conversation.
 */
object PhoneNumberKeys {
    fun digitsOnly(number: String): String = number.filter { it.isDigit() }

    /**
     * Stable lookup key for sender prefs / sender-targeted rules.
     * Iranian mobiles (`+98912…` / `0912…` / `912…`) share `98` + 10-digit national.
     * Anything else (shortcodes like `100065`/`+98100065`, landlines) is reduced to
     * the trunk-stripped national form so carrier `+98`/`0` prefixes can't split
     * one sender into two identities.
     */
    fun canonical(address: String): String {
        val cleaned = address.trim()
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
        val digits = digitsOnly(address)
        if (digits.isEmpty()) return cleaned

        val national = when {
            digits.startsWith("98") && digits.length > 2 -> digits.substring(2)
            digits.startsWith("0") && digits.length > 1 -> digits.substring(1)
            else -> digits
        }
        if (national.length == 10 && national.startsWith("9")) {
            return "98$national"
        }
        return national
    }

    fun keys(number: String): Set<String> {
        val digits = digitsOnly(number)
        if (digits.isEmpty()) return emptySet()

        val keys = mutableSetOf(digits)
        if (digits.startsWith("98") && digits.length > 10) {
            val national = digits.substring(2)
            keys.add(national)
            keys.add("0$national")
        } else if (digits.startsWith("98") && digits.length > 2) {
            // Shortcode with country prefix (`+98100065`): also match the bare
            // (`100065`) and trunk (`0100065`) spellings, not just mobiles.
            val national = digits.substring(2)
            if (national.length >= 4) {
                keys.add(national)
                keys.add("0$national")
            }
        }
        if (digits.startsWith("0") && digits.length > 1) {
            val withoutTrunk = digits.substring(1)
            keys.add(withoutTrunk)
            keys.add("98$withoutTrunk")
        }
        if (!digits.startsWith("98") && !digits.startsWith("0") && digits.length >= 4) {
            // Bare form (`100065`, mobile national `912…`, landline national):
            // also match the `+98`/`0` prefixed spellings of the same sender.
            keys.add("98$digits")
            keys.add("0$digits")
        }
        if (digits.length >= 10) {
            keys.add(digits.takeLast(10))
        }
        return keys
    }

    fun lookup(map: Map<String, String>, number: String): String? {
        for (key in keys(number)) {
            val name = map[key]
            if (!name.isNullOrBlank()) return name
        }
        return null
    }

    fun redact(address: String): String {
        if (address.length <= 4) return "****"
        return address.take(2) + "****" + address.takeLast(2)
    }
}
