package com.example.imasjidhub.ui.screens.startapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.imasjidhub.R
import com.example.imasjidhub.ui.theme.AppText

@Composable
fun StartAppScreen(navController: NavController) {
    val selectedButton = rememberSaveable {mutableStateOf("login")}
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_secondary_imasjidhub),
            contentDescription = null,
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
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(300.dp))
            Column(
                modifier =
                    Modifier.width(354.dp)
            ) {
                Text(
                    text = "Akses Informasi Masjid Lebih Mudah",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 28.sp,
                        lineHeight = 40.sp
                    ),
                    color = AppText.Main,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "iMasjidHub memudahkan akses informasi kegiatan, jadwal imam, dan layanan masjid untuk Anda",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 15.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.W300
                    ),
                    color = AppText.Main,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .width(380.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(50.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        selectedButton.value = "Sign Up"
                        navController.navigate("register")
                    },
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
                        .weight(1f)
                        .padding(end = 2.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton.value == "Sign Up") AppText.Third else Color.White,
                        contentColor = if (selectedButton.value == "Sign Up") Color.White else AppText.Third
                    )
                ) {
                    Text(text = "Sign Up")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        selectedButton.value = "login"
                        navController.navigate("login")
                    },
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
                        .weight(1f)
                        .padding(start = 2.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton.value == "login") AppText.Third else Color.White,
                        contentColor = if (selectedButton.value == "login") Color.White else AppText.Third
                    )
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartAppScreenPreview() {
    StartAppScreen(navController = rememberNavController())
}