package com.example.imasjidhub.ui.screens.changepass

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.theme.poppinsMedium
import com.example.imasjidhub.viewmodel.FirebaseAuthViewModel

@Composable
fun ChangePasswordScreen(navController: NavController,
                         viewModel: FirebaseAuthViewModel = viewModel()
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

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
                        .clickable { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.width(46.dp))

                Text(
                    text = "Ubah Password",
                    fontFamily = poppinsMedium,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            PasswordInput("Old Password", "Enter Old Password", oldPassword) { oldPassword = it }
            Spacer(modifier = Modifier.height(24.dp))
            PasswordInput("New Password", "Enter New Password", newPassword) { newPassword = it }
            Spacer(modifier = Modifier.height(24.dp))
            PasswordInput("Re Enter New Password", "Enter New Password Again", confirmPassword) { confirmPassword = it }


            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = {
                        if (newPassword != confirmPassword) {
                            Toast.makeText(context, "Password baru tidak cocok", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        isLoading = true
                        viewModel.changePassword(
                            oldPassword = oldPassword,
                            newPassword = newPassword
                        ) { success, error ->
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                                navController.navigate("login")
                            } else {
                                Toast.makeText(context, "Gagal ubah password: $error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B4C3B)),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Save",
                        fontFamily = poppinsMedium,
                        color = Color.White,
                        fontSize = 14.sp
                    )
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
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Composable
fun PasswordInput(label: String, hint: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = label,
            fontFamily = poppinsMedium,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = hint,
                    color = Color.Gray,
                    fontFamily = poppinsMedium,
                    fontSize = 14.sp
                )
            },
            shape = RoundedCornerShape(50),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = poppinsMedium,
                fontSize = 14.sp,
                color = Color.White
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp)
        )
    }
}


