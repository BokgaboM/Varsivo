package com.example.varsivo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


private const val VARSIVO_BASE_URL = "http://10.0.2.2:3000/api/"

    private const val BURSARY_BASE_URL =
        "https://6aa34e30e7ae868cdf7aceb1.mockapi.io/"

    val universityApi: UniversityApi =
        Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversityApi::class.java)

    val bursaryApi: BursaryApiService =
        Retrofit.Builder()
            .baseUrl(BURSARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BursaryApiService::class.java)

    val geocodingApi: GeocodingApi =
        Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApi::class.java)

val varsivoApi: VarsivoApi =
    Retrofit.Builder()
        .baseUrl(VARSIVO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(VarsivoApi::class.java)
}