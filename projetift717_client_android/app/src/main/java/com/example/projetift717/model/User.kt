package com.example.projetift717.model

//import com.google.gson.annotations.SerializedName

data class User (
    //@SerializedName("_id") val id: String,
    // L'identifiant unique de l'utilisateur
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    var password: String,
    var phone: String,
    var isAdmin: Boolean,
    val orders: List<String>
)