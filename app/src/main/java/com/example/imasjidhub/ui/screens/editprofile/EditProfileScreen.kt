package com.example.imasjidhub.ui.screens.editprofile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.theme.AppText
import com.example.imasjidhub.viewmodel.FirebaseAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun copyUriToTempFile(context: Context, uri: Uri): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        Uri.fromFile(tempFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: FirebaseAuthViewModel = viewModel()
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        photoUri = uri
    }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uid) {
        uid?.let {
            db.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("fullName") ?: ""
                        email = document.getString("email") ?: ""
                        phone = document.getString("phone") ?: ""
                        address = document.getString("address") ?: ""
                        gender = document.getString("gender") ?: ""
                        photoUrl = document.getString("photoUrl") ?: ""
                    }
                }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_main_imasjidhub),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = AppText.Main,
                modifier = Modifier
                    .size(42.dp)
                    .clickable { navController.navigate("profil") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { launcher.launch("image/*") }
            ) {
                when {
                    photoUri != null -> AsyncImage(
                        model = photoUri,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(1.dp, AppText.Main, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    photoUrl.isNotEmpty() -> AsyncImage(
                        model = photoUrl,
                        contentDescription = "Existing Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(1.dp, AppText.Main, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    else -> Image(
                        painter = painterResource(id = R.drawable.ic_account),
                        contentDescription = "Default Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(1.dp, AppText.Main, CircleShape),
                    )
                }
            }

            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                LabeledTextFieldWithDivider("Name", name) { name = it }
                LabeledTextFieldWithDivider("Email", email) { email = it }
                LabeledTextFieldWithDivider("Phone Number", phone) { phone = it }
                LabeledTextFieldWithDivider("Address", address) { address = it }

                Text(
                    text = "Gender",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    RadioButton(
                        selected = gender == "male",
                        onClick = { gender = "male" },
                        colors = RadioButtonDefaults.colors(selectedColor = AppText.Main)
                    )
                    Text("Laki-laki", color = Color.White, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = gender == "female",
                        onClick = { gender = "female" },
                        colors = RadioButtonDefaults.colors(selectedColor = AppText.Main)
                    )
                    Text("Perempuan", color = Color.White, fontSize = 14.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionButton("Cancel") {
                        navController.navigate("profil")
                    }

                    ActionButton("Change") {
                        isLoading = true

                        fun updateProfile(photoUrlToUse: String) {
                            viewModel.updateUserProfile(
                                fullName = name,
                                phone = phone,
                                address = address,
                                gender = gender,
                                photoUrl = photoUrlToUse
                            ) { success, err ->
                                isLoading = false
                                if (success) {
                                    Toast.makeText(context, "Berhasil Update Profile", Toast.LENGTH_SHORT).show()
                                    CoroutineScope(Dispatchers.Main).launch {
                                        delay(400)
                                        navController.navigate("profil") {
                                            popUpTo("editprofil") { inclusive = true }
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Update Gagal: $err", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        if (photoUri != null) {
                            val fileUri = copyUriToTempFile(context, photoUri!!)

                            if (fileUri != null) {
                                val fileName = UUID.randomUUID().toString() + ".jpg"
                                val storageRef = Firebase.storage.reference.child("profileImages/$fileName")

                                storageRef.putFile(fileUri)
                                    .addOnSuccessListener {
                                        storageRef.downloadUrl
                                            .addOnSuccessListener { uri ->
                                                updateProfile(uri.toString())
                                            }
                                            .addOnFailureListener {
                                                isLoading = false
                                                Toast.makeText(context, "Gagal ambil URL gambar", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                    .addOnFailureListener {
                                        isLoading = false
                                        Toast.makeText(context, "Upload gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                isLoading = false
                                Toast.makeText(context, "Gagal memproses file gambar", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            updateProfile(photoUrl)
                        }
                    }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppText.Main)
            }
        }
    }
}

@Composable
fun LabeledTextFieldWithDivider(label: String, value: String, onValueChange: (String) -> Unit) {
    FieldLabel(label)
    CustomTextField(value = value, onValueChange = onValueChange)
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = AppText.Main)
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun FieldLabel(label: String) {
    Text(
        text = label,
        fontSize = 14.sp,
        color = Color.White,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(fontSize = 14.sp, color = AppText.Secondary)
    )
}

@Composable
fun RowScope.ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = AppText.Third.copy(alpha = 0.5f)),
        shape = CircleShape,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}
