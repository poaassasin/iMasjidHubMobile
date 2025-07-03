package com.example.imasjidhub.ui.screens.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.components.MainScreenLayout
import com.example.imasjidhub.ui.theme.AppText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import coil.compose.AsyncImage
import com.example.imasjidhub.viewmodel.FirebaseAuthViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(4) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val viewModel: FirebaseAuthViewModel = viewModel()

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }

    DisposableEffect(uid) {
        val listenerRegistration = uid?.let {
            db.collection("users").document(it)
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener

                    name = snapshot.getString("fullName") ?: ""
                    email = snapshot.getString("email") ?: ""
                    phone = snapshot.getString("phone") ?: ""
                    address = snapshot.getString("address") ?: ""
                    gender = snapshot.getString("gender") ?: ""
                    photoUrl = snapshot.getString("photoUrl") ?: ""
                }
        }
        onDispose {
            listenerRegistration?.remove()
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
            Text(
                text = "Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Card
            Card(
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AppText.Main),
                colors = CardDefaults.cardColors(containerColor = AppText.Fourth),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        if (photoUrl.isNotBlank()) {
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, AppText.Main, shape = CircleShape)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_account),
                                contentDescription = "Default Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, AppText.Main, shape = CircleShape)
                            )
                        }
                        if (gender.isNotBlank()) {
                            Icon(
                                painter = painterResource(
                                    id = if (gender.lowercase() == "female") R.drawable.ic_female else R.drawable.ic_male
                                ),
                                contentDescription = "Gender Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(22.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(color = AppText.Third, shape = CircleShape)
                                    .padding(4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(name, color = AppText.Main, fontWeight = FontWeight.Bold)
                        Text(email, color = AppText.Main, fontSize = 12.sp)
                        Text(
                            if (phone.isNotBlank()) phone else "Belum diisi",
                            color = AppText.Main,
                            fontSize = 12.sp
                        )
                        Text(
                            if (address.isNotBlank()) address else "Belum diisi",
                            color = AppText.Main,
                            fontSize = 12.sp
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navController.navigate("editprofil")
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val settingsItems = listOf(
                "Daftar Acara yang diikuti" to R.drawable.ic_event,
                "Daftar Permintaan Tambah Inventaris" to R.drawable.ic_inventory,
                "Ubah Password" to R.drawable.ic_lock,
                "FAQ" to R.drawable.ic_faq
            )

            Card(
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AppText.Main),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    settingsItems.forEach { (title, icon) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    when (title) {
                                        "Daftar Acara yang diikuti" -> navController.navigate("event")
                                        "Daftar Permintaan Tambah Inventaris" -> navController.navigate("request")
                                        "Ubah Password" -> navController.navigate("change")
                                        "FAQ" -> navController.navigate("faq")
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(title, color = Color.White, modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.ic_next),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Logout
            Card(
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AppText.Main),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showLogoutDialog = true
                        }
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Red),
                        modifier = Modifier
                            .size(20.dp)
                            .graphicsLayer { scaleX = -1f }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Logout",
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
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
                    text = { Text("Apakah kamu yakin ingin keluar?", color = AppText.Main) },
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
