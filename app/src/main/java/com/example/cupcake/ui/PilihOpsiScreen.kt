package com.example.cupcake.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.data.ComponentOption
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.components.OptionCard
import com.example.cupcake.ui.components.PriceBottomBar
import com.example.cupcake.ui.components.StepTopBar
import com.example.cupcake.ui.components.formatRupiah
import com.example.cupcake.ui.theme.TokoPrimary

/**
 * [PilihOpsiScreen] adalah layar wizard dinamis (Langkah 1 s.d. 5)
 * yang digunakan untuk memilih komponen spesifikasi komputer secara berurutan:
 * 1. Prosesor
 * 2. Memori (RAM)
 * 3. Hardisk & SSD (dengan Segmented Buttons untuk tipe penyimpanan)
 * 4. Monitor / Ukuran Layar
 * 5. Kartu Grafis (GPU)
 */
@Composable
fun PilihOpsiScreen(
    step: Int,
    uiState: OrderUiState,
    onProcessorSelected: (ComponentOption) -> Unit,
    onRamSelected: (ComponentOption) -> Unit,
    onStorageTypeSelected: (String) -> Unit,
    onStorageSelected: (ComponentOption) -> Unit,
    onMonitorSelected: (ComponentOption) -> Unit,
    onGraphicsCardSelected: (ComponentOption) -> Unit,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = when (step) {
        1 -> "Wizard Prosesor"
        2 -> "Wizard Memori & RAM"
        3 -> "Wizard Memori & Penyimpanan"
        4 -> "Wizard Monitor & Layar"
        else -> "Wizard Kartu Grafis"
    }

    val heading = when (step) {
        1 -> "Pilih Prosesor"
        2 -> "Pilih Memori (RAM)"
        3 -> "Pilih Hardisk & SSD"
        4 -> if (uiState.deviceType == "Desktop PC") "Pilih Monitor" else "Pilih Ukuran Layar"
        else -> "Pilih Kartu Grafis"
    }

    val isEnabled = when (step) {
        1 -> uiState.processor != null
        2 -> uiState.ram != null
        3 -> uiState.storage != null
        4 -> uiState.monitor != null
        else -> uiState.graphicsCard != null
    }

    Scaffold(
        topBar = {
            StepTopBar(
                title = title,
                step = step,
                onBackClicked = onBackClicked
            )
        },
        bottomBar = {
            PriceBottomBar(
                totalPrice = uiState.totalPrice,
                onNextClicked = onNextClicked,
                enabled = isEnabled
            )
        },
        containerColor = Color(0xFFF5F6F8)
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Langkah $step dari 5",
                fontSize = 14.sp,
                color = Color(0xFF6B7280)
            )

            Text(
                text = heading,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937)
            )

            Spacer(modifier = Modifier.height(4.dp))

            when (step) {
                1 -> {
                    DataSource.processorOptions.forEach { option ->
                        OptionCard(
                            title = option.name,
                            subtitle = formatRupiah(option.price).let { if (it == "Rp 0") "Termasuk" else "+ $it" },
                            selected = uiState.processor == option,
                            onClick = { onProcessorSelected(option) }
                        )
                    }
                }
                2 -> {
                    DataSource.ramOptions.forEach { option ->
                        OptionCard(
                            title = option.name,
                            subtitle = "+ ${formatRupiah(option.price)}",
                            selected = uiState.ram == option,
                            onClick = { onRamSelected(option) }
                        )
                    }
                }
                3 -> {
                    // Segmented Button untuk memilih Tipe Penyimpanan (HDD, SSD SATA, SSD NVMe)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("HDD", "SSD SATA", "SSD NVMe").forEach { type ->
                            val selected = uiState.storageType == type
                            OutlinedButton(
                                onClick = { onStorageTypeSelected(type) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (selected) TokoPrimary else Color.Transparent,
                                    contentColor = if (selected) Color.White else Color(0xFF1F2937)
                                ),
                                border = null
                            ) {
                                Text(
                                    text = type,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val storageList = DataSource.storageOptionsByType[uiState.storageType] ?: emptyList()
                    storageList.forEach { option ->
                        OptionCard(
                            title = option.name,
                            subtitle = "+ ${formatRupiah(option.price)}",
                            selected = uiState.storage == option,
                            onClick = { onStorageSelected(option) }
                        )
                    }
                }
                4 -> {
                    val monitorList = if (uiState.deviceType == "Desktop PC") {
                        DataSource.desktopMonitorOptions
                    } else {
                        DataSource.notebookMonitorOptions
                    }
                    monitorList.forEach { option ->
                        OptionCard(
                            title = option.name,
                            subtitle = formatRupiah(option.price).let { if (it == "Rp 0") "Standar" else "+ $it" },
                            selected = uiState.monitor == option,
                            onClick = { onMonitorSelected(option) }
                        )
                    }
                }
                5 -> {
                    DataSource.graphicsCardOptions.forEach { option ->
                        OptionCard(
                            title = option.name,
                            subtitle = formatRupiah(option.price).let { if (it == "Rp 0") "Onboard" else "+ $it" },
                            selected = uiState.graphicsCard == option,
                            onClick = { onGraphicsCardSelected(option) }
                        )
                    }
                }
            }
        }
    }
}
