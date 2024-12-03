package com.example.projetift717.network

import com.example.projetift717.model.PlaceType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlaceTypeService {
    @GET("/place-types")
    suspend fun fetchAll(): List<PlaceType>

    @GET("/place-types/{id}")
    suspend fun fetch(@Path("id") id: String): PlaceType

    @POST("/place-types")
    suspend fun create(@Body placeType: PlaceType): PlaceType

    @PUT("/place-types/{id}")
    suspend fun update(@Path("id") id: String, @Body placeType: PlaceType): PlaceType

    @DELETE("/place-types/{id}")
    suspend fun delete(@Path("id") id: String): PlaceType
}