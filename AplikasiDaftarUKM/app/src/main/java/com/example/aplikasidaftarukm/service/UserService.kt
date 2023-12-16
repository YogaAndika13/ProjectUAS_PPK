package com.example.aplikasidaftarukm.service


import com.example.aplikasidaftarukm.model.ChangePasswordForm
import com.example.aplikasidaftarukm.model.LoginForm
import com.example.aplikasidaftarukm.model.LoginResponse
import com.example.aplikasidaftarukm.model.RegisterForm
import com.example.aplikasidaftarukm.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @POST("/register")
    suspend fun register(@Body user: RegisterForm)

    @POST("/login")
    suspend fun login(@Body form: LoginForm): LoginResponse

    @GET("/profile")
    suspend fun getProfile(@Header("Authorization") token: String): User

    @PUT("/profile")
    suspend fun updateProfile(@Header("Authorization") token: String, @Body user: User): User

    @PUT("/profile/password")
    suspend fun updatePassword(@Header("Authorization") token: String, @Body form: ChangePasswordForm): User

    @DELETE("/profile")
    suspend fun deleteProfile(@Header("Authorization") token: String)

    @GET("/user")
    suspend fun getAllUsers(@Header("Authorization") token: String): List<User>

    @DELETE("/user/{id}")
    suspend fun deleteUser(@Header("Authorization") token: String, @Path("id") id: Long)

    @GET("/user/{id}")
    suspend fun getUserById(@Header("Authorization") token: String, @Path("id") id: Long): User

}