package com.example.projetift717.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetift717.R
import com.example.projetift717.repository.*
import com.example.projetift717.viewmodel.*
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var eventRepository: EventRepository
    private lateinit var placeRepository: PlaceRepository
    private lateinit var userRepository: UserRepository
    private lateinit var chatRepository: ChatRepository

    private lateinit var eventDetailsViewModel: EventDetailsViewModel
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var placesListViewModel: PlacesListViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var chatViewModel: ChatViewModel

    private lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventRepository = EventRepository()
        placeRepository = PlaceRepository()
        userRepository = UserRepository(this)
        chatRepository = ChatRepository()

        eventDetailsViewModel = EventDetailsViewModel(eventRepository, userRepository)
        eventsViewModel = EventsViewModel(eventRepository)
        placesListViewModel = PlacesListViewModel(placeRepository)
        profileViewModel = ProfileViewModel(userRepository, eventRepository)
        userViewModel = UserViewModel(userRepository)
        chatViewModel = ChatViewModel(chatRepository)

        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "Login") {
                    composable("Login") {
                        LoginScreen(userViewModel, navController = navController, context = this@MainActivity)
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
                        ChatBotView(viewModel = chatViewModel, navController = navController)
                    }
                    composable("PlacesListView") {
                        PlacesListView(viewModel = placesListViewModel, navController = navController)
                    }
                    composable("MapView") {
                        MapView(navController = navController)
                    }
                    composable("EventDetailsView/{eventId}") { backStackEntry ->
                        val eventId = backStackEntry.arguments?.getString("eventId")
                        if (eventId != null) {
                            EventDetailsView(viewModel = eventDetailsViewModel, navController = navController, eventId = eventId)
                        }
                    }
                    composable("EventDetailsView/{eventId}/PaymentView") { backStackEntry ->
                        val eventId = backStackEntry.arguments?.getString("eventId")
                        if (eventId != null) {
                            PaymentView(viewModel = eventDetailsViewModel, navController = navController, eventId = eventId)
                        }
                    }
                }
            }
        }

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Channel"
            val descriptionText = "Channel for receiving notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("NOTIFICATION_CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(message: String) {
        val builder = NotificationCompat.Builder(this, "NOTIFICATION_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_map)
            .setContentTitle("ProjetIFT717")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            try {
                notify(1, builder.build())
            } catch (e: SecurityException) {
                Log.e("MainActivity", "Notification permission not granted: ${e.message}")
            }
        }
    }

    fun connectToNotifications() {
        val userId = userRepository.getUserId()
        if (userId != null) {
            try {
                val url = "http://10.0.2.2:8080"
                Log.d("MainActivity", "Connecting to URL: $url")
                mSocket = IO.socket(url)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
                Log.e("MainActivity", "URISyntaxException: ${e.message}")
            }

            mSocket.on(Socket.EVENT_CONNECT) {
                Log.d("MainActivity", "Connected to server")
                mSocket.emit("register", userId)
            }.on("notification") { args ->
                if (args[0] != null) {
                    val data = args[0] as JSONObject
                    Log.d("MainActivity", "Notification received: $data")
                    runOnUiThread {
                        handleNotification(data)
                    }
                }
            }.on(Socket.EVENT_DISCONNECT) {
                Log.d("MainActivity", "Disconnected from server")
            }

            mSocket.connect()
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleNotification(data: JSONObject) {
        val type = data.getString("type")
        val event = data.getJSONObject("event")
        val dateString = event.getString("date")
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)
        val message = when (type) {
            "EVENT_JOINED" -> "Confirmation d'inscription à l'événement : \nNom : ${event.getString("name")}\nDate : ${date?.toLocaleString()}\nAdresse : ${event.getString("address")}"
            "EVENT_LEFT" -> "Confirmation de désinscription de l'événement : \nNom : ${event.getString("name")}\nDate : ${date?.toLocaleString()}\nAdresse : ${event.getString("address")}"
            else -> "Notification reçue : \nType : $type\nDétails : ${data.toString(2)}"
        }
        showNotification(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Check if mSocket is initialized before using it
        if (::mSocket.isInitialized) {
            mSocket.disconnect()
            mSocket.close()
        }
    }
}