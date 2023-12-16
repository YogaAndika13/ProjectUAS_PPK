package com.example.aplikasidaftarukm.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordForm(
    val oldPassword: String,
    val newPassword: String
)
