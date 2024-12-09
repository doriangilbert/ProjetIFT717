package com.example.projetift717.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.projetift717.model.User
import com.example.projetift717.viewmodel.UserViewModel

@Composable
fun RegisterScreen(vm: UserViewModel, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val responseObserver = Observer<User?> { user ->
        if (user != null) {
            navController.navigate("Login")
        }
    }
    vm.registerResponse.observe(LocalLifecycleOwner.current, responseObserver)

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text("Inscription")
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )
            TextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Prénom") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )
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
                    vm.register(name, surname, email, password)
                    navController.navigate("Login")
                },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
            ) {
                Text("S'inscrire")
            }
            Button(
                onClick = { navController.navigate("Login") },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
            ) {
                Text("Se connecter")
            }
        }
    }
}