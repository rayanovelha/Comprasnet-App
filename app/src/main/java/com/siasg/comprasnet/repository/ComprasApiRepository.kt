package com.siasg.comprasnet.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siasg.comprasnet.di.ComprasApiService
import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.ContratosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComprasApiRepository @Inject constructor() {

    @Inject
    lateinit var comprasApiService: ComprasApiService

    private val _singleContrato = MutableLiveData<Contrato>()
    val singleContrato: LiveData<Contrato> = _singleContrato

    private val _contratos = MutableLiveData<Resource<ContratosResponse?>>()
    val contratos: LiveData<Resource<ContratosResponse?>> = _contratos

    suspend fun fetchTodosContratos(): LiveData<Resource<ContratosResponse?>> {

        val response = withContext(Dispatchers.IO) {
            comprasApiService.getTodosContratos().execute()
        }

        if (response.isSuccessful) {
            val contratosResponse = response.body()
            _contratos.postValue(Resource(dado = contratosResponse))
        } else {
            val resourceAtual = _contratos.value
            val resourceNovo = resourceFalha<ContratosResponse?>(resourceAtual, response.errorBody().toString())
            _contratos.postValue(resourceNovo)
            Log.e("ComprasApiRepository", "fetchTodosContratos: ${response.errorBody()}")
        }

        return contratos
    }

    suspend fun fetchContratosByDateMinMax(date_min: String, date_max: String): LiveData<Resource<ContratosResponse?>> {

        val response = withContext(Dispatchers.IO) {
            comprasApiService.getContratosByDateMinMax(date_min, date_max).execute()
        }

        if (response.isSuccessful) {
            val contratosResponse = response.body()
            _contratos.postValue(Resource(dado = contratosResponse))
        } else {
            val resourceAtual = _contratos.value
            val resourceNovo = resourceFalha<ContratosResponse?>(resourceAtual, response.errorBody().toString())
            _contratos.postValue(resourceNovo)
            Log.e("ComprasApiRepository", "fetchContratosByDateMinMax: ${response.errorBody()}")
        }

        return contratos
    }

    suspend fun fetchContratosByDateMin(date_min: String): LiveData<Resource<ContratosResponse?>> {

        val response = withContext(Dispatchers.IO) {
            comprasApiService.getContratosByDateMin(date_min).execute()
        }

        if (response.isSuccessful) {
            val contratosResponse = response.body()
            _contratos.postValue(Resource(dado = contratosResponse))
        } else {
            val resourceAtual = _contratos.value
            val resourceNovo = resourceFalha<ContratosResponse?>(resourceAtual, response.errorBody().toString())
            _contratos.postValue(resourceNovo)
            Log.e("ComprasApiRepository", "fetchContratosByDateMin: ${response.errorBody()}")
        }

        return contratos
    }

    suspend fun fetchContratosByDateMax(date_max: String): LiveData<Resource<ContratosResponse?>> {

        val response = withContext(Dispatchers.IO) {
            comprasApiService.getContratosByDateMax(date_max).execute()
        }

        if (response.isSuccessful) {
            val contratosResponse = response.body()
            _contratos.postValue(Resource(dado = contratosResponse))
        } else {
            val resourceAtual = _contratos.value
            val resourceNovo = resourceFalha<ContratosResponse?>(resourceAtual, response.errorBody().toString())
            _contratos.postValue(resourceNovo)
            Log.e("ComprasApiRepository", "fetchContratosByDateMax: ${response.errorBody()}")
        }

        return contratos
    }

    suspend fun fetchContratosByObjeto(search: String): LiveData<Resource<ContratosResponse?>> {

        val response = withContext(Dispatchers.IO) {
            comprasApiService.getContratosByObjeto(search).execute()
        }

        if (response.isSuccessful) {
            val contratosResponse = response.body()
            _contratos.postValue(Resource(dado = contratosResponse))
        } else {
            val resourceAtual = _contratos.value
            val resourceNovo = resourceFalha<ContratosResponse?>(resourceAtual, response.errorBody().toString())
            _contratos.postValue(resourceNovo)
            Log.e("ComprasApiRepository", "fetchContratosByObjeto: ${response.errorBody()}")
        }

        return contratos
    }

}