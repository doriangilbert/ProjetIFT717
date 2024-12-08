package com.example.projetift717.view

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.EventDetailsViewModel

@Composable
fun EventDetailsView(viewModel: EventDetailsViewModel, navController: NavController, eventId: String) {
    viewModel.fetchEvent(eventId)
    val event by viewModel.event.collectAsState()

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp) // Adjust this value based on the height of the footer
        ) {
            event?.let {
                Text(text = "Name: ${it.name}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: ${it.description}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date: ${it.date}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Price: ${it.price}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Address: ${it.address}")
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("PaymentView") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Go to Payment")
            }
        }
    }
}