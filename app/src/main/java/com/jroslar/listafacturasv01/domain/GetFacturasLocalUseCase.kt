package com.jroslar.listafacturasv01.domain

import android.content.Context
import com.jroslar.listafacturasv01.data.FacturasRepository
import com.jroslar.listafacturasv01.data.model.FacturaModel

class GetFacturasLocalUseCase {
    suspend operator fun invoke(context: Context, repository: FacturasRepository): List<FacturaModel> {
        val facturas = repository.getAllFacturasLocal(context)

        return if (facturas.isNotEmpty()) facturas
            else emptyList()
    }
}