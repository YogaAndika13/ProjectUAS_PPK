package com.polstat.ukm.service

import com.polstat.ukm.model.Ukm
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UkmService {
    @GET("/ukm")
    suspend fun getUkms(@Header("Authorization") token: String): List<Ukm>

    @POST("/ukm")
    suspend fun createUkm(@Header("Authorization") token: String, @Body ptype: Ukm)

    @DELETE("/ukm/{id}")
    suspend fun deleteUkm(@Header("Authorization") token: String, @Path("id") id: Long)
}