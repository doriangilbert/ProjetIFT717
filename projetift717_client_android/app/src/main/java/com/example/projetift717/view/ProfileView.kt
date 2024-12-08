package com.example.projetift717.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.model.Event
import com.example.projetift717.model.User
import com.example.projetift717.viewmodel.ProfileViewModel

@Composable
fun ProfileView(viewModel: ProfileViewModel, navController: NavController) {
    val user by viewModel.user.collectAsState()
    val events by viewModel.events.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchProfile()
    }

    if (user != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Profile(user = user!!)
            Spacer(modifier = Modifier.height(16.dp))
            if (events != null) {
                (events as Iterable<Event?>).forEach { event ->
                    EventItem(
                        event!!,
                        onClick = {
                            navController.navigate("EventDetailsView/${event.id}")
                        }
                    )
                }
            }
            else {
                Text("Pas d'événements")
            }
        }

    }
    Footer(navController = navController)
}

@Composable
fun Profile(user: User) {
    Spacer(modifier = Modifier.height(32.dp))
    Text(text = "Profil", style = MaterialTheme.typography.headlineLarge)
    Spacer(modifier = Modifier.height(16.dp))
    ProfileField(label = "Nom", value = user.name)
    ProfileField(label = "Prénom", value = user.surname)
    ProfileField(label = "Email", value = user.email)
}

@Composable
fun ProfileField(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        BasicTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                .padding(8.dp)
        )
    }
}