package com.example.projetift717.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetift717.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {
    private val _chatHistory = MutableStateFlow<List<Pair<String, Boolean>>>(emptyList())
    val chatHistory = _chatHistory.asStateFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchChat(message)
                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    _chatHistory.value = _chatHistory.value + Pair("Vous : $message", true) + Pair("IA : ${chatResponse?.message}", false)
                } else {
                    _chatHistory.value = _chatHistory.value + Pair("Erreur : ${response.errorBody()?.string()}", false)
                }
            } catch (e: Exception) {
                _chatHistory.value = _chatHistory.value + Pair("Erreur : ${e.message}", false)
            }
        }
    }
}