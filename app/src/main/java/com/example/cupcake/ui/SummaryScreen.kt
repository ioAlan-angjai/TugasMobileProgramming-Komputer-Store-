package com.example.cupcake.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.components.formatRupiah
import com.example.cupcake.ui.theme.TokoAccent
import com.example.cupcake.ui.theme.TokoPrimary

/**
 * [SummaryScreen] menampilkan ringkasan lengkap spesifikasi rakitan komputer
 * beserta total estimasi harga, tombol untuk menghubungi toko via WhatsApp (08816516325),
 * serta opsi untuk mengulang konfigurasi dari awal.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    uiState: OrderUiState,
    onRestartClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Format pesan ringkasan spesifikasi untuk dikirimkan ke WhatsApp toko (nomor: 628816516325)
    val message = "Halo AlanKomputer Store, saya ingin memesan rakitan ${uiState.deviceType} dengan spesifikasi:\n" +
            "- Prosesor: ${uiState.processor?.name ?: "-"}\n" +
            "- RAM: ${uiState.ram?.name ?: "-"}\n" +
            "- Penyimpanan: (${uiState.storageType}) ${uiState.storage?.name ?: "-"}\n" +
            "- Monitor/Layar: ${uiState.monitor?.name ?: "-"}\n" +
            "- Kartu Grafis: ${uiState.graphicsCard?.name ?: "-"}\n" +
            "Total Estimasi: ${formatRupiah(uiState.totalPrice)}"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ringkasan Rakitan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(50))
                                .background(TokoPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profil",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Ringkasan Konfigurasi",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = "Spesifikasi rakitan pilihanmu telah siap dirangkum.",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
            }

            // Card daftar komponen terpilih
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SpecRow(label = "Jenis Perangkat", value = uiState.deviceType)
                    Divider(color = Color(0xFFE5E7EB))
                    SpecRow(label = "Prosesor", value = uiState.processor?.name ?: "-")
                    Divider(color = Color(0xFFE5E7EB))
                    SpecRow(label = "Memori (RAM)", value = uiState.ram?.name ?: "-")
                    Divider(color = Color(0xFFE5E7EB))
                    SpecRow(label = "Penyimpanan", value = "${uiState.storageType} ${uiState.storage?.name ?: ""}")
                    Divider(color = Color(0xFFE5E7EB))
                    SpecRow(label = "Monitor / Layar", value = uiState.monitor?.name ?: "-")
                    Divider(color = Color(0xFFE5E7EB))
                    SpecRow(label = "Kartu Grafis", value = uiState.graphicsCard?.name ?: "-")
                }
            }

            // Card total estimasi harga
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Estimasi Harga",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = formatRupiah(uiState.totalPrice),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TokoAccent
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "*Harga perkiraan",
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tombol Hubungi Toko via WhatsApp (Nomor: 628816516325)
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://api.whatsapp.com/send?phone=628816516325&text=${Uri.encode(message)}")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TokoPrimary)
            ) {
                Text(
                    text = "Hubungi Toko",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Tombol teks untuk mengulang konfigurasi
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ulangi Konfigurasi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4B5563),
                    modifier = Modifier.clickable(onClick = onRestartClicked)
                )
            }
        }
    }
}

/**
 * Komponen baris tabel spesifikasi (Label & Value) di layar ringkasan.
 */
@Composable
fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF6B7280)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937)
        )
    }
}
