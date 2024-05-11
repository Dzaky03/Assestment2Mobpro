package org.d3if3022.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3022.mobpro1.database.BarangDao
import org.d3if3022.mobpro1.model.Barang

class DetailViewModel(private val dao: BarangDao) : ViewModel() {

    fun insert(
        nama: String,
        jenis: String,
        jumlah: String,
        harga: String,
        ukuran: String,
        deskripsi: String
    ) {
        val barang = Barang(
            nama = nama,
            jenis = jenis,
            jumlah = jumlah,
            harga = harga,
            ukuran = ukuran,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(barang)
        }
    }

    suspend fun getBarang(id: Long): Barang? {
        return dao.getBarangById(id)
    }

    fun update(
        id: Long,
        nama: String,
        jenis: String,
        jumlah: String,
        harga: String,
        ukuran: String,
        deskripsi: String
    ) {
        val barang = Barang(
            id = id,
            nama = nama,
            jenis = jenis,
            jumlah = jumlah,
            harga = harga,
            ukuran = ukuran,
            deskripsi = deskripsi
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(barang)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }


}