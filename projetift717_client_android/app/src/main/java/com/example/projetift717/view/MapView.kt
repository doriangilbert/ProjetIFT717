package com.example.projetift717.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission", "PermissionLaunchedDuringComposition")
@Composable
fun MapView(navController: NavHostController) {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    val defaultLocation = GeoPoint(45.4042, -71.8929) // Sherbrooke
    var mapView by remember { mutableStateOf<MapView?>(null) }

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                currentLocation = location
            }
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
                    mapView.overlays.add(currentLocationMarker)
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
                        mapView?.overlays?.clear()
                        val marker = Marker(mapView)
                        marker.position = centerPoint
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        mapView?.overlays?.add(marker)
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

    if (!locationPermissionState.status.isGranted) {
        LaunchedEffect(Unit) {
            locationPermissionState.launchPermissionRequest()
        }
    }
    Footer(navController = navController)
}