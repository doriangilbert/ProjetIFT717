package com.example.projetift717.repository

//import io.github.cdimascio.dotenv.Dotenv

import android.util.Log
import com.example.projetift717.model.Event
import com.example.projetift717.network.RetrofitInstance

class EventRepository {
    //private val token: String = Dotenv.load().get("API")
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    private val api = RetrofitInstance.eventService

    suspend fun fetchAllEvents(): List<Event>? {
        return try {
            val response = api.fetchAllEvents("Bearer $token")
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchEventById(id: String): Event? {
        return try {
            val response = api.fetchEventById("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchEventsByUserId(userId: String): List<Event>? {
        return try {
            val response = api.fetchEventsByUserId("Bearer $token", userId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createEvent(event: Event): Event? {
        return try {
            val response = api.createEvent("Bearer $token", event)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateEvent(id: String, event: Event): Event? {
        return try {
            val response = api.updateEvent("Bearer $token", id, event)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteEvent(id: String): Event? {
        return try {
            val response = api.deleteEvent("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun addUserToEvent(id: String, userId: String): Event? {
        return try {
            val response = api.addUserToEvent("Bearer $token", id, mapOf("userId" to userId))
            Log.d("EventRepository", "addUserToEvent: $response")
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}