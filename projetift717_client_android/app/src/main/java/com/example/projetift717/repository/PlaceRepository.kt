package com.example.projetift717.repository

import com.example.projetift717.model.Place
//import io.github.cdimascio.dotenv.Dotenv
import com.example.projetift717.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRepository {

    //private val token: String = Dotenv.load().get("API")
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    private val api = RetrofitInstance.placeService

    suspend fun fetchAllPlaces(): List<Place> = withContext(Dispatchers.IO) {
        api.fetchAllPlaces("Bearer $token")
    }

    suspend fun fetchPlaceById(id: String): Place = withContext(Dispatchers.IO) {
        api.fetchPlaceById("Bearer $token", id)
    }

    suspend fun createPlace(place: Place): Place = withContext(Dispatchers.IO) {
        api.createPlace("Bearer $token", place)
    }

    suspend fun updatePlace(id: String, place: Place): Place = withContext(Dispatchers.IO) {
        api.updatePlace("Bearer $token", id, place)
    }

    suspend fun deletePlace(id: String): Place = withContext(Dispatchers.IO) {
        api.deletePlace("Bearer $token", id)
    }
}