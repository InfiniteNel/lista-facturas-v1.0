package com.jroslar.listafacturasv01.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jroslar.listafacturasv01.data.model.FacturaModel

@Dao
interface FacturasDao {
    @Insert
    fun insertFacturas(facturas: List<FacturaModel>)
    @Query("DELETE from factura_entity")
    fun clearDataBase()
    @Query("SELECT * from factura_entity")
    fun getFacturas(): List<FacturaModel>
}