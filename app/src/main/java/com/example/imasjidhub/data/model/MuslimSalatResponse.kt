package com.example.imasjidhub.model

data class MuslimSalatResponse(
    val items: List<PrayerItem>
)

data class PrayerItem(
    val fajr: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)

