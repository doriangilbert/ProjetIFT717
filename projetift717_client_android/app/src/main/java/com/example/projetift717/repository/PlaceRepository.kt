package com.example.projetift717.repository

import com.example.projetift717.network.PlaceService
import com.example.projetift717.model.Place

class PlaceRepository(private val placeService: PlaceService) {
    suspend fun fetchAll(): List<Place> {
        return placeService.fetchAll()
    }

    suspend fun fetch(id: String): Place {
        return placeService.fetch(id)
    }

    suspend fun create(place: Place): Place {
        return placeService.create(place)
    }

    suspend fun update(id: String, place: Place): Place {
        return placeService.update(id, place)
    }

    suspend fun delete(id: String): Place {
        return placeService.delete(id)
    }
}