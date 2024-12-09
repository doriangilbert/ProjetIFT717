package com.example.projetift717.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.repository.EventRepository
import com.example.projetift717.viewmodel.EventDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PaymentView(viewModel: EventDetailsViewModel, navController: NavController, eventId: String) {
    viewModel.fetchEvent(eventId)
    val event by viewModel.event.collectAsState()
    val userId = viewModel.getUserId()

    // Les champs a remplir pour le paiement
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("") }
    var expiryYear by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    // Les regex pour valider les champs
    val cardNumberPattern = "[0-9]{16}"
    val cardHolderPattern = "[\\p{L} '\\p{M}]+"
    val expiryMonthPattern = "[0-9]{2}"
    val expiryYearPattern = "[0-9]{2}"
    val cvvPattern = "[0-9]{3,4}"

    fun paymentVerification() {
        if (!cardNumber.matches(cardNumberPattern.toRegex()) ||
            !cardHolder.matches(cardHolderPattern.toRegex()) ||
            !expiryMonth.matches(expiryMonthPattern.toRegex()) ||
            !expiryYear.matches(expiryYearPattern.toRegex()) ||
            !cvv.matches(cvvPattern.toRegex())) {
            errorMessage = "Au moins un des champs est mal renseigné"
        } else {
            if (userId != null) {
                viewModel.addUserToEvent(eventId, userId)
            }
            navController.navigate("EventsView")



            /*CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (userId != null) {
                        val event = viewModel.addUserToEvent(eventId, userId)
                        if (event != null) {
                            withContext(Dispatchers.Main) {
                                navController.navigate("EventsView")
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                errorMessage = "Erreur lors de l'ajout de l'utilisateur à l'événement"
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            errorMessage = "ID utilisateur non trouvé"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        errorMessage = "Erreur lors de la requête : ${e.message}"
                    }
                }
            }*/
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        event?.let {
            Text(text = "Event: ${it.name}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: ${it.price}$", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
        }
        TextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Numéro de la carte") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = cardHolder,
            onValueChange = { cardHolder = it },
            label = { Text("Titulaire de la carte") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = expiryMonth,
                onValueChange = { expiryMonth = it },
                label = { Text("Mois d'expiration") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = expiryYear,
                onValueChange = { expiryYear = it },
                label = { Text("Année d'expiration") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("Code de sécurité") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = { paymentVerification() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Payer l'inscription")
        }
    }
    Footer(navController = navController)
}