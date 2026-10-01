package com.miss.ga.data.model

import kotlinx.serialization.Serializable

/** A conversation's manually chosen reply SIM, valid until [pickedAtMessageId] is superseded. */
@Serializable
data class SimPick(
    val address: String,
    val subscriptionId: Int,
    val pickedAtMessageId: Long = 0L
)
