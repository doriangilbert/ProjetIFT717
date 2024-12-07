package com.example.projetift717.repository

//import io.github.cdimascio.dotenv.Dotenv

import com.example.projetift717.model.Event
import com.example.projetift717.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventRepository{

    //private val token: String = Dotenv.load().get("API")
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    private val api = RetrofitInstance.eventService

    suspend fun fetchAllEvents(): List<Event> = withContext(Dispatchers.IO) {
        api.fetchAllEvents("Bearer $token")
    }

    suspend fun fetchEventById(id: String): Event = withContext(Dispatchers.IO) {
        api.fetchEventById("Bearer $token", id)
    }

    suspend fun createEvent(event: Event): Event = withContext(Dispatchers.IO) {
        api.createEvent("Bearer $token", event)
    }

    suspend fun updateEvent(id: String, event: Event): Event = withContext(Dispatchers.IO) {
        api.updateEvent("Bearer $token", id, event)
    }

    suspend fun deleteEvent(id: String): Event = withContext(Dispatchers.IO) {
        api.deleteEvent("Bearer $token", id)
    }
}