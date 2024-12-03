package com.example.projetift717.network

import com.example.projetift717.model.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {
    @GET("/events")
    suspend fun fetchAll(): List<Event>

    @GET("/events/{id}")
    suspend fun fetch(@Path("id") id: String): Event

    @POST("/events")
    suspend fun create(@Body event: Event): Event

    @PUT("/events/{id}")
    suspend fun update(@Path("id") id: String, @Body event: Event): Event

    @POST("/events/{id}")
    suspend fun delete(@Path("id") id: String): Event
}