package com.siasg.comprasnet.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_contratos")
data class FavoriteContrato(
    @PrimaryKey val id: String,
    val processo: String
)