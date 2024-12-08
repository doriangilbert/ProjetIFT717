package com.example.projetift717.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

// Un lieu que l'on peut visiter dans la ville de Sherbrooke
data class Place (
    // L'identifiant unique de l'endroit
    @SerializedName("_id") val id: String,
    // Le nom de l'endroit
    val name: String,
    // L'adresse de l'endroit
    val address: String,
    // Des coordonnées latitude et longitude pour l'emplacement de l'endroit
    val latitude: Double,
    val longitude: Double,
    // Une enumeration des types d'endroit que l'on peut rencontrer
    val type: PlaceType,
    // L'horaire d'ouverture
    // Concerne les lieux comme les restaurants, les bars, les musees.
    val openingHours: LocalDate,
    val closingHours: LocalDate,
    // La meilleure heure pour visiter ce lieu et profiter de sa plus belle vue.
    // Concerne les lieux en pleine nature: parcs, montagnes, lacs.
    val preferredTime: LocalDate
)