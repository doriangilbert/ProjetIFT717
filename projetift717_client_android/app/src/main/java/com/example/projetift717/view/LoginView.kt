package com.example.projetift717.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    val responseObserver = Observer<LoginResponse?> { response ->
        if (response?.token != null) {
            context.connectToNotifications()
            navController.navigate("MapView")
        }
    }
    vm.loginResponse.observe(LocalLifecycleOwner.current, responseObserver)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login")
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
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                vm.login(email, password)
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Login")
        }
        Button(
            onClick = { navController.navigate("Register") },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Register")
        }
    }
}