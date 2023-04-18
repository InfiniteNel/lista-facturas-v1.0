package com.jroslar.listafacturasv01.domain

import android.content.Context
import com.jroslar.listafacturasv01.data.FacturasRepository
import com.jroslar.listafacturasv01.data.model.FacturaModel

class GetFacturasFromApiUseCase {
    suspend operator fun invoke(context: Context): List<FacturaModel> {
        var facturas = FacturasRepository.getAllFacturasFromApi()

        return if (facturas.isNotEmpty()) {
            FacturasRepository.clearFacturas(context)
            FacturasRepository.insertFacturas(context, facturas)
            facturas
        } else FacturasRepository.getAllFacturasLocal(context)
    }
}