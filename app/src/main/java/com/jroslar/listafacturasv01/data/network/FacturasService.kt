package com.jroslar.listafacturasv01.data.network

import com.jroslar.listafacturasv01.core.RetrofitFacturas
import com.jroslar.listafacturasv01.data.model.FacturasModel

class FacturasService {
    private val serverOn = true
    private val retrofit = RetrofitFacturas.getRetrofit()
    private val retromock = RetrofitFacturas.getRetromock()

    suspend fun getFaturas(): FacturasModel {
        return if (serverOn) {
            val response = retrofit.create(FacturasApiClient::class.java).getAllFacturas()
            response.body()?: FacturasModel(0, emptyList())
        } else {
            val response = retromock.create(FacturasApiClient::class.java).getAllFacturas()
            response.body()?: FacturasModel(0, emptyList())
        }
    }
}