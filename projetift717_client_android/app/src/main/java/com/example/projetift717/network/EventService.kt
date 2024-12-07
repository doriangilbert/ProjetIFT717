package com.example.projetift717.network

import com.example.projetift717.model.Event
import retrofit2.http.*

interface EventService {
    @GET("/events")
    suspend fun fetchAllEvents(@Header("Authorization") token: String): List<Event>

    @GET("/events/{id}")
    suspend fun fetchEventById(@Header("Authorization") token: String, @Path("id") id: String): Event

    @POST("/events")
    suspend fun createEvent(@Header("Authorization") token: String, @Body event: Event): Event

    @PUT("/events/{id}")
    suspend fun updateEvent(@Header("Authorization") token: String, @Path("id") id: String, @Body event: Event): Event

    @POST("/events/{id}")
    suspend fun deleteEvent(@Header("Authorization") token: String, @Path("id") id: String): Event
}