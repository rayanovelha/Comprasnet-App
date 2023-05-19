package com.siasg.comprasnet.repository

import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.FavoriteContrato
import com.siasg.comprasnet.interactor.FavoriteContratoDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteContratosRepository @Inject constructor (
    private val favoriteContratoDao: FavoriteContratoDao
    ) {
        suspend fun addFavoriteContrato(contrato: Contrato){
            val favoriteContrato = FavoriteContrato(contrato.id.toString(), contrato.processo)
            favoriteContratoDao.insert(favoriteContrato)
        }
        suspend fun getAllFavoriteContrato(): List<FavoriteContrato>{
            return favoriteContratoDao.getAll()
        }
        suspend fun removeFavoriteContrato(contrato: Contrato){
            val favoriteContrato = FavoriteContrato(contrato.id.toString(), contrato.processo)
            favoriteContratoDao.delete(favoriteContrato)
        }
        suspend fun isFavoriteContrato(contrato: Contrato): Boolean{
            return favoriteContratoDao.isFavorite(contrato.id.toString()) != null
        }

        suspend fun cleanFavoriteContrato() {
            favoriteContratoDao.deleteAll()
        }
}