package com.example.projetift717.network

import com.example.projetift717.model.User
import com.example.projetift717.network.requests.LoginRequest
import com.example.projetift717.network.requests.LoginResponse

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserService {
    @GET("/users")
    suspend fun fetchAll(): List<User>

    @GET("/users/{id}")
    suspend fun fetch(@Path("id") id: String): User

    @POST("/users")
    suspend fun create(@Body user: User): User

    @POST("/users/login")
    suspend fun login(@Body user: LoginRequest): LoginResponse

    @POST("/users/logout")
    suspend fun logout(@Body user: User): User

    @PUT("/users/{id}")
    suspend fun update(@Path("id") id: String, @Body user: User): User

    @DELETE("/users/{id}")
    suspend fun delete(@Path("id") id: String): User
}