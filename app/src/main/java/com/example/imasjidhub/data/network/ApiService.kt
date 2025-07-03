package com.example.imasjidhub.data.network

import com.example.imasjidhub.model.MuslimSalatResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("malang.json")
    suspend fun getPrayerTimes(): MuslimSalatResponse

    @GET("malang/{date}.json")
    suspend fun getPrayerTimesByDate(
        @Path("date") date: String
    ): MuslimSalatResponse
}