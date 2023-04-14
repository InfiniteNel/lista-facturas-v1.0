package com.jroslar.listafacturasv01.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFacturas {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://viewnextandroid.mocklab.io/")
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}