package org.d3if3022.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val jenis: String,
    val jumlah: String,
    val harga: String,
    val ukuran: String,
    val deskripsi: String
)