package com.example.projetift717.repository

import com.example.projetift717.model.User
import com.example.projetift717.network.UserService
import com.example.projetift717.model.requests.LoginRequest
import com.example.projetift717.model.requests.LoginResponse

class UserRepository(private val userService: UserService) {
    suspend fun fetchAllUsers(): List<User> {
        return userService.fetchAll()
    }

    suspend fun fetchUserById(id: String): User {
        return userService.fetch(id)
    }

    suspend fun createUser(user: User): User {
        return userService.create(user)
    }

    suspend fun updateUser(id: String, user: User): User {
        return userService.update(id, user)
    }

    suspend fun deleteUser(id: String): User {
        return userService.delete(id)
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return userService.login(request)
    }

    suspend fun logout(user: User): User {
        return userService.logout(user)
    }
}