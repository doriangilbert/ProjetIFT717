package com.example.projetift717.viewmodel

import android.util.Log
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

    /*private fun fetchAllEvents() {
        viewModelScope.launch {
            Log.d("salut", "programme")
            try {
                val eventList = repository.fetchAllEvents()
                _events.value = eventList
                Log.d("EventsViewModel", "Fetched events: $eventList")
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error fetching events", e)
            }
        }
    }*/

    fun fetchAllEvents() {
        viewModelScope.launch {
            val eventList = repository.fetchAllEvents()
            if (eventList != null) {
               _events.value = eventList
                Log.d("EventsViewModel", "Fetched events: $eventList")
            }
        }
    }
}