package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetift717.model.Event

// Les coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Les imports des fichiers du projet
import com.example.projetift717.model.User
import com.example.projetift717.repository.EventRepository
import com.example.projetift717.repository.UserRepository

// Les donnees pour la page affichant les details d'un evenement
class ProfileViewModel(private val userRepo: UserRepository, private val eventRepo: EventRepository) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    private val _events = MutableStateFlow<List<Event>?>(null)

    val user = _user.asStateFlow()
    val events = _events.asStateFlow()

    fun fetchProfile() {
        viewModelScope.launch {
            val userId = getUserId()
            if (userId != null) {
                _user.value = userRepo.fetchUserById(userId)
                fetchEvents()
            }
        }
    }

    fun fetchEvents() {
        viewModelScope.launch {
            println(_user.value)
            if (_user.value != null) {
                _events.value = eventRepo.fetchEventsByUserId(_user.value!!.id)
            }
        }
    }

    fun getUserId(): String? {
        return userRepo.getUserId()
    }
}