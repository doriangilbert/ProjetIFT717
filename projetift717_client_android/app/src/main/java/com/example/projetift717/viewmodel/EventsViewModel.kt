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

// Les donnees pour la page affichant la liste des evenements
class EventsViewModel(private val eventRepository: EventRepository, private val userRepository: UserRepository) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        fetchAllEvents()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            val userId = getUserId()
            if (userId != null) {
                _user.value = userRepository.fetchUserById(userId)
            }
        }
    }

    fun getUserId(): String? {
        return userRepository.getUserId()
    }

    fun fetchAllEvents() {
        viewModelScope.launch {
            val eventList = eventRepository.fetchAllEvents()
            if (eventList != null) {
                _events.value = eventList
            }
        }
    }
}