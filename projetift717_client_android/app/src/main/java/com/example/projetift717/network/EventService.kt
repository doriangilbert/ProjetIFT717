package com.example.projetift717.network

import com.example.projetift717.model.Event
import retrofit2.Response
import retrofit2.http.*

interface EventService {
    @GET("/events")
    suspend fun fetchAllEvents(@Header("Authorization") token: String): Response<List<Event>>

    @GET("/events/{id}")
    suspend fun fetchEventById(@Header("Authorization") token: String, @Path("id") id: String): Response<Event>

    @GET("/events/user/{id}")
    suspend fun fetchEventsByUserId(@Header("Authorization") token: String, @Path("id") id: String): Response<List<Event>>

    @POST("/events")
    suspend fun createEvent(@Header("Authorization") token: String, @Body event: Event): Response<Event>

    @PUT("/events/{id}")
    suspend fun updateEvent(@Header("Authorization") token: String, @Path("id") id: String, @Body event: Event): Response<Event>

    @POST("/events/{id}")
    suspend fun deleteEvent(@Header("Authorization") token: String, @Path("id") id: String): Response<Event>
}