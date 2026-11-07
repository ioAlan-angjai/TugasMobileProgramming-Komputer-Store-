package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.ComponentOption
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel yang mengelola state konfigurasi komputer ([OrderUiState])
 * dan menyediakan fungsi untuk memperbarui pilihan komponen serta menghitung total harga secara reaktif.
 */
class OrderViewModel : ViewModel() {

    // State internal yang dapat dimodifikasi
    private val _uiState = MutableStateFlow(OrderUiState())

    // State publik berjenis StateFlow untuk diobservasi oleh UI Compose
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Menentukan jenis perangkat yang dipilih (Desktop PC / Notebook)
     * sekaligus menetapkan harga dasar (base price) dan mereset komponen.
     */
    fun setDeviceType(deviceType: String) {
        val basePrice = if (deviceType == "Desktop PC") {
            DataSource.BASE_PRICE_DESKTOP
        } else {
            DataSource.BASE_PRICE_NOTEBOOK
        }

        _uiState.update { currentState ->
            val newState = currentState.copy(
                deviceType = deviceType,
                basePrice = basePrice,
                processor = null,
                ram = null,
                storage = null,
                monitor = null,
                graphicsCard = null
            )
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Memperbarui pilihan prosesor */
    fun setProcessor(option: ComponentOption) {
        _uiState.update { currentState ->
            val newState = currentState.copy(processor = option)
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Memperbarui pilihan kapasitas RAM */
    fun setRam(option: ComponentOption) {
        _uiState.update { currentState ->
            val newState = currentState.copy(ram = option)
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Memperbarui kategori tipe penyimpanan (HDD, SSD SATA, SSD NVMe) */
    fun setStorageType(type: String) {
        _uiState.update { currentState ->
            val newState = currentState.copy(
                storageType = type,
                storage = null // Reset pilihan spesifik saat tipe berubah
            )
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Memperbarui pilihan spesifik penyimpanan */
    fun setStorage(option: ComponentOption) {
        _uiState.update { currentState ->
            val newState = currentState.copy(storage = option)
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Memperbarui pilihan monitor atau ukuran layar */
    fun setMonitor(option: ComponentOption) {
        _uiState.update { currentState ->
            val newState = currentState.copy(monitor = option)
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Memperbarui pilihan kartu grafis (GPU) */
    fun setGraphicsCard(option: ComponentOption) {
        _uiState.update { currentState ->
            val newState = currentState.copy(graphicsCard = option)
            newState.copy(totalPrice = calculateTotalPrice(newState))
        }
    }

    /** Menghitung total harga keseluruhan (Harga Dasar + Seluruh Komponen Terpilih) */
    private fun calculateTotalPrice(state: OrderUiState): Long {
        return state.basePrice +
                (state.processor?.price ?: 0L) +
                (state.ram?.price ?: 0L) +
                (state.storage?.price ?: 0L) +
                (state.monitor?.price ?: 0L) +
                (state.graphicsCard?.price ?: 0L)
    }

    /** Mereset konfigurasi kembali ke kondisi awal */
    fun resetOrder() {
        _uiState.value = OrderUiState()
    }
}
