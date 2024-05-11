package org.d3if3022.mobpro1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3022.mobpro1.model.Barang


@Database(entities = [Barang::class], version = 1, exportSchema = false)
abstract class BarangDb: RoomDatabase() {
    abstract val dao: BarangDao

    companion object{
        @Volatile
        private var INSTANCE: BarangDb? = null

        fun getInstance(context: Context): BarangDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BarangDb::class.java,
                        "barang.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}