package com.example.imasjidhub.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.components.MainScreenLayout
import com.example.imasjidhub.ui.theme.AppText

@Composable
fun NewsScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(3) }
    var searchText by remember { mutableStateOf("") }

    // Sample data berita
    val sampleNews = listOf(
        Triple("Eid al-Adha", "Eid al-Adha 2025: A Day of Sacrifice", "April 22, 2025"),
        Triple("Community", "Mosque Clean-Up Event 2025", "May 15, 2025"),
        Triple("Community", "Mosque Clean-Up Event 2025", "May 15, 2025"),
        Triple("Community", "Mosque Clean-Up Event 2025", "May 15, 2025"),
        Triple("Community", "Mosque Clean-Up Event 2025", "May 15, 2025")
    )

    val filteredNews = sampleNews.filter {
        it.first.contains(searchText, ignoreCase = true) || // category
                it.second.contains(searchText, ignoreCase = true)    // title
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // Title
            Text(
                text = "News",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 48.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = AppText.Main
                    )
                },
                placeholder = {
                    Text(
                        text = "Search news...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = AppText.Main)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = AppText.Main),
                shape = RoundedCornerShape(36.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = AppText.Main,
                    unfocusedIndicatorColor = AppText.Main,
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Scroll konten berita saja
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (filteredNews.isEmpty()) {
                    Text(
                        text = "Berita tidak ditemukan.",
                        color = AppText.Main,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                } else {
                    filteredNews.forEach { (category, title, date) ->
                        NewsItem(
                            imageRes = R.drawable.img_news,
                            category = category,
                            title = title,
                            date = date,
                            onClick = {
                                navController.navigate("detailberita")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(
    imageRes: Int,
    category: String,
    title: String,
    date: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(8.dp))
    Column(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "News Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodySmall.copy(color = AppText.Main)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = AppText.Main,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall.copy(color = AppText.Main)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = AppText.Main.copy(alpha = 0.6f)
        )
    }
}