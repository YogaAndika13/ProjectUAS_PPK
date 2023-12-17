package com.polstat.ukm.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.polstat.ukm.service.PengajuanService
import com.polstat.ukm.service.UkmService
import com.polstat.ukm.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val userRepository: UserRepository
    val ukmRepository: UkmRepository
    val pengajuanRepository: PengajuanRepository
}

class DefaultAppContainer() : AppContainer {
    // kos ade
    private val baseUrl = "http://192.168.1.21:8081"
    //kos shela
//    private val baseUrl = "http://192.168.1.23:8080"
    //kfc
//    private val baseUrl = "http://10.237.41.211:8081"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
    private val ukmService: UkmService by lazy {
        retrofit.create(UkmService::class.java)
    }

    private val pengajuanService: PengajuanService by lazy {
        retrofit.create(PengajuanService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userService)
    }

    override val ukmRepository: UkmRepository by lazy {
        NetworkUkmRepository(ukmService)
    }

    override val pengajuanRepository: PengajuanRepository by lazy {
        NetworkPengajuanRepository(pengajuanService)
    }
}