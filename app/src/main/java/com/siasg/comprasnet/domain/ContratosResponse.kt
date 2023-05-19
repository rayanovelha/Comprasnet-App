package com.siasg.comprasnet.domain

import kotlinx.serialization.Serializable

@Serializable
data class ContratosResponse(
    val _embedded: Embedded
)