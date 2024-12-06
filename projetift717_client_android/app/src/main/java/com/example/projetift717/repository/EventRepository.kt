package com.example.projetift717.repository

import com.example.projetift717.model.Event
import com.example.projetift717.network.EventService

class EventRepository(private val eventService: EventService) {
    suspend fun fetchAll(): List<Event> {
        return eventService.fetchAll()
    }

    suspend fun fetchById(id: String): Event {
        return eventService.fetch(id)
    }

    suspend fun create(event: Event): Event {
        return eventService.create(event)
    }

    suspend fun update(id: String, event: Event): Event {
        return eventService.update(id, event)
    }

    suspend fun delete(id: String): Event {
        return eventService.delete(id)
    }
}