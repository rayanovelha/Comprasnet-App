package com.siasg.comprasnet.domain

import kotlinx.serialization.Serializable

@Serializable
data class Embedded(
    val contratos: List<Contrato>
)