package com.example.projetift717.repository

import com.example.projetift717.model.requests.ChatRequest
import com.example.projetift717.model.requests.ChatResponse
import com.example.projetift717.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ChatRepository {

    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    private val api = RetrofitInstance.chatService

    suspend fun fetchChat(message: String): Response<ChatResponse> = withContext(Dispatchers.IO) {
        val chatRequest = ChatRequest(message)
        api.sendMessage("Bearer $token", chatRequest)
    }
}