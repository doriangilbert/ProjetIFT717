package com.example.projetift717.viewmodel

import android.R.bool
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetift717.model.User
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.model.requests.LoginResponse
import com.example.projetift717.model.requests.RegisterRequest
import com.example.projetift717.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repo: UserRepository) : ViewModel() {
    val registerResponse = MutableLiveData<User?>()
    val loginResponse = MutableLiveData<LoginResponse?>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginResponse.value = repo.login(LoginRequest(email, password))
        }
    }

    fun register(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(name = name, surname = surname, email = email, password = password)
            registerResponse.value = repo.register(registerRequest)
        }
    }
}