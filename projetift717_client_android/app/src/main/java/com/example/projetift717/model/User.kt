package com.example.projetift717.model

import com.google.gson.annotations.SerializedName

// Un utilisateur de l'application
data class User (
    // L'identifiant unique de l'utilisateur
    @SerializedName("_id") val id: String,
    // Le nom de l'utilisateur
    val name: String,
    // Le prenom de l'utilisateur
    val surname: String,
    // L'adresse email de l'utilisateur
    val email: String,
    // Le mot de passe de l'utilisateur
    var password: String,
    // Le role de l'utilisateur: administrateur ou simple utilisateur
    var isAdmin: Boolean,
    // La liste des evenements auxquels l'utilisateur est inscrit
    val events: List<String>
)