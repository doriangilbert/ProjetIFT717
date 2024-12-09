package com.example.projetift717.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.UserViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.projetift717.model.requests.LoginResponse

@Composable
fun LoginScreen(vm: UserViewModel, navController: NavController, context: MainActivity) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showWelcomeDialog by remember { mutableStateOf(false) }

    val responseObserver = Observer<LoginResponse?> { response ->
        if (response?.token != null) {
            context.connectToNotifications()
            showWelcomeDialog = true
        }
    }
    vm.loginResponse.observe(LocalLifecycleOwner.current, responseObserver)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Connexion")
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                vm.login(email, password)
            },
            modifier = Modifier.padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
        ) {
            Text("Se connecter")
        }
        Button(
            onClick = { navController.navigate("Register") },
            modifier = Modifier.padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
        ) {
            Text("S'inscrire")
        }
    }

    if (showWelcomeDialog) {
        WelcomeDialog(onDismiss = {
            showWelcomeDialog = false
            navController.navigate("MapView")
        })
    }
}

@Composable
fun WelcomeDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Bienvenue") },
        text = {
            Column {
                Text("Bienvenue !")
                Text("Cette application vous permet de connaitre les lieux à visiter et les événements à venir à Sherbrooke.")
                Text("Vous pouvez naviguer entre les onglets en bas de l'écran.")
                Text("Voici comment utiliser les différents onglets:")
                Text("- Onglet Chatbot : Vous pouvez converser avec un chatbot IA pour obtenir des informations.")
                Text("- Onglet Lieux : Vous pouvez voir les lieux à visiter.")
                Text("- Onglet Carte : Vous pouvez vous situer et voir les lieux à visiter sur une carte.")
                Text("- Onglet Evénements : Vous pouvez voir les événements à venir et vous y inscrire.")
                Text("- Onglet Profil : Vous pouvez voir votre profil.")
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
            ) {
                Text("OK")
            }
        }
    )
}