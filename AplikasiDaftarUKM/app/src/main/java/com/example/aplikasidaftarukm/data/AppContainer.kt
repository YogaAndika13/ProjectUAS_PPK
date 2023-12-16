package com.example.aplikasidaftarukm.data

import com.example.aplikasidaftarukm.service.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val userRepository: UserRepository
//    val problemTypeRepository: ProblemTypeRepository
}

class DefaultAppContainer() : AppContainer {
    private val baseUrl = "http://192.168.1.13:8080"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
//    private val problemTypeService: ProblemTypeService by lazy {
//        retrofit.create(ProblemTypeService::class.java)
//    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userService)
    }

//    override val problemTypeRepository: ProblemTypeRepository by lazy {
//        NetworkProblemTypeRepository(problemTypeService)
//    }
}