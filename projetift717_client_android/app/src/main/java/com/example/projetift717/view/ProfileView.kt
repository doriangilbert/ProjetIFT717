package com.example.projetift717.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.ProfileViewModel

@Composable
fun ProfileView(viewModel: ProfileViewModel, navController: NavController) {

    Footer(navController = navController)
}