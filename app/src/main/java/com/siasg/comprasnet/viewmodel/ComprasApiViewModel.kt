package com.siasg.comprasnet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.siasg.comprasnet.domain.ContratosResponse
import com.siasg.comprasnet.repository.ComprasApiRepository
import com.siasg.comprasnet.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComprasApiViewModel @Inject constructor(
    private val repository: ComprasApiRepository
) : ViewModel() {

    suspend fun fetchTodosContratos(): LiveData<Resource<ContratosResponse?>> {
        return repository.fetchTodosContratos()
    }

    suspend fun fetchContratosByDateMinMax(date_min: String, date_max: String): LiveData<Resource<ContratosResponse?>> {
        return repository.fetchContratosByDateMinMax(date_min, date_max)
    }

    suspend fun fetchContratosByDateMin(date_min: String): LiveData<Resource<ContratosResponse?>> {
        return repository.fetchContratosByDateMin(date_min)
    }

    suspend fun fetchContratosByDateMax(date_max: String): LiveData<Resource<ContratosResponse?>> {
        return repository.fetchContratosByDateMax(date_max)
    }

    suspend fun fetchContratosByObjeto(search: String): LiveData<Resource<ContratosResponse?>> {
        return repository.fetchContratosByObjeto(search)
    }

}