package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// Les imports des fichiers du projet
import com.example.projetift717.model.Event
import com.example.projetift717.repository.EventRepository


class EventsViewModel(private val repository: EventRepository) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    init {
        fetchAllEvents()
    }

    private fun fetchAllEvents() {
        viewModelScope.launch {
            _events.value = repository.fetchAll()
        }
    }
}