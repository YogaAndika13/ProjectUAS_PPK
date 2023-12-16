package com.example.aplikasidaftarukm.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val email: String,
    val accessToken: String
)
