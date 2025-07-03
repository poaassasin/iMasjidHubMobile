package com.example.imasjidhub.ui.screens.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.theme.AppText
import com.example.imasjidhub.ui.theme.poppinsMedium

@Composable
fun EventScreen(navController: NavController) {
    val selectedButton = rememberSaveable { mutableStateOf("event") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_main_imasjidhub),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(42.dp)
                        .clickable { navController.navigate("profil") }
                )

                Spacer(modifier = Modifier.width(60.dp))

                Text(
                    text = "Request Event",
                    fontFamily = poppinsMedium,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .border(1.dp, AppText.Main, RoundedCornerShape(50.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        selectedButton.value = "event"
                        navController.navigate("event")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(end = 2.dp),
                    shape = RoundedCornerShape(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton.value == "event") AppText.Third else Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Event", style = MaterialTheme.typography.bodyLarge)
                }

                Button(
                    onClick = {
                        selectedButton.value = "request"
                        navController.navigate("request")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 2.dp),
                    shape = RoundedCornerShape(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton.value == "request") AppText.Third else Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Inventaris", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            EventCard()
        }
    }
}

@Composable
fun EventCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, AppText.Main, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = cardColors(containerColor = AppText.Fourth)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_event),
                contentDescription = "Event Image",
                modifier = Modifier
                    .width(145.dp)
                    .height(180.dp)
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Pengajian Rutin",
                    fontFamily = poppinsMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Senin, 05 April 2025",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "16:00",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Masjid Al-Waqar",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Handle cancel */ },
                    modifier = Modifier
                        .height(45.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppText.Third,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Batalkan",
                        fontSize = 14.sp,
                        fontFamily = poppinsMedium,
                        color = AppText.Main
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = AppText.Main.copy(alpha = 0.6f)
    )
}
