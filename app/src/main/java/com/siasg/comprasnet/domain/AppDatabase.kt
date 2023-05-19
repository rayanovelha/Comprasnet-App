package com.siasg.comprasnet.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siasg.comprasnet.interactor.FavoriteContratoDao

@Database(entities = [FavoriteContrato::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteContratoDao(): FavoriteContratoDao
}