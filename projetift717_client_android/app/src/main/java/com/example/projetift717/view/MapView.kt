package com.example.projetift717.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.projetift717.R
import com.example.projetift717.repository.PlaceRepository
import com.example.projetift717.model.Place
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission", "PermissionLaunchedDuringComposition", "UseCompatLoadingForDrawables")
@Composable
fun MapView(navController: NavHostController) {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    val defaultLocation = GeoPoint(45.4042, -71.8929) // Sherbrooke
    var mapView by remember { mutableStateOf<MapView?>(null) }
    val placeRepository = PlaceRepository()
    var places by remember { mutableStateOf<List<Place>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = location
                    println("Current location: ${location.latitude}, ${location.longitude}")
                    mapView?.controller?.setCenter(GeoPoint(location.latitude, location.longitude))
                    mapView?.controller?.setZoom(18.0) // Set the desired zoom level
                    val currentLocationMarker = Marker(mapView)
                    currentLocationMarker.position = GeoPoint(location.latitude, location.longitude)
                    currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    currentLocationMarker.title = "Current Location"
                    currentLocationMarker.icon = context.getDrawable(R.drawable.ic_position)
                    mapView?.overlays?.add(currentLocationMarker)
                } else {
                    println("Failed to retrieve current location")
                }
            }.addOnFailureListener {
                println("Error retrieving location: ${it.message}")
            }
        } else {
            println("Location permission not granted")
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            places = placeRepository.fetchAllPlaces()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    controller.setZoom(15.0)
                    val centerPoint = currentLocation?.let {
                        GeoPoint(it.latitude, it.longitude)
                    } ?: defaultLocation
                    controller.setCenter(centerPoint)
                    mapView = this
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { mapView ->
                mapView.overlays.clear()
                currentLocation?.let {
                    val currentLocationMarker = Marker(mapView)
                    currentLocationMarker.position = GeoPoint(it.latitude, it.longitude)
                    currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    currentLocationMarker.title = "Current Location"
                    currentLocationMarker.icon = context.getDrawable(R.drawable.ic_position)
                    mapView.overlays.add(currentLocationMarker)
                }
                places?.forEach { place ->
                    val placeMarker = Marker(mapView)
                    placeMarker.position = GeoPoint(place.latitude, place.longitude)
                    placeMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    placeMarker.title = place.name
                    placeMarker.setOnMarkerClickListener { _, _ ->
                        selectedPlace = place
                        true
                    }
                    mapView.overlays.add(placeMarker)
                }
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 80.dp)
        ) {
            Button(
                onClick = {
                    currentLocation?.let {
                        val centerPoint = GeoPoint(it.latitude, it.longitude)
                        mapView?.controller?.setCenter(centerPoint)
                        mapView?.controller?.setZoom(18.0) // Set the desired zoom level
                        val currentLocationMarker = Marker(mapView)
                        currentLocationMarker.position = centerPoint
                        currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        currentLocationMarker.title = "Position Actuelle"
                        currentLocationMarker.icon = context.getDrawable(R.drawable.ic_position)
                        mapView?.overlays?.add(currentLocationMarker)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Centrer")
            }

            Button(
                onClick = {
                    mapView?.controller?.zoomIn()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Zoom +")
            }

            Button(
                onClick = {
                    mapView?.controller?.zoomOut()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Zoom -")
            }
        }
    }

    if (selectedPlace != null) {
        AlertDialog(
            onDismissRequest = { selectedPlace = null },
            title = { Text(text = selectedPlace!!.name) },
            text = {
                Column {
                    Text(text = "Adresse: ${selectedPlace!!.address}")
                    Text(text = "Latitude: ${selectedPlace!!.latitude}")
                    Text(text = "Longitude: ${selectedPlace!!.longitude}")
                    Text(text = "Type: ${selectedPlace!!.type}")
                    Text(text = "Heure d'ouverture: ${selectedPlace!!.openingHours}")
                    Text(text = "Heure de fermeture: ${selectedPlace!!.closingHours}")
                    Text(text = "Heure idéale de visite: ${selectedPlace!!.preferredTime}")
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedPlace = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (!locationPermissionState.status.isGranted) {
        LaunchedEffect(Unit) {
            locationPermissionState.launchPermissionRequest()
        }
    }
    Footer(navController = navController)
}