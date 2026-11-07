package com.example.cupcake.data

/**
 * Sumber data (DataSource) statis yang menyediakan data pilihan perangkat,
 * opsi komponen hardware (Prosesor, RAM, Penyimpanan, Monitor, GPU),
 * serta harga dasar masing-masing perangkat.
 */
object DataSource {

    // Harga dasar perangkat (Base Price)
    const val BASE_PRICE_DESKTOP = 3000000L
    const val BASE_PRICE_NOTEBOOK = 4000000L

    // Pilihan jenis perangkat utama
    val deviceOptions = listOf(
        "Desktop PC",
        "Notebook"
    )

    // Daftar pilihan prosesor dan tambahan harganya
    val processorOptions = listOf(
        ComponentOption("Intel Core i3", 1200000L),
        ComponentOption("Intel Core i5", 2200000L),
        ComponentOption("Intel Core i7", 3800000L),
        ComponentOption("AMD Ryzen 5", 2000000L),
        ComponentOption("AMD Ryzen 7", 3500000L)
    )

    // Daftar pilihan kapasitas RAM dan tambahan harganya
    val ramOptions = listOf(
        ComponentOption("8 GB", 500000L),
        ComponentOption("16 GB", 950000L),
        ComponentOption("32 GB", 1900000L)
    )

    // Kategori tipe penyimpanan
    val storageTypes = listOf("HDD", "SSD SATA", "SSD NVMe")

    // Peta pilihan kapasitas penyimpanan berdasarkan tipenya
    val storageOptionsByType = mapOf(
        "HDD" to listOf(
            ComponentOption("500 GB", 300000L),
            ComponentOption("1 TB", 550000L),
            ComponentOption("2 TB", 1000000L)
        ),
        "SSD SATA" to listOf(
            ComponentOption("256 GB", 350000L),
            ComponentOption("512 GB", 650000L),
            ComponentOption("1 TB", 1200000L)
        ),
        "SSD NVMe" to listOf(
            ComponentOption("256 GB", 450000L),
            ComponentOption("512 GB", 850000L),
            ComponentOption("1 TB", 1500000L),
            ComponentOption("2 TB", 2800000L)
        )
    )

    // Pilihan monitor khusus untuk Desktop PC
    val desktopMonitorOptions = listOf(
        ComponentOption("Monitor 19 inci", 900000L),
        ComponentOption("Monitor 22 inci", 1300000L),
        ComponentOption("Monitor 24 inci", 1800000L),
        ComponentOption("Monitor 27 inci", 2700000L)
    )

    // Pilihan ukuran layar untuk Notebook
    val notebookMonitorOptions = listOf(
        ComponentOption("Layar 14 inci", 0L),
        ComponentOption("Layar 15.6 inci", 400000L),
        ComponentOption("Layar 16 inci", 900000L),
        ComponentOption("Layar 17 inci", 1500000L)
    )

    // Pilihan kartu grafis (GPU)
    val graphicsCardOptions = listOf(
        ComponentOption("Onboard Graphics", 0L),
        ComponentOption("NVIDIA GTX 1650", 2000000L),
        ComponentOption("NVIDIA RTX 3050", 3500000L),
        ComponentOption("NVIDIA RTX 4060", 5500000L)
    )
}
