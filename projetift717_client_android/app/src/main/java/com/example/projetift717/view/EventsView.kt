package com.example.projetift717.view


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

import com.example.projetift717.model.Event
import com.example.projetift717.viewmodel.EventsViewModel

@Composable
fun EventsView(viewModel: EventsViewModel, navController: NavController) {
    //val events by viewModel.events.collectAsState()

    Footer()
}

