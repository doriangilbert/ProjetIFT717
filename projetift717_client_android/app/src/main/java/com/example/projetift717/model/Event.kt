package com.example.projetift717.model

// Un evenement qui se deroule dans la ville de Sherbrooke
data class Event (
    // L'identifiant unique de l'evenement
    val id: String,
    // Le nom de l'evenement
    val name: String,
    // Le prix d'inscription pour l'evenement
    val price: Float,
    // La description de l'evenement
    val description: String,
    // La date de l'evenement
    val date: String,
    // L'adresse de l'evenement
    val address: String,
    // La liste des utilisateurs inscrits a l'evenement
    val users: List<String>
)