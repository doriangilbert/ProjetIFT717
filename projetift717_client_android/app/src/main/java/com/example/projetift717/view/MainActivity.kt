package com.example.projetift717.view

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// import de l'Instance Retrofit pour obtenir toutes les routes et les utiliser
import com.example.projetift717.network.RetrofitInstance

// import des differents repository
import com.example.projetift717.repository.EventRepository
import com.example.projetift717.repository.PlaceRepository
import com.example.projetift717.repository.UserRepository
import com.example.projetift717.repository.ChatRepository

// import des differents viewmodel
import com.example.projetift717.viewmodel.EventDetailsViewModel
import com.example.projetift717.viewmodel.PlacesListViewModel
import com.example.projetift717.viewmodel.ProfileViewModel
import com.example.projetift717.viewmodel.EventsViewModel
import com.example.projetift717.viewmodel.UserViewModel
import com.example.projetift717.viewmodel.ChatViewModel

class MainActivity : ComponentActivity() {
    // Les repository
    private val eventRepository = EventRepository()
    private val placeRepository = PlaceRepository()
    private lateinit var userRepository: UserRepository
    private val chatRepository = ChatRepository()

    // Les viewmodel
    private val eventDetailsViewModel = EventDetailsViewModel(eventRepository)
    private val eventsViewModel = EventsViewModel(eventRepository)
    private val placesListViewModel = PlacesListViewModel(placeRepository)
    private lateinit var profileViewModel: ProfileViewModel
    private val chatViewModel = ChatViewModel(chatRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRepository = UserRepository(this)
        profileViewModel = ProfileViewModel(userRepository)
        val userVM = UserViewModel(userRepository)

        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                //On renseigne tous les chemins pour naviguer de page en page.
                //On commence par aller à la page ou il y a la liste des lieux.
                NavHost(navController, startDestination = "EventsView") {
                    composable("EventsView") {
                        EventsView(viewModel = eventsViewModel, navController = navController)
                    }
                    composable("ProfileView") {
                        ProfileView(viewModel = profileViewModel, navController = navController)
                    }
                    composable("ChatBotView") {
                        ChatBotView(viewModel = chatViewModel, navController = navController)
                    }
                    composable("PlacesListView") {
                        PlacesListView(viewModel = placesListViewModel, navController = navController)
                    }
                }

                if (navController.currentBackStackEntry?.destination?.route != "Login") {
                    Footer(navController = navController)
                }
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