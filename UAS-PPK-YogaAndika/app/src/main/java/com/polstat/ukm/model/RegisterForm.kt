package com.polstat.ukm.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterForm(
    val name: String,
    val email: String,
    val password: String
)