package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// Les coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Les imports des fichiers du projet
import com.example.projetift717.model.Event
import com.example.projetift717.model.User
import com.example.projetift717.repository.EventRepository
import com.example.projetift717.repository.UserRepository

// Les donnees pour la page affichant les details d'un evenement
class EventDetailsViewModel(private val eventRepository: EventRepository, private val userRepository: UserRepository) : ViewModel() {
    private val _event = MutableStateFlow<Event?>(null)
    val event = _event.asStateFlow()
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()
    private val _events = MutableStateFlow<List<Event>?>(null)
    val events = _events.asStateFlow()

    fun fetchProfile() {
        viewModelScope.launch {
            val userId = getUserId()
            if (userId != null) {
                _user.value = userRepository.fetchUserById(userId)
                fetchEvents()
            }
        }
    }

    fun fetchEvents() {
        viewModelScope.launch {
            println(_user.value)
            if (_user.value != null) {
                _events.value = eventRepository.fetchEventsByUserId(_user.value!!.id)
            }
        }
    }

    fun fetchEvent(eventId: String) {
        viewModelScope.launch {
            _event.value = eventRepository.fetchEventById(eventId)
        }
    }

    fun addUserToEvent(eventId: String, userId: String) {
        viewModelScope.launch {
            eventRepository.addUserToEvent(eventId, userId)
        }
    }

    fun getUserId(): String? {
        return userRepository.getUserId()
    }
}