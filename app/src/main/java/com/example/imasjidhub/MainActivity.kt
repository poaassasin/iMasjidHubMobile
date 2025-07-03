package com.example.imasjidhub

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imasjidhub.ui.screens.add.AddScreen
import com.example.imasjidhub.ui.screens.changepass.ChangePasswordScreen
import com.example.imasjidhub.ui.screens.detailadd.DetailAddScreen
import com.example.imasjidhub.ui.screens.detailnews.DetailNewsScreen
import com.example.imasjidhub.ui.screens.detailrequest.DetailRequestScreen
import com.example.imasjidhub.ui.screens.editprofile.EditProfileScreen
import com.example.imasjidhub.ui.screens.event.EventScreen
import com.example.imasjidhub.ui.screens.faq.FAQScreen
import com.example.imasjidhub.ui.screens.home.HomeScreen
import com.example.imasjidhub.ui.screens.login.LoginScreen
import com.example.imasjidhub.ui.screens.news.NewsScreen
import com.example.imasjidhub.ui.screens.profile.ProfileScreen
import com.example.imasjidhub.ui.screens.register.RegisterScreen
import com.example.imasjidhub.ui.screens.request.RequestScreen
import com.example.imasjidhub.ui.screens.splash.SplashScreen
import com.example.imasjidhub.ui.screens.startapp.StartAppScreen
import com.example.imasjidhub.ui.screens.time.TimeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        setContent {
            val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController) }
                composable("startapp") { StartAppScreen(navController) }
                composable("login") { LoginScreen(navController) }
                composable("register") { RegisterScreen(navController) }
                composable("home") { HomeScreen(navController) }
                composable("jadwal") { TimeScreen(navController) }
                composable("tambah") { AddScreen(navController) }
                composable("detailtambah") { DetailAddScreen(navController) }
                composable("berita") { NewsScreen(navController) }
                composable("detailberita") { DetailNewsScreen(navController) }
                composable("profil") { ProfileScreen(navController) }
                composable("editprofil") { EditProfileScreen(navController) }
                composable("event") { EventScreen(navController) }
                composable("request") { RequestScreen(navController) }
                composable("change") { ChangePasswordScreen(navController) }
                composable("faq") { FAQScreen(navController) }
                composable("detailrequest") { DetailRequestScreen(navController) }
            }
        }
    }
}
