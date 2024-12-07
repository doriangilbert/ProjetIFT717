package com.example.projetift717.repository

import android.content.Context
import com.example.projetift717.model.User
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.model.requests.LoginResponse
//import com.example.projetift717.model.requests.LoginRequest
//import com.example.projetift717.model.requests.LoginResponse
import io.github.cdimascio.dotenv.Dotenv
import com.example.projetift717.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepository(context: Context) {
    //private val token: String = Dotenv.load().get("API")
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NzUyNTVlZDc2ZjYxNTAzZDE2MDljMGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MzM1MTI0ODV9.LuMTturiWxGS-AQ53JmPfo_eN1MZmsD2-O2ZNPE18Fk"

    private val api = RetrofitInstance.userService
    private val sharedPreferences = context.getSharedPreferences("user_token_prefs", Context.MODE_PRIVATE)

    suspend fun fetchAllUsers(): List<User> = withContext(Dispatchers.IO) {
        api.fetchAllUsers(token)
    }

    suspend fun fetchUserById(id: String): User = withContext(Dispatchers.IO) {
        api.fetchUserById(token, id)
    }

    suspend fun createUser(user: User): User = withContext(Dispatchers.IO) {
        api.createUser(token,user)
    }

    suspend fun updateUser(id: String, user: User): User = withContext(Dispatchers.IO) {
        api.updateUser(token,id, user)
    }

    suspend fun deleteUser(id: String): User = withContext(Dispatchers.IO) {
        api.deleteUser(token, id)
    }

    suspend fun login(request: LoginRequest): LoginResponse = withContext(Dispatchers.IO) {
        api.login(request)
    }

    suspend fun logout(user: User): User {
        return api.logout(user)
    }

    fun getToken(): String? = sharedPreferences.getString("auth_token", null)

    fun saveToken(token: String) = sharedPreferences.edit().putString("auth_token", token).apply()
}