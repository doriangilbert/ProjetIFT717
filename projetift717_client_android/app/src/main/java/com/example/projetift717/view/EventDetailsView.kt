package com.example.projetift717.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.EventDetailsViewModel


@Composable
fun EventDetailsView(viewModel: EventDetailsViewModel, navController: NavController, eventId: String) {
    viewModel.fetchEvent(eventId)
    val event by viewModel.event.collectAsState()

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
                Text(text = "Date: $formattedDate")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.price}$")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Address: ${it.address}")
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("EventDetailsView/${eventId}/PaymentView") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Procéder au paiement")
            }
        }
    }
    Footer(navController = navController)
}