package org.d3if3022.mobpro1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3022.mobpro1.model.Barang


@Dao
interface BarangDao {
    @Insert
    suspend fun insert(barang: Barang)

    @Update
    suspend fun update(barang: Barang)

    @Query("SELECT * FROM barang ORDER BY deskripsi, ukuran, harga, jumlah, jenis, nama ASC")
    fun getBarang(): Flow<List<Barang>>

    @Query("SELECT * FROM barang WHERE id = :id")
    suspend fun getBarangById(id: Long): Barang?

    @Query("DELETE FROM barang WHERE id = :id")
    suspend fun deleteById(id: Long)
}