package com.polstat.ukm.model

import kotlinx.serialization.Serializable

@Serializable
data class Ukm(
    val id: Long?,
    val namaUkm: String,
    val deskripsiUkm: String
)