package com.example.imasjidhub.ui.screens.detailadd

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.components.MainScreenLayout
import com.example.imasjidhub.ui.theme.AppText

@Composable
fun DetailAddScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(2) }

    MainScreenLayout(
        selectedIndex = selectedIndex,
        onItemSelected = {
            selectedIndex = it
            when (it) {
                0 -> navController.navigate("home")
                1 -> navController.navigate("jadwal")
                2 -> navController.navigate("tambah")
                3 -> navController.navigate("berita")
                4 -> navController.navigate("profil")
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.img_event),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .blur(radius = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 48.dp),
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = AppText.Main,
                    modifier = Modifier
                        .size(42.dp)
                        .clickable { navController.navigate("tambah") }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(280.dp)
                            .height(200.dp)
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(24.dp),
                                clip = false
                            )
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_event),
                            contentDescription = "Event Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Pengajian Rutin",
                        fontSize = 20.sp,
                        color = AppText.Main,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Mari hadir dan isi hari-harimu dengan ilmu dan keimanan dalam pengajian rutin yang diselenggarakan oleh Masjid Al Waqaar. Terbuka untuk umum. Ajak keluarga dan sahabat untuk ikut memperdalam ilmu agama bersama dalam suasana yang penuh kedamaian.",
                        fontSize = 15.sp,
                        color = AppText.Main,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_calendar),
                            contentDescription = "Tanggal",
                            tint = AppText.Main,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Senin, 05 April 2025 • 16:00 (Ba'da Ashar)",
                            fontSize = 13.sp,
                            color = AppText.Main
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = "Lokasi",
                            tint = AppText.Main,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Masjid Al-Waqar",
                            fontSize = 13.sp,
                            color = AppText.Main
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_book),
                            contentDescription = "Topik",
                            tint = AppText.Main,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tafsir Surat Al-Mulk: Pelindung dari Siksa Kubur",
                            fontSize = 13.sp,
                            color = AppText.Main
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { /* TODO: aksi Ikuti */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppText.Fourth)
                    ) {
                        Text(
                            text = "Ikuti",
                            fontSize = 16.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                            color = AppText.Main
                        )
                    }
                }
            }
        }
    }
}
