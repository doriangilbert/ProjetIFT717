package com.example.projetift717.network

import com.example.projetift717.model.User
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.model.requests.LoginResponse

import retrofit2.http.*

interface UserService {
    @GET("/users")
    suspend fun fetchAllUsers(@Header("Authorization") token: String): List<User>

    @GET("/users/{id}")
    suspend fun fetchUserById(@Header("Authorization") token: String, @Path("id") id: String): User

    @POST("/users")
    suspend fun createUser(@Header("Authorization") token: String, @Body user: User): User

    @POST("/users/login")
    suspend fun login(@Body user: LoginRequest): LoginResponse

    @POST("/users/logout")
    suspend fun logout(@Body user: User): User

    @PUT("/users/{id}")
    suspend fun updateUser(@Header("Authorization") token: String, @Path("id") id: String, @Body user: User): User

    @DELETE("/users/{id}")
    suspend fun deleteUser(@Header("Authorization") token: String, @Path("id") id: String): User
}