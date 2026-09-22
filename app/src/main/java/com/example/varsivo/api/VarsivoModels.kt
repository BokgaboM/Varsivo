package com.example.varsivo.api

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val schoolName: String? = null,
    val studentId: String? = null,
    val preferredLanguage: String = "EN"
)

data class LoginRequest(val email: String, val password: String)

data class AuthResponse(
    val message: String,
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val uid: String,
    val email: String,
    val fullName: String,
    val preferredLanguage: String,
    val role: String
)

data class SettingsRequest(
    val preferredLanguage: String? = null,
    val notificationsEnabled: Boolean? = null
)

data class InstitutionResponse(
    val id: Int, val name: String, val min_aps: Int,
    val province: String?, val latitude: Double?, val longitude: Double?
)

data class BursaryResponse(
    val id: Int, val name: String, val provider: String?,
    val field: String?, val province: String?, val closing_date: String?, val description: String?
)

data class ApplicationRequest(val institutionId: Int, val status: String)

data class ApplicationResponse(
    val id: Int, val user_id: Int, val institution_id: Int,
    val status: String, val institution_name: String?
)