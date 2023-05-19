package com.siasg.comprasnet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.FavoriteContrato
import com.siasg.comprasnet.repository.FavoriteContratosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteContratosViewModel @Inject constructor(
    private val repository: FavoriteContratosRepository
) : ViewModel() {

    private val _favoriteContratos = MutableLiveData<List<FavoriteContrato>>()
    val favoriteContratos: LiveData<List<FavoriteContrato>> = _favoriteContratos

    fun addFavoriteContrato(contrato: Contrato) {
        viewModelScope.launch {
            repository.addFavoriteContrato(contrato)
        }
    }

    fun getAllFavoriteContrato() {
        viewModelScope.launch {
            val favoriteContratos = repository.getAllFavoriteContrato()
            _favoriteContratos.value = favoriteContratos
        }
    }

    fun removeFavoriteContrato(contrato: Contrato) {
        viewModelScope.launch {
            repository.removeFavoriteContrato(contrato)
        }
    }

    suspend fun isFavoriteContrato(contrato: Contrato): Boolean {
        return repository.isFavoriteContrato(contrato)
    }

    fun cleanFavoriteContrato() {
        viewModelScope.launch {
            repository.cleanFavoriteContrato()
        }
    }
}