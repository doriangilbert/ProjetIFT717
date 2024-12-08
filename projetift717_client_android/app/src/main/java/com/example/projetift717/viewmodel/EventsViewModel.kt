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

// Les donnees pour la page affichant la liste des evenements
class EventsViewModel(private val repository: EventRepository) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    init {
        fetchAllEvents()
    }

    fun fetchAllEvents() {
        viewModelScope.launch {
            val eventList = repository.fetchAllEvents()
            if (eventList != null) {
                _events.value = eventList
            }
        }
    }
}