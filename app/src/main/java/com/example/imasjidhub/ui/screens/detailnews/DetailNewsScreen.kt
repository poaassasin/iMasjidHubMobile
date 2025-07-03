package com.example.imasjidhub.ui.screens.detailnews

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.components.MainScreenLayout
import com.example.imasjidhub.ui.theme.AppText

@Composable
fun DetailNewsScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(3) }

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 48.dp)
            ) {
                Spacer(modifier = Modifier.height(72.dp))
                
                Text(
                    text = "April 22, 2025  -  Eid al-Adha",
                    fontSize = 12.sp,
                    color = AppText.Main,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Eid al-Adha 2025: A Day of Sacrifice",
                    fontSize = 16.sp,
                    color = AppText.Main,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.img_news),
                    contentDescription = "News Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Isi berita
                Text(
                    text = """
April 22, 2025 – Masjid Al-Waqar, was filled with joy and devotion as hundreds of worshippers gathered to celebrate Eid al-Adha, one of Islam’s holiest festivals.

The celebration began with the special Eid prayer at dawn, led by [Ustadz Jalaludin], followed by a khutbah (sermon) reminding the congregation of the values of sacrifice, patience, and sincerity. The mosque courtyard became a place of unity as men, women, and children came together in colorful attire, exchanging greetings and warm smiles.

Following the prayer, Masjid Al-Waqar held the Qurban ritual, sacrificing several livestock in honor of Prophet Ibrahim’s devotion to Allah. The meat was prepared and distributed fairly to the surrounding community, especially targeting the underprivileged, in the spirit of sharing and caring.
                    """.trimIndent(),
                    fontSize = 14.sp,
                    color = AppText.Main,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Justify
                )
            }

            // Icon Back selalu di atas (absolute posisi)
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = AppText.Main,
                modifier = Modifier
                    .padding(start = 24.dp, top = 48.dp)
                    .size(42.dp)
                    .clickable { navController.navigate("berita") }
            )
        }
    }
}

