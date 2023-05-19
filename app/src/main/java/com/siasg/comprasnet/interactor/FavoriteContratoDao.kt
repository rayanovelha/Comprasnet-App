package com.siasg.comprasnet.interactor

import androidx.room.*
import com.siasg.comprasnet.domain.FavoriteContrato

@Dao
interface FavoriteContratoDao {

    @Query("SELECT * FROM favorite_contratos")
    suspend fun getAll(): List<FavoriteContrato>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteContrato: FavoriteContrato)

    @Delete
    suspend fun delete(favoriteContrato: FavoriteContrato)

    @Query("SELECT * FROM favorite_contratos WHERE id = :id")
    suspend fun isFavorite(id: String): FavoriteContrato?

    @Query("DELETE FROM favorite_contratos")
    suspend fun deleteAll()
}