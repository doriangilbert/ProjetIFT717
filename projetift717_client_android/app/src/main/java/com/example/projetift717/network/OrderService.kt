package com.example.projetift717.network

import com.example.projetift717.model.Order
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {
    @GET("/orders")
    suspend fun fetchAll(): List<Order>

    @GET("/orders/{id}")
    suspend fun fetch(@Path("id") id: String): Order

    @POST("/orders")
    suspend fun create(@Body order: Order): Order

    @PUT("/orders/{id}")
    suspend fun update(@Path("id") id: String, @Body order: Order): Order

    @DELETE("/orders/{id}")
    suspend fun delete(@Path("id") id: String): Order
}