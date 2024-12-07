package com.example.projetift717.repository

import io.github.cdimascio.dotenv.Dotenv

import com.example.projetift717.model.Event
import com.example.projetift717.network.EventService

class EventRepository(private val eventService: EventService) {

    //private val token: String = Dotenv.load().get("API")
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    suspend fun fetchAllEvents(): List<Event> {
        return eventService.fetchAllEvents(token)
    }

    suspend fun fetchEventById(id: String): Event {
        return eventService.fetchEventById(token, id)
    }

    suspend fun createEvent(event: Event): Event {
        return eventService.createEvent(token, event)
    }

    suspend fun updateEvent(id: String, event: Event): Event {
        return eventService.updateEvent(token, id, event)
    }

    suspend fun deleteEvent(id: String): Event {
        return eventService.deleteEvent(token, id)
    }
}