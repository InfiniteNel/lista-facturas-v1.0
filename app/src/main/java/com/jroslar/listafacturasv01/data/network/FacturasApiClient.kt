package com.jroslar.listafacturasv01.data.network

import com.jroslar.listafacturasv01.data.model.FacturasModel
import retrofit2.Response
import retrofit2.http.GET

interface FacturasApiClient {
    @GET("facturas")
    suspend fun getAllFacturas(): Response<FacturasModel>
}