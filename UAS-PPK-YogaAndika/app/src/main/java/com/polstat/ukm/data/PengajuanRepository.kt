package com.polstat.ukm.data

import com.polstat.ukm.model.Pengajuan
import com.polstat.ukm.model.PengajuanForm
import com.polstat.ukm.service.PengajuanService

interface PengajuanRepository {
    suspend fun createPengajuan(token: String, pengajuan: PengajuanForm)
    suspend fun getAllPengajuans(token: String): List<Pengajuan>
    suspend fun getPengajuansByUser(token: String, userId: Long): List<Pengajuan>
    suspend fun deletePengajuan(token: String, pengajuanId: Long)
    suspend fun updatePengajuan(token: String, pengajuanId: Long, pengajuan: Pengajuan)
}

class NetworkPengajuanRepository(private val pengajuanService: PengajuanService) : PengajuanRepository {
    override suspend fun createPengajuan(token: String, pengajuan: PengajuanForm) = pengajuanService.createPengajuan("Bearer $token", pengajuan)
    override suspend fun getAllPengajuans(token: String) = pengajuanService.getAllPengajuans("Bearer $token")
    override suspend fun getPengajuansByUser(token: String, userId: Long) = pengajuanService.getPengajuanByUser("Bearer $token", userId)
    override suspend fun deletePengajuan(token: String, pengajuanId: Long) = pengajuanService.deletePengajuan("Bearer $token", pengajuanId)
    override suspend fun updatePengajuan(token: String, pengajuanId: Long, pengajuan: Pengajuan) = pengajuanService.updatePengajuan("Bearer $token", pengajuanId, pengajuan)
}