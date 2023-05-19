package com.siasg.comprasnet.di

import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.ContratosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComprasApiService {

    @GET("comprasContratos/v1/contratos.json")
    fun getTodosContratos(): Call<ContratosResponse>

    @GET("comprasContratos/doc/contrato/{id}.json")
    fun getSingleContratoID(@Path("id") id: String): Call<Contrato>

    @GET("comprasContratos/v1/contratos.json")
    fun getContratosByDateMinMax(
        @Query("vigencia_fim_min") date_min: String,
        @Query("vigencia_fim_max") date_max: String
    ): Call<ContratosResponse>

    @GET("comprasContratos/v1/contratos.json")
    fun getContratosByDateMin(
        @Query("vigencia_fim_min") date_min: String
    ): Call<ContratosResponse>

    @GET("comprasContratos/v1/contratos.json")
    fun getContratosByDateMax(
        @Query("vigencia_fim_max") date_max: String
    ): Call<ContratosResponse>

    @GET("comprasContratos/v1/contratos.json")
    fun getContratosByObjeto(
        @Query("objeto") search: String
    ): Call<ContratosResponse>

}