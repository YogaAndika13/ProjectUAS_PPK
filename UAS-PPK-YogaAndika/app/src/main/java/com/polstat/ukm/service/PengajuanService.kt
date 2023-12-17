package com.polstat.ukm.service

import com.polstat.ukm.model.Pengajuan
import com.polstat.ukm.model.PengajuanForm
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PengajuanService {

    @POST("/pengajuan")
    suspend fun createPengajuan(@Header("Authorization") token: String, @Body pengajuan: PengajuanForm)

    @GET("/pengajuan")
    suspend fun getAllPengajuans(@Header("Authorization") token: String): List<Pengajuan>

    @GET("/user/{userId}/pengajuan")
    suspend fun getPengajuanByUser(@Header("Authorization") token: String, @Path("userId") userId: Long): List<Pengajuan>

    @DELETE("/pengajuan/{pengajuanId}")
    suspend fun deletePengajuan(@Header("Authorization") token: String, @Path("pengajuanId") pengajuanId: Long)

    @PUT("/pengajuan/{pengajuanId}")
    suspend fun updatePengajuan(@Header("Authorization") token: String, @Path("pengajuanId") pengajuanId: Long, @Body pengajuan: Pengajuan)
}