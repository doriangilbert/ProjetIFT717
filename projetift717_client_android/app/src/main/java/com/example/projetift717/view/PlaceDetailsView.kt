package com.example.projetift717.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.PlaceDetailsViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun PlaceDetailsView(viewModel: PlaceDetailsViewModel, placeId: String, navController: NavController) {
    val place by viewModel.place.collectAsState()

    LaunchedEffect(key1 = placeId) {
        viewModel.fetchPlace(placeId)
    }

    place?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = it.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = it.address,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Latitude: ${it.latitude}, Longitude: ${it.longitude}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Type: ${it.type}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            it.openingHours?.let { openingHours ->
                it.closingHours?.let { closingHours ->
                    val openingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(openingHours.seconds.toLong()), ZoneId.systemDefault())
                    val closingDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(closingHours.seconds.toLong()), ZoneId.systemDefault())
                    Text(
                        text = "Opening Hours: ${openingDateTime.format(formatter)} - ${closingDateTime.format(formatter)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            it.preferredTime?.let { preferredTime ->
                val preferredDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(preferredTime.seconds.toLong()), ZoneId.systemDefault())
                Text(
                    text = "Preferred Time: ${preferredDateTime.format(formatter)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            Button(
                onClick = { navController.navigateUp() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Retour")
            }
        }
    }
    Footer(navController = navController)
}