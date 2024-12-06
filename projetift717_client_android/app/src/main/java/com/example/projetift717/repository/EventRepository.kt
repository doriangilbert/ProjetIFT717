package com.example.projetift717.repository

import com.example.projetift717.model.Event
import com.example.projetift717.network.EventService

class EventRepository(private val eventService: EventService) {
    suspend fun fetchAllEvents(): List<Event> {
        return eventService.fetchAll()
    }

    suspend fun fetchEventById(id: String): Event {
        return eventService.fetch(id)
    }

    suspend fun createEvent(event: Event): Event {
        return eventService.create(event)
    }

    suspend fun updateEvent(id: String, event: Event): Event {
        return eventService.update(id, event)
    }

    suspend fun deleteEvent(id: String): Event {
        return eventService.delete(id)
    }
}