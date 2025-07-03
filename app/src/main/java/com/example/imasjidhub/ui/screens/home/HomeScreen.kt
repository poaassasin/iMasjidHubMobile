package com.example.imasjidhub.ui.screens.home

import PrayerViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.imasjidhub.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.example.imasjidhub.ui.components.MainScreenLayout
import com.example.imasjidhub.ui.theme.AppText
import com.example.imasjidhub.viewmodel.FirebaseAuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale



@Composable
fun HomeScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val viewModel: FirebaseAuthViewModel = viewModel()
    BackHandler {
        showLogoutDialog = true
    }

    val currentDate = remember {
        SimpleDateFormat("d MMMM", Locale("id", "ID")).format(Date())
    }
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid

    var userName by remember { mutableStateOf("") }

    LaunchedEffect(uid) {
        uid?.let {
            FirebaseFirestore.getInstance().collection("users")
                .document(it)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName = document.getString("fullName") ?: ""
                    }
                }
        }
    }

    val prayerViewModel: PrayerViewModel = viewModel()
    val prayerItem by prayerViewModel.prayerTimes.collectAsState()
    val jadwal = prayerItem

    fun convertTo24HourFormat(time: String?): String {
        return try {
            val inputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
            val date = inputFormat.parse(time ?: "") ?: return "-"
            outputFormat.format(date)
        } catch (_: Exception) {
            "-"
        }
    }

    val prayerMap = mapOf(
        "Fajr" to convertTo24HourFormat(jadwal?.fajr),
        "Zuhr" to convertTo24HourFormat(jadwal?.dhuhr),
        "Ashr" to convertTo24HourFormat(jadwal?.asr),
        "Maghrib" to convertTo24HourFormat(jadwal?.maghrib),
        "Isya" to convertTo24HourFormat(jadwal?.isha)
    )

    val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val now = Calendar.getInstance().time

    val nextPrayer = prayerMap.entries
        .mapNotNull { (name, formattedTime) ->
            try {
                val parsedTime = inputFormat.parse(formattedTime) ?: return@mapNotNull null
                val calPrayer = Calendar.getInstance().apply {
                    time = parsedTime
                    val today = Calendar.getInstance()
                    set(Calendar.YEAR, today.get(Calendar.YEAR))
                    set(Calendar.MONTH, today.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))
                }.time

                if (calPrayer.after(now)) {
                    val diffMillis = calPrayer.time - now.time
                    Triple(name, formattedTime, diffMillis)
                } else null
            } catch (_: Exception) {
                null
            }
        }
        .minByOrNull { it.third }

    val nextPrayerName = nextPrayer?.first ?: "-"
    val timeUntil = nextPrayer?.third?.let { millis ->
        val hours = millis / (1000 * 60 * 60)
        val minutes = (millis / (1000 * 60)) % 60
        "$hours h $minutes min"
    } ?: "-"

    LaunchedEffect(key1 = true) {
        prayerViewModel.fetchPrayerTimes()
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
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Assalamualaikum",
                        color = AppText.Main,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (userName.isBlank()) {
                        CircularProgressIndicator(
                            color = AppText.Main,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            text = userName,
                            color = AppText.Main,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(AppText.Third)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentDate,
                            color = AppText.Main,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(AppText.Secondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_notifications),
                            contentDescription = "Notifikasi",
                            tint = AppText.Main,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .border(
                            width = 1.dp,
                            color = AppText.Main,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AppText.Fourth),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = 1.dp,
                                    color = AppText.Main,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bg_card_imasjidhub),
                                contentDescription = null,
                                modifier = Modifier
                                    .matchParentSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(
                                    Color.Black.copy(alpha = 0.8f),
                                    BlendMode.Darken
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "$nextPrayerName - Malang",
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 24.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = timeUntil,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 20.sp
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_sound),
                                        contentDescription = "Sound",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_next),
                                        contentDescription = "Next",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 16.dp,
                                        bottomEnd = 16.dp
                                    )
                                )
                                .background(AppText.Fourth)
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val jadwal = prayerItem
                                fun convertTo24HourFormat(time: String?): String {
                                    return try {
                                        val inputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
                                        val outputFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
                                        val date = inputFormat.parse(time ?: "")
                                        outputFormat.format(date!!)
                                    } catch (_: Exception) {
                                        "-"
                                    }
                                }

                                val imsakTime = try {
                                    val fajrRaw = jadwal?.fajr ?: "4:30 am"
                                    val inputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
                                    val outputFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
                                    val fajrDate = inputFormat.parse(fajrRaw)
                                    val imsakDate = Date(fajrDate!!.time - 10 * 60 * 1000)
                                    outputFormat.format(imsakDate)
                                } catch (_: Exception) {
                                    "-"
                                }

                                val jadwalLengkap = listOf(
                                    Triple("Imsak", imsakTime, R.drawable.ic_imsak),
                                    Triple("Fajr", convertTo24HourFormat(jadwal?.fajr), R.drawable.ic_shubuh),
                                    Triple("Zuhr", convertTo24HourFormat(jadwal?.dhuhr), R.drawable.ic_dzuhur),
                                    Triple("Ashr", convertTo24HourFormat(jadwal?.asr), R.drawable.ic_ashar),
                                    Triple("Maghrib", convertTo24HourFormat(jadwal?.maghrib), R.drawable.ic_maghrib),
                                    Triple("Isya", convertTo24HourFormat(jadwal?.isha), R.drawable.ic_isya)
                                )


                                jadwalLengkap.forEach { (nama, jam, iconRes) ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = nama,
                                            color = AppText.Main,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Icon(
                                            painter = painterResource(id = iconRes),
                                            contentDescription = nama,
                                            tint = AppText.Main,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = jam,
                                            color = AppText.Main,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                        // Glimpse of al-Waqar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_home1),
                                contentDescription = "Masjid al-Waqar",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(170.dp)
                                    .clip(RoundedCornerShape(24.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "a Glimpse of al-Waqar",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Masjid Al Waqar is a welcoming place of worship and community located in the heart of our neighborhood. Committed to spiritual growth and social unity, the mosque serves as a center for daily prayers, religious education, and community outreach",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Light of the Ummah
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Light of the Ummah",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AppText.Main
                                )
                            )
                            Text(
                                text = "Show more",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    textDecoration = TextDecoration.Underline,
                                    color = AppText.Main
                                ),
                                modifier = Modifier.clickable {
                                    // TODO: aksi ketika diklik
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = AppText.Main,
                                    shape = RoundedCornerShape(36.dp)
                                ),
                            shape = RoundedCornerShape(36.dp),
                            colors = CardDefaults.cardColors(containerColor = AppText.Fourth),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Message of Peace this Eid",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = AppText.Main,
                                            fontSize = 15.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Last Wednesday, our mosque joyfully celebrated Eid al-Fitr with hundreds of worshippers gathering for the Eid prayer. The celebration began with Takbir in the early morning, followed by a heartfelt sermon reminding us of unity, gratitude, and compassion",
                                        style = MaterialTheme.typography.bodySmall.copy(color = AppText.Main)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.img_home2),
                                    contentDescription = "Eid Image",
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    if (showLogoutDialog) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.6f))
                                .blur(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AlertDialog(
                                onDismissRequest = { showLogoutDialog = false },
                                title = { Text("Konfirmasi Logout", color = AppText.Main) },
                                text = {
                                    Text(
                                        "Apakah kamu yakin ingin keluar?",
                                        color = AppText.Main
                                    )
                                },
                                confirmButton = {
                                    TextButton(onClick = {
                                        showLogoutDialog = false
                                        viewModel.signOut {
                                            navController.navigate("login") {
                                                popUpTo(0) { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        }
                                    }) {
                                        Text("Ya", color = AppText.Main)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showLogoutDialog = false }) {
                                        Text("Tidak", color = AppText.Main)
                                    }
                                },
                                containerColor = AppText.Fitfth,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
}