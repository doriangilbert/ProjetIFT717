package com.example.projetift717.network

import com.example.projetift717.model.User
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.model.requests.LoginResponse
import com.example.projetift717.model.requests.RegisterRequest
import retrofit2.Response

import retrofit2.http.*

interface UserService {
    @GET("/users")
    suspend fun fetchAllUsers(@Header("Authorization") token: String): Response<List<User>>

    @GET("/users/{id}")
    suspend fun fetchUserById(@Header("Authorization") token: String, @Path("id") id: String): Response<User>

    @POST("/users")
    suspend fun createUser(@Header("Authorization") token: String, @Body user: User): Response<User>

    @POST("/users/login")
    suspend fun login(@Body user: LoginRequest): Response<LoginResponse>

    @POST("/users/register")
    suspend fun register(@Header("Authorization") token: String, @Body user: RegisterRequest): Response<User>

    @POST("/users/logout")
    suspend fun logout(@Body user: User): Response<User>

    @PUT("/users/{id}")
    suspend fun updateUser(@Header("Authorization") token: String, @Path("id") id: String, @Body user: User): Response<User>

    @DELETE("/users/{id}")
    suspend fun deleteUser(@Header("Authorization") token: String, @Path("id") id: String): Response<User>
}