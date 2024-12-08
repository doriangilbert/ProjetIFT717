package com.example.projetift717.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.R

@Composable
fun Footer(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF006400))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate("ChatBotView") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_robot), contentDescription = "Chatbot")
                }
                IconButton(onClick = { navController.navigate("PlacesListView") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_place), contentDescription = "Place")
                }
                IconButton(onClick = { navController.navigate("MapView") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_map), contentDescription = "Card")
                }
                IconButton(onClick = { navController.navigate("EventsView") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "Calendar")
                }
                IconButton(onClick = { navController.navigate("ProfileView") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = "Person")
                }
            }
        }
    }
}