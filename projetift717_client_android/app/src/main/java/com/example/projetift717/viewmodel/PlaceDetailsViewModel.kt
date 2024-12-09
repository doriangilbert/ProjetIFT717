package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetift717.model.Place
import com.example.projetift717.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PlaceDetailsViewModel(private val placeRepository: PlaceRepository) : ViewModel() {
    private val _place = MutableStateFlow<Place?>(null)
    val place = _place.asStateFlow()

    fun fetchPlace(placeId: String) {
        viewModelScope.launch {
            _place.value = placeRepository.fetchPlaceById(placeId)
        }
    }
}