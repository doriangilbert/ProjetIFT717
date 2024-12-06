package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// Les coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Les imports des fichiers du projet
import com.example.projetift717.model.Event
import com.example.projetift717.repository.EventRepository

// Les donnees pour la page affichant les details d'un evenement
class EventDetailsViewModel(private val repository: EventRepository) : ViewModel() {
    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()

    fun fetchEvent(eventId: String) {
        viewModelScope.launch {
            _event.value = repository.fetchEventById(eventId)
        }
    }
}