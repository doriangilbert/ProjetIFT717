package com.example.projetift717.repository

import com.example.projetift717.network.PlaceService
import com.example.projetift717.model.Place

class PlaceRepository(private val placeService: PlaceService) {
    suspend fun fetchAllPlaces(): List<Place> {
        return placeService.fetchAll()
    }

    suspend fun fetchPlaceById(id: String): Place {
        return placeService.fetch(id)
    }

    suspend fun createPlace(place: Place): Place {
        return placeService.create(place)
    }

    suspend fun updatePlace(id: String, place: Place): Place {
        return placeService.update(id, place)
    }

    suspend fun deletePlace(id: String): Place {
        return placeService.delete(id)
    }
}