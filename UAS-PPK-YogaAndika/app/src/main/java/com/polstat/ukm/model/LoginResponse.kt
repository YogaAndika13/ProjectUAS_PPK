package com.polstat.ukm.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val email: String,
    val accessToken: String
)