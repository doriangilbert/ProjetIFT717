package com.example.projetift717.viewmodel

import com.example.projetift717.model.Event
import com.example.projetift717.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddEventViewModel(private val eventRepository: EventRepository) {

    suspend fun addEvent(event: Event): Event? {
        return withContext(Dispatchers.IO) {
            eventRepository.createEvent(event)
        }
    }
}