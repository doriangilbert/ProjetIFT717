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

// Les donnees pour la page affichant la liste des evenements
class PlacesListViewModel(private val repository: PlaceRepository) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places = _places.asStateFlow()

    init {
        fetchAllEvents()
    }

    private fun fetchAllEvents() {
        viewModelScope.launch {
            _places.value = repository.fetchAllPlaces()
        }
    }
}