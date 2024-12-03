package com.example.projetift717.repository

import com.example.projetift717.model.User
import com.example.projetift717.network.UserService
import com.example.projetift717.network.requests.LoginRequest
import com.example.projetift717.network.requests.LoginResponse

class UserRepository(private val userService: UserService) {
    suspend fun fetchAll(): List<User> {
        return userService.fetchAll()
    }

    suspend fun fetch(id: String): User {
        return userService.fetch(id)
    }

    suspend fun create(user: User): User {
        return userService.create(user)
    }

    suspend fun update(id: String, user: User): User {
        return userService.update(id, user)
    }

    suspend fun delete(id: String): User {
        return userService.delete(id)
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return userService.login(request)
    }

    suspend fun logout(user: User): User {
        return userService.logout(user)
    }
}