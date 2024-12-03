package com.example.projetift717.network

import com.example.projetift717.model.Place
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlaceService {
    @GET("/places")
    suspend fun fetchAll(): List<Place>

    @GET("/places/{id}")
    suspend fun fetch(@Path("id") id: String): Place

    @POST("places")
    suspend fun create(@Body place: Place): Place

    @PUT("/places/{id}")
    suspend fun update(@Path("id") id: String, @Body place: Place): Place

    @DELETE("/places/{id}")
    suspend fun delete(@Path("id") id: String): Place
}