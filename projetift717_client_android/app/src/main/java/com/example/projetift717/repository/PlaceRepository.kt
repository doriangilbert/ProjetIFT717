package com.example.projetift717.repository

import com.example.projetift717.network.PlaceService
import com.example.projetift717.model.Place
import io.github.cdimascio.dotenv.Dotenv

class PlaceRepository(private val placeService: PlaceService) {

    //private val token: String = Dotenv.load().get("API")
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    suspend fun fetchAllPlaces(): List<Place> {
        return placeService.fetchAllPlaces(token)
    }

    suspend fun fetchPlaceById(id: String): Place {
        return placeService.fetchPlaceById(token,id)
    }

    suspend fun createPlace(place: Place): Place {
        return placeService.createPlace(token,place)
    }

    suspend fun updatePlace(id: String, place: Place): Place {
        return placeService.updatePlace(token,id, place)
    }

    suspend fun deletePlace(id: String): Place {
        return placeService.deletePlace(token,id)
    }
}