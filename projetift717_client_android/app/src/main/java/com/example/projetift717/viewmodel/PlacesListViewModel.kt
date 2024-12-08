package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// Les coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Les imports des fichiers du projet
import com.example.projetift717.model.Place
import com.example.projetift717.repository.PlaceRepository
import kotlin.io.path.Path

// Les donnees pour la page affichant la liste des lieux
class PlacesListViewModel(private val repository: PlaceRepository) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places = _places.asStateFlow()

    init {
        fetchAllPlaces()
    }

    fun fetchAllPlaces() {
        viewModelScope.launch {
            val places = repository.fetchAllPlaces()
            if (places != null) {
                _places.value = places
            }
        }
    }
}