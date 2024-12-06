package com.example.projetift717.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// import des differents repository
import com.example.projetift717.repository.EventRepository
import com.example.projetift717.repository.PlaceRepository
import com.example.projetift717.repository.UserRepository

// import des differents viewmodel
import com.example.projetift717.viewmodel.EventDetailsViewModel
import com.example.projetift717.viewmodel.EventsViewModel
import com.example.projetift717.viewmodel.PlacesListViewModel
import com.example.projetift717.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    // Les repository
    private val eventRepository = EventRepository()
    private val placeRepository = PlaceRepository()
    private val userRepository = UserRepository()

    // Les viewmodel
    private val eventViewModel = EventsViewModel(eventRepository)
    private val restaurantViewModel = PlaceRepository()
    private val restaurantDetailViewModel = UserRepository()

    private val restaurantRepository = RestaurantRepository()
    private val restaurantViewModel = RestaurantViewModel(restaurantRepository)
    private val restaurantDetailViewModel = RestaurantDetailViewModel(restaurantRepository)
    private val menuItemRepository = MenuItemRepository()
    private val menuItemDetailViewModel = MenuItemDetailViewModel(menuItemRepository)

    /*@Composable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                //On renseigne tous les chemins pour naviguer de page en page.
                //On commence par aller à la page d'accueil, ou il y a la liste des restaurants.
                NavHost(navController, startDestination = "MainScreen") {
                    composable("MainScreen") {
                        MainScreen(viewModel = restaurantViewModel, navController = navController)
                    }
                    composable("RestaurantDetailView/{restaurantId}") { backStackEntry ->
                        val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                        if (restaurantId != null) {
                            RestaurantDetailView(
                                viewModel = restaurantDetailViewModel,
                                navController = navController,
                                restaurantId = restaurantId
                            )
                        }
                    }
                    composable("RestaurantDetailView/{restaurantId}/MenuItemDetailView/{menuItemId}") { backStackEntry ->
                        val restaurantId = backStackEntry.arguments?.getString("restaurantId")
                        val menuItemId = backStackEntry.arguments?.getString("menuItemId")
                        if (restaurantId != null && menuItemId != null) {
                            MenuItemDetailView(
                                viewModel = menuItemDetailViewModel,
                                navController = navController,
                                restaurantId = restaurantId,
                                menuItemId = menuItemId
                            )
                        }
                    }

                    composable("CartView") {
                        CartView(viewModel = restaurantDetailViewModel, navController = navController)
                    }
                    /*composable("Login") {
                        LoginScreen(navController)
                    }*/
                    // Ajouter d'autres destinations
                }
            }
        }
    }*/
}