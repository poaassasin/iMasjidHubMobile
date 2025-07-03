package com.example.imasjidhub.ui.screens.login

import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imasjidhub.viewmodel.FirebaseAuthViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.theme.AppText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val selectedButton = rememberSaveable {mutableStateOf("login")}
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val authViewModel: FirebaseAuthViewModel = viewModel()
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("home") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_main_imasjidhub),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_main_imasjidhub),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(430.dp)
                .height(615.dp)
                .offset(y = 265.dp)) {
            Image(
                painter = painterResource(id = R.drawable.outer_imasjidhub),
                contentDescription = "Outer",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize() .alpha(0.1f) .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            )
            val cornerRadius = with(LocalDensity.current) { 50.dp.toPx() }
            val strokeWidth = with(LocalDensity.current) { 0.5.dp.toPx() }

            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, cornerRadius)
                    quadraticTo(0f, 0f, cornerRadius, 0f)
                    lineTo(size.width - cornerRadius, 0f)
                    quadraticTo(size.width, 0f, size.width, cornerRadius)
                }
                drawPath(
                    path = path,
                    color = AppText.Main,
                    style = Stroke(width = strokeWidth)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .width(384.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .border(
                            width = 1.dp,
                            color = AppText.Main,
                            shape = RoundedCornerShape(50.dp)
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            selectedButton.value = "register"
                            navController.navigate("register")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(end = 2.dp),
                        shape = RoundedCornerShape(80.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedButton.value == "register") AppText.Third else Color.Transparent,
                            contentColor = if (selectedButton.value == "register") Color.White else Color.White
                        )
                    ) {
                        Text(text = "Register", style = MaterialTheme.typography.bodyLarge)
                    }
                    Button(
                        onClick = {
                            selectedButton.value = "login"
                            navController.navigate("login")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 2.dp),
                        shape = RoundedCornerShape(80.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedButton.value == "login") AppText.Third else Color.Transparent,
                            contentColor = if (selectedButton.value == "login") Color.White else Color.White
                        )
                    ) {
                        Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                LoginInputFields(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = rememberMe, onCheckedChange = {rememberMe = it})
                        Text(text = "Remember me", color = AppText.Main)
                    }
                    Text(text = "Forgot Password?", color = AppText.Main)
                }
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Kamu belum mengisi email / password", Toast.LENGTH_SHORT).show()
                        } else {
                            authViewModel.login(email, password) { success, error ->
                                if (success) {
                                    Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home")
                                } else {
                                    Toast.makeText(context, error ?: "Login gagal", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .padding(vertical = 8.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = AppText.Third)
                ) {
                    Text(text = "Login", color = Color.White)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = AppText.Main.copy(alpha = 0.3f))
                    Text(text = "  or login with  ", color = AppText.Main.copy(alpha = 0.5f))
                    HorizontalDivider(modifier = Modifier.weight(1f), color = AppText.Main.copy(alpha = 0.3f))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_google_imasjidhub),
                    contentDescription = "Google",
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(48.dp)
                        .clickable {
                            coroutineScope.launch {
                                val credentialManager = CredentialManager.create(context)

                                val googleIdOption = GetGoogleIdOption.Builder()
                                    .setServerClientId("613222569358-rn4ml8sh4l47otua1lcdvs5csmukc9pr.apps.googleusercontent.com")
                                    .setFilterByAuthorizedAccounts(false)
                                    .build()

                                val request = GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()

                                try {
                                    val result = credentialManager.getCredential(context, request)
                                    val credential = result.credential
                                    if (credential is GoogleIdTokenCredential) {
                                        val idToken = credential.idToken
                                        authViewModel.signInWithGoogle(idToken) { success, error ->
                                            if (success) {
                                                navController.navigate("home") {
                                                    popUpTo("login") { inclusive = true }
                                                }
                                            } else {
                                                Toast.makeText(context, error ?: "Login gagal", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "Credential Google tidak ditemukan", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: GetCredentialException) {
                                    Log.e("GoogleSignIn", "Error: ${e.message}")
                                    Toast.makeText(context, "Google Sign-In dibatalkan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                )
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = "Don’t have account? ", color = AppText.Main.copy(alpha = 0.5f))
                    Text(text = "Register", color = AppText.Third, modifier = Modifier.clickable { navController.navigate("register") })
                }
            }
        }
    }
}

@Composable
fun LoginInputFields(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(value = email, onValueChange = onEmailChange, placeholder = { Text("Enter your email here", color = AppText.Main.copy(alpha = 0.5f), style = MaterialTheme.typography.bodySmall)},
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Spacer(modifier = Modifier.width(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(AppText.Main)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(35.dp),
                        color = AppText.Main.copy(alpha = 0.5f)
                    )
                }
            },
                colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppText.Main,
                unfocusedBorderColor = AppText.Main,
                cursorColor = AppText.Main
            ), modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(50.dp)), shape = RoundedCornerShape(50.dp), singleLine = true)
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text("Enter your password here", color = AppText.Main.copy(alpha = 0.5f), style = MaterialTheme.typography.bodySmall)},
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Spacer(modifier = Modifier.width(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Lock Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(AppText.Main)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(35.dp),
                        color = AppText.Main.copy(alpha = 0.5f)
                    )
                }
            },
            trailingIcon = {
                val visibilityIcon = if (passwordVisible)
                    R.drawable.ic_visibility_on else R.drawable.ic_visibility_off

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(AppText.Main)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppText.Main,
                unfocusedBorderColor = AppText.Main,
                cursorColor = AppText.Main
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(50.dp)),
            shape = RoundedCornerShape(50.dp),
            singleLine = true
        )
    }
}
