package com.example.imasjidhub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.imasjidhub.R

val poppinsMedium = FontFamily(
    Font(R.font.poppins_light, weight = FontWeight.W300),
    Font(R.font.poppins_regular, weight = FontWeight.W400),
    Font(R.font.poppins_medium, weight = FontWeight.W500),
    Font(R.font.poppins_semibold, weight = FontWeight.W600),
    Font(R.font.poppins_bold, weight = FontWeight.W700),
)


val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.W700,
    ),
    displayMedium = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.W600,
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.W500,
    ),
    bodyMedium = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.W400,
    ),
    bodySmall = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.W300,
    )
)
