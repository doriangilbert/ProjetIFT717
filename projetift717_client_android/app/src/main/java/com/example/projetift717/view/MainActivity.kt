package com.example.projetift717.view

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetift717.model.User

// import de l'Instance Retrofit pour obtenir toutes les routes et les utiliser
import com.example.projetift717.network.RetrofitInstance

// import des differents repository
import com.example.projetift717.repository.EventRepository
import com.example.projetift717.repository.PlaceRepository
import com.example.projetift717.repository.UserRepository

// import des differents viewmodel
import com.example.projetift717.viewmodel.EventDetailsViewModel
import com.example.projetift717.viewmodel.PlacesListViewModel
import com.example.projetift717.viewmodel.ProfileViewModel
import com.example.projetift717.viewmodel.EventsViewModel
import com.example.projetift717.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    // Les repository
    private lateinit var eventRepository: EventRepository
    private lateinit var placeRepository: PlaceRepository

    private lateinit var userRepository: UserRepository

    // Les viewmodel
    private lateinit var eventDetailsViewModel: EventDetailsViewModel
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var placesListViewModel: PlacesListViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventRepository = EventRepository()
        placeRepository = PlaceRepository()
        userRepository = UserRepository(this)

        eventDetailsViewModel = EventDetailsViewModel(eventRepository)
        eventsViewModel = EventsViewModel(eventRepository)
        placesListViewModel = PlacesListViewModel(placeRepository)
        profileViewModel = ProfileViewModel(userRepository)
        userViewModel = UserViewModel(userRepository)

        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                //On renseigne tous les chemins pour naviguer de page en page.
                //On commence par aller à la page ou il y a la liste des lieux.
                NavHost(navController, startDestination = "Login") {
                    composable("Login") {
                        LoginScreen(userViewModel, navController = navController)
                    }
                    composable("Register") {
                        RegisterScreen(userViewModel, navController = navController)
                    }

                    composable("EventsView") {
                        EventsView(viewModel = eventsViewModel, navController = navController)
                    }
                    composable("ProfileView") {
                        ProfileView(viewModel = profileViewModel, navController = navController)
                    }
                    composable("ChatBotView") {
                        ChatBotView(navController = navController)
                    }
                    composable("PlacesListView") {
                        PlacesListView(viewModel = placesListViewModel, navController = navController)
                    }
                }
                Footer(navController = navController)
            }
        }

        // Pour retirer la barre de navigation lorsque l'on est dans l'application

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }
}