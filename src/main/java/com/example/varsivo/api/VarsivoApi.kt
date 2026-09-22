package com.example.varsivo.api

import retrofit2.Response
import retrofit2.http.*

interface VarsivoApi {
    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponse>

    @GET("users/me")
    suspend fun getMe(@Header("Authorization") token: String): Response<UserResponse>

    @PATCH("users/{id}/settings")
    suspend fun updateSettings(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body body: SettingsRequest
    ): Response<UserResponse>

    @GET("institutions")
    suspend fun getInstitutions(@Query("aps") aps: Int? = null): Response<List<InstitutionResponse>>

    @GET("bursaries")
    suspend fun getBursaries(): Response<List<BursaryResponse>>

    @GET("applications")
    suspend fun getApplications(@Header("Authorization") token: String): Response<List<ApplicationResponse>>

    @POST("applications")
    suspend fun createApplication(
        @Header("Authorization") token: String,
        @Body body: ApplicationRequest
    ): Response<ApplicationResponse>
}