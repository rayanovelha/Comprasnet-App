package com.siasg.comprasnet.domain

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostgrestMessage(
    val uid: String,
    @SerialName("created_at")
    val createdAt: Instant?,
    @SerialName("favorites")
    val favorites: String,
    @SerialName("name")
    val name: String,
)