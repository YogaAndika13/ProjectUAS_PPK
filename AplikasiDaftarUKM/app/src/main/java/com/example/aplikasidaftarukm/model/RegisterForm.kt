package com.example.aplikasidaftarukm.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterForm(
    val name: String,
    val email: String,
    val password: String
)