package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// Les coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Les imports des fichiers du projet
import com.example.projetift717.model.User
import com.example.projetift717.repository.UserRepository

// Les donnees pour la page affichant les details d'un evenement
class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun fetchEvent(userId: String) {
        viewModelScope.launch {
            _user.value = repository.fetchUserById(userId)
        }
    }
}