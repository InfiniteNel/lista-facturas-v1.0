package com.jroslar.listafacturasv01.data

import android.content.Context
import com.jroslar.listafacturasv01.core.FacturasDatabase
import com.jroslar.listafacturasv01.data.model.FacturaModel
import com.jroslar.listafacturasv01.data.network.FacturasService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FacturasRepository {
    val facturasService = FacturasService()
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    suspend fun getAllFacturas(context: Context): List<FacturaModel> {
        return withContext(dispatcherIO) {
            val result = facturasService.getFaturas()

            FacturasDatabase.getInstance(context).facturaDao().clearDataBase()
            FacturasDatabase.getInstance(context).facturaDao().insertFacturas(result.facturas)

            result.facturas
        }
    }

    suspend fun getFacturas(context: Context): List<FacturaModel> {
        return  withContext(dispatcherIO) {
            FacturasDatabase.getInstance(context).facturaDao().getFacturas()
        }
    }
}