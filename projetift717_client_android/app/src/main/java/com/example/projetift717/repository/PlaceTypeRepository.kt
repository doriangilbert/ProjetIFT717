package com.example.projetift717.repository

import com.example.projetift717.network.PlaceTypeService
import com.example.projetift717.model.PlaceType

class PlaceTypeRepository(private val placeTypeService: PlaceTypeService) {
    suspend fun fetchAll(): List<PlaceType> {
        return placeTypeService.fetchAll()
    }

    suspend fun fetch(id: String): PlaceType {
        return placeTypeService.fetch(id)
    }

    suspend fun create(placeType: PlaceType): PlaceType {
        return placeTypeService.create(placeType)
    }

    suspend fun update(id: String, placeType: PlaceType): PlaceType {
        return placeTypeService.update(id, placeType)
    }

    suspend fun delete(id: String): PlaceType {
        return placeTypeService.delete(id)
    }
}