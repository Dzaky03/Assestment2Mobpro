package org.d3if3022.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val nim: String,
    val harga: String,
    val kelas: String
)