package com.example.projetift717.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.projetift717.model.Place
import com.example.projetift717.repository.PlaceRepository
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

// Les donnees pour la page affichant la liste des lieux
class PlacesListViewModel(private val placeRepo: PlaceRepository) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places = _places.asStateFlow()

    init {
        fetchAllPlaces()
    }

    fun fetchAllPlaces() {
        viewModelScope.launch {
            val places = placeRepo.fetchAllPlaces()
            if (places != null) {
                _places.value = places
            }
        }
    }

    fun sortByDistance(context: Context) {
        viewModelScope.launch {
            try {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

                fusedLocationClient.lastLocation.addOnSuccessListener{ location ->
                    val sortedPlaces = _places.value.sortedBy { place ->
                        val placeLocation = Location("").apply {
                            latitude = place.latitude
                            longitude = place.longitude
                        }
                        location.distanceTo(placeLocation)
                    }
                    _places.value = sortedPlaces
                }
            }
            catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    fun sortByName() {
        viewModelScope.launch {
            _places.value = _places.value.sortedBy { it.name }
        }
    }

    fun sortByPreferredDate() {
        viewModelScope.launch {
            _places.value = _places.value.sortedBy { it.preferredTime }
        }
    }
}