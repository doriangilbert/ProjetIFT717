package com.example.projetift717.model

import com.google.gson.annotations.SerializedName

// Un evenement qui se deroule dans la ville de Sherbrooke
data class Event (
    // L'identifiant unique de l'evenement
    @SerializedName("_id") val id: String,
    // Le nom de l'evenement
    val name: String,
    // La date de l'evenement
    @SerializedName("date") val date: String,
    // La description de l'evenement
    val description: String,
    // Le prix d'inscription pour l'evenement
    val price: Float,
    // L'adresse de l'evenement
    val address: String,
    // La liste des utilisateurs inscrits a l'evenement
    val users: List<String>
)