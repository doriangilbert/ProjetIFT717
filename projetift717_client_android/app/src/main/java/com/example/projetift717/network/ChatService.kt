package com.example.projetift717.network

import com.example.projetift717.model.requests.ChatRequest
import com.example.projetift717.model.requests.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatService {
    @POST("chat")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body chatRequest: ChatRequest
    ): Response<ChatResponse>
}