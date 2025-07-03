package com.example.imasjidhub.ui.screens.time

import PrayerViewModel
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.components.MainScreenLayout
import com.example.imasjidhub.ui.theme.AppText
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TimeScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(1) }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    var selectedDate by remember { mutableStateOf(formatDate(calendar.time)) }

    val prayerViewModel: PrayerViewModel = viewModel()
    val prayerItem by prayerViewModel.prayerTimes.collectAsState()

    fun showDatePicker(context: Context) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = formatDate(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    LaunchedEffect(selectedDate) {
        val parsed = SimpleDateFormat("EEEE, dd MMMM", Locale("id", "ID")).parse(selectedDate)
        parsed?.let {
            prayerViewModel.fetchPrayerTimesByDate(it)
        }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Jadwal Sholat",
                        color = AppText.Main,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Masjid al-Waqar",
                        color = AppText.Main,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = "Kalendar",
                        tint = AppText.Main,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                showDatePicker(context)
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_before),
                    contentDescription = "Sebelumnya",
                    tint = AppText.Main,
                    modifier = Modifier.clickable {
                        calendar.add(Calendar.DATE, -1)
                        selectedDate = formatDate(calendar.time)
                    }
                        .size(36.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = selectedDate,
                        color = AppText.Main,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "Berikutnya",
                    tint = AppText.Main,
                    modifier = Modifier.clickable {
                        calendar.add(Calendar.DATE, 1)
                        selectedDate = formatDate(calendar.time)
                    }
                        .size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sholat",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = AppText.Main,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Waktu",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = AppText.Main,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Imam",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = AppText.Main,
                        fontWeight = FontWeight.Bold
                    )
                }

                fun convertTo24HourFormat(time: String?): String {
                    return try {
                        val inputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
                        val outputFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
                        val date = inputFormat.parse(time ?: "")
                        outputFormat.format(date!!)
                    } catch (e: Exception) {
                        "-"
                    }
                }

                val imsakTime = try {
                    val inputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
                    val outputFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
                    val fajrRaw = prayerItem?.fajr ?: "4:30 am"
                    val fajrDate = inputFormat.parse(fajrRaw)
                    if (fajrDate != null) {
                        val imsakDate = Date(fajrDate.time - 10 * 60 * 1000)
                        outputFormat.format(imsakDate)
                    } else "-"
                } catch (e: Exception) {
                    "-"
                }

                val jadwalLengkap = listOf(
                    Triple("Imsak", imsakTime, "Ust. Mukhlis"),
                    Triple("Fajr", convertTo24HourFormat(prayerItem?.fajr), "Ust. Ahmad"),
                    Triple("Zuhr", convertTo24HourFormat(prayerItem?.dhuhr), "Ust. Fulan"),
                    Triple("Ashr", convertTo24HourFormat(prayerItem?.asr), "Ust. Hidayat"),
                    Triple("Maghrib", convertTo24HourFormat(prayerItem?.maghrib), "Ust. Budi"),
                    Triple("Isya", convertTo24HourFormat(prayerItem?.isha), "Ust. Zain")
                )

                jadwalLengkap.forEach { (sholat, waktu, imam) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sholat,
                            modifier = Modifier.weight(1f),
                            fontSize = 14.sp,
                            color = AppText.Main
                        )
                        Text(
                            text = waktu,
                            modifier = Modifier.weight(1f),
                            fontSize = 14.sp,
                            color = AppText.Main
                        )
                        Text(
                            text = imam,
                            modifier = Modifier.weight(1f),
                            fontSize = 14.sp,
                            color = AppText.Main
                        )
                    }
                }
            }
        }
    }
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale("id", "ID"))
    return formatter.format(date)
}
