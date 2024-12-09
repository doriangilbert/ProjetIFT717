package com.example.projetift717.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.projetift717.model.Event
import com.example.projetift717.viewmodel.EventsViewModel


@Composable
fun EventsView(viewModel: EventsViewModel, navController: NavController) {
    val events by viewModel.events.collectAsState()
    val user by viewModel.user.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAllEvents()
        viewModel.fetchProfile()
    }
    Column {
        Text(
            text = "Liste des événements",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF4CAF50),
            modifier = Modifier.padding(16.dp)
        )
        EventList(events = events, navController = navController)
        if (user?.isAdmin == true) {
            Button(
                onClick = { navController.navigate("AddEventView") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006400)
                )
            ) {
                Text("Ajouter un événement")
            }
        }
    }
    Footer(navController = navController)
}

@Composable
fun EventList(events: List<Event>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(events) { event ->
            EventItem(event) {
                navController.navigate("EventDetailsView/${event.id}")
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF4CAF50),
            )
            Text(
                text = "Prix: ${event.price}$",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}