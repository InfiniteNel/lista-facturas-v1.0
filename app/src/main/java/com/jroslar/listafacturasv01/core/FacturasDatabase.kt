package com.jroslar.listafacturasv01.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jroslar.listafacturasv01.data.dao.FacturasDao
import com.jroslar.listafacturasv01.data.model.FacturaModel

@Database(entities = [FacturaModel::class], version = 2, exportSchema = false)
abstract class FacturasDatabase: RoomDatabase() {
    abstract fun facturaDao(): FacturasDao

    companion object {

        @Volatile
        private var INSTANCE: FacturasDatabase? = null

        fun getInstance(context: Context): FacturasDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FacturasDatabase::class.java,
                        "facturas_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}