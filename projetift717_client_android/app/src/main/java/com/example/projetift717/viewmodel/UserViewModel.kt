package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repo: UserRepository) : ViewModel() {
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = repo.login(LoginRequest(email, password))
            if (response.token.isNotEmpty()) {
                repo.saveToken(response.token)
            }
        }
    }
}