package com.polstat.ukm.data

import com.polstat.ukm.model.Ukm
import com.polstat.ukm.service.UkmService

interface UkmRepository {
    suspend fun getUkms(token: String): List<Ukm>
    suspend fun createUkm(token: String, ptype: Ukm)
    suspend fun deleteUkm(token: String, id: Long)
}

class NetworkUkmRepository(private val ukmService: UkmService) : UkmRepository {
    override suspend fun getUkms(token: String): List<Ukm> = ukmService.getUkms("Bearer $token")
    override suspend fun createUkm(token: String, ptype: Ukm) = ukmService.createUkm("Bearer $token", ptype)
    override suspend fun deleteUkm(token: String, id: Long) = ukmService.deleteUkm("Bearer $token", id)
}