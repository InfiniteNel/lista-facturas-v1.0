package com.jroslar.listafacturasv01.data.network

import com.jroslar.listafacturasv01.core.RetrofitFacturas
import com.jroslar.listafacturasv01.data.model.FacturasModel

class FacturasService {
    private val retrofit = RetrofitFacturas.getRetrofit()

    suspend fun getFaturas(): FacturasModel {
        val response = retrofit.create(FacturasApiClient::class.java).getAllFacturas()
        return response.body()?: FacturasModel(0, emptyList())
    }
}