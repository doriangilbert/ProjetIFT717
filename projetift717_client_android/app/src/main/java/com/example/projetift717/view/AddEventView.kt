package com.example.projetift717.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.model.Event
import com.example.projetift717.viewmodel.AddEventViewModel
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

@Composable
fun AddEventView(viewModel: AddEventViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = {
                if (name.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && price.isNotEmpty() && address.isNotEmpty()) {
                    val event = Event(
                        id = ObjectId().toString(),
                        name = name,
                        description = description,
                        date = date,
                        price = price.toFloat(),
                        address = address,
                        users = emptyList()
                    )
                    coroutineScope.launch {
                        viewModel.addEvent(event)
                        navController.navigate("EventsView")
                    }
                } else {
                    errorMessage = "All fields must be filled"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add Event")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Footer(navController = navController)
    }
}