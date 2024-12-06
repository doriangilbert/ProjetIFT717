package com.example.projetift717.model.requests

import com.example.projetift717.model.User

data class LoginResponse(val token: String, val user: User)