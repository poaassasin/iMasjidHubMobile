package com.example.imasjidhub.ui.screens.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.outlinedCardBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
fun AddScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(2) }
    var searchText by remember { mutableStateOf("") }

    // Sample static event data
    val allEvents = listOf(
        "Pengajian Rutin",
        "Kajian Remaja",
        "Majlis Taklim",
        "Tabligh Akbar"
    )

    // Filter berdasarkan input user
    val filteredEvents = remember(searchText) {
        if (searchText.isBlank()) allEvents
        else allEvents.filter { it.contains(searchText, ignoreCase = true) }
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
                text = "Event",
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
                        text = "Search event..",
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
            Spacer(modifier = Modifier.height(24.dp))

            val categories = listOf("Kajian Remaja", "Majlis Taklim", "Tabligh Akbar")
            var selectedCategory by remember { mutableStateOf(categories[0]) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    categories.forEach { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(36.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) AppText.Third else AppText.Fourth
                                )
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category,
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                Text(
                    text = "Acara Terdekat",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    if (filteredEvents.isEmpty()) {
                        Text(
                            text = "Event atau Acara tidak ditemukan.",
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
                        filteredEvents.forEach { eventTitle ->
                            EventCard(navController, eventTitle)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

// MODIFIED EventCard menerima judul event
@Composable
fun EventCard(navController: NavController, eventTitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                navController.navigate("detailtambah")
            },
        shape = RoundedCornerShape(24.dp),
        colors = cardColors(containerColor = AppText.Fourth),
        border = outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_event),
                contentDescription = "Event Image",
                modifier = Modifier
                    .width(125.dp)
                    .height(150.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 0.dp,
                            bottomStart = 24.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = eventTitle, // Dynamic title
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppText.Main
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Calendar",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Senin, 05 April", fontSize = 12.sp, color = AppText.Main)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "Time",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("16:00", fontSize = 12.sp, color = AppText.Main)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "Location",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Masjid Al-Waqar", fontSize = 12.sp, color = AppText.Main)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(24.dp),
                    colors = buttonColors(
                        containerColor = AppText.Third
                    ),
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    Text("Ikuti", fontSize = 14.sp, color = AppText.Main)
                }
            }
        }
    }
}
