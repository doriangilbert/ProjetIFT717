package com.example.projetift717.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.EventDetailsViewModel


@Composable
fun EventDetailsView(viewModel: EventDetailsViewModel, navController: NavController, eventId: String) {
    viewModel.fetchEvent(eventId)
    viewModel.fetchProfile()
    val event by viewModel.event.collectAsState()
    val user by viewModel.user.collectAsState()
    val events by viewModel.events.collectAsState()

    val isUserRegistered = events?.any { it.id == eventId } == true

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            event?.let {

                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = LocalDate.parse(it.date.substring(0, 10)).format(dateFormatter)

                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 30.sp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Date : $formattedDate")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.price}$")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Adresse : ${it.address}")
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { if (!isUserRegistered) navController.navigate("EventDetailsView/${eventId}/PaymentView") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isUserRegistered) Color.Gray else Color(0xFF006400)
                ),
                enabled = !isUserRegistered
            ) {
                Text(if (isUserRegistered) "Vous êtes déjà inscrit" else "Procéder au paiement")
            }
        }
    }
    Footer(navController = navController)
}