package com.example.projetift717.repository

import android.content.Context
import com.example.projetift717.model.User
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.model.requests.LoginResponse
import com.example.projetift717.model.requests.RegisterRequest
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

    suspend fun fetchAllUsers(): List<User>? {
        return try {
            val response = api.fetchAllUsers("Bearer $token")
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchUserById(id: String): User? {
        return try {
            val response = api.fetchUserById("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun register(request: RegisterRequest): User? {
        return try {
            val response = api.register("Bearer $token", request)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateUser(id: String, user: User): User? {
        return try {
            val response = api.updateUser(token, id, user)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteUser(id: String): User? {
        return try {
            val response = api.deleteUser(token, id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse? {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.token != null) {
                    saveToken(body.token)
                }
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun logout(user: User): User? {
        return try {
            val response = api.logout(user)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getToken(): String? = sharedPreferences.getString("auth_token", null)

    fun saveToken(token: String) = sharedPreferences.edit().putString("auth_token", token).apply()
}