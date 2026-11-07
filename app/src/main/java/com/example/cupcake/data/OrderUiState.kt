package com.example.cupcake.data

/**
 * Merepresentasikan opsi komponen komputer individual (nama dan harga).
 */
data class ComponentOption(
    val name: String,
    val price: Long
)

/**
 * Menyimpan seluruh state konfigurasi komputer saat ini yang dipilih oleh pengguna,
 * termasuk jenis perangkat, komponen terpilih, dan total estimasi harga.
 */
data class OrderUiState(
    val deviceType: String = "",
    val basePrice: Long = 0L,
    val processor: ComponentOption? = null,
    val ram: ComponentOption? = null,
    val storageType: String = "SSD NVMe", // Tipe penyimpanan aktif: HDD, SSD SATA, SSD NVMe
    val storage: ComponentOption? = null,
    val monitor: ComponentOption? = null,
    val graphicsCard: ComponentOption? = null,
    val totalPrice: Long = 0L
)
