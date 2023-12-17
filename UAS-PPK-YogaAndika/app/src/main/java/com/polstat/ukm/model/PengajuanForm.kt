package com.polstat.ukm.model

import kotlinx.serialization.Serializable

@Serializable
data class PengajuanForm(
    val status: Boolean,
    val pengguna: User,
    val ukm: Ukm
)