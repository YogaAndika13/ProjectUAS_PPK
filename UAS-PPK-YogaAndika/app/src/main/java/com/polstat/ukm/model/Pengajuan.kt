package com.polstat.ukm.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class Pengajuan @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: Long? = null,
    @EncodeDefault val status: Boolean = false,
    val pengguna: User? = null,
    val ukm: Ukm,
    val createdOn: String? = null,
    val updatedOn: String? = null
)