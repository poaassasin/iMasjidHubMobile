package com.example.imasjidhub.data.remote
import com.example.imasjidhub.model.MuslimSalatResponse
import retrofit2.http.GET

interface MuslimSalatApi {
    @GET("malang.json")
    suspend fun getPrayerTimes(): MuslimSalatResponse
}
