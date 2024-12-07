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
import com.example.projetift717.R

@Composable
fun Footer() {
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
                IconButton(onClick = { /* Handle chatbot action */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_robot), contentDescription = "Chatbot")
                }
                IconButton(onClick = { /* Handle place action */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_place), contentDescription = "Place")
                }
                IconButton(onClick = { /* Handle card action */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_map), contentDescription = "Card")
                }
                IconButton(onClick = { /* Handle calendar action */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "Calendar")
                }
                IconButton(onClick = { /* Handle person action */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = "Person")
                }
            }
        }
    }
}