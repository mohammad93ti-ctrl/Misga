package com.miss.ga.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.miss.ga.data.model.SimOption

/**
 * Zero-width dual-SIM indicator living inside the message field's trailing slot
 * (Textra-style). Tap instantly cycles SIMs; always shows the active line.
 * Renders nothing on single-SIM devices.
 */
@Composable
fun SimFieldIndicator(
    sims: List<SimOption>,
    selectedId: Int?,
    onCycle: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (sims.size < 2) return
    val current = sims.find { it.subscriptionId == selectedId } ?: sims.first()
    val description = "Send with SIM ${current.slotIndex + 1}" +
        (current.carrierName?.takeIf { it.isNotBlank() }?.let { ", $it" } ?: "") +
        ". Tap to switch SIM."
    Text(
        text = "SIM ${current.slotLabel}",
        style = MaterialTheme.typography.labelMedium.copy(textDirection = TextDirection.Ltr),
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .semantics { contentDescription = description }
            .clickable(onClick = onCycle)
            .padding(horizontal = 4.dp, vertical = 12.dp)
            .alpha(0.9f)
    )
}

/** Next SIM id after [selectedId], wrapping around. */
fun nextSimId(sims: List<SimOption>, selectedId: Int?): Int? {
    if (sims.isEmpty()) return null
    val idx = sims.indexOfFirst { it.subscriptionId == selectedId }
    return sims[(idx + 1) % sims.size].subscriptionId
}
