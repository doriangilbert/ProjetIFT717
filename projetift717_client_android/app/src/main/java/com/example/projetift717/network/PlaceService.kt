package com.example.projetift717.network

import com.example.projetift717.model.Place
import retrofit2.Response
import retrofit2.http.*

interface PlaceService {
    @GET("/places")
    suspend fun fetchAllPlaces(@Header("Authorization") token: String): Response<List<Place>>

    @GET("/places/{id}")
    suspend fun fetchPlaceById(@Header("Authorization") token: String, @Path("id") id: String): Response<Place>

    @POST("places")
    suspend fun createPlace(@Header("Authorization") token: String, @Body place: Place): Response<Place>

    @PUT("/places/{id}")
    suspend fun updatePlace(@Header("Authorization") token: String, @Path("id") id: String, @Body place: Place): Response<Place>

    @DELETE("/places/{id}")
    suspend fun deletePlace(@Header("Authorization") token: String, @Path("id") id: String): Response<Place>
}