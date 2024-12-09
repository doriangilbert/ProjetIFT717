package com.example.projetift717.view

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatBotView(viewModel: ChatViewModel, navController: NavController) {
    val chatHistory by viewModel.chatHistory.collectAsState()
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("chatbot_prefs", Context.MODE_PRIVATE)
    val showWelcomeDialog = remember { mutableStateOf(sharedPreferences.getBoolean("show_welcome_dialog", true)) }

    LaunchedEffect(chatHistory) {
        if (chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
    }

    if (showWelcomeDialog.value) {
        WelcomeDialog(sharedPreferences, showWelcomeDialog)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp) // Increased padding to avoid being cut off by the navigation bar
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(chatHistory) { (text, isUser) ->
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(if (isUser) Alignment.End else Alignment.Start),
                    color = if (isUser) Color(0xFF006400) else Color.Black
                )
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
        ChatInput(viewModel = viewModel)
    }
    Footer(navController = navController)
}

@Composable
fun WelcomeDialog(sharedPreferences: SharedPreferences, showWelcomeDialog: MutableState<Boolean>) {
    var doNotShowAgain by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { showWelcomeDialog.value = false },
        title = { Text("Bienvenue") },
        text = {
            Column {
                Text("Bienvenue dans le chatbot!")
                Text("Ce chatbot utilise Mistral AI pour répondre à vos questions.")
                Text("Après avoir saisi une requête, veuillez patienter un moment pour la réponse.")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = doNotShowAgain,
                        onCheckedChange = { doNotShowAgain = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF006400),
                            uncheckedColor = Color(0xFF006400)
                        )
                    )
                    Text("Ne plus afficher")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (doNotShowAgain) {
                        sharedPreferences.edit().putBoolean("show_welcome_dialog", false).apply()
                    }
                    showWelcomeDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400))
            ) {
                Text("OK")
            }
        }
    )
}

@Composable
fun ChatInput(viewModel: ChatViewModel) {
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { Text("Tapez votre message...") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF006400),
                unfocusedBorderColor = Color(0xFF006400)
            )
        )
        Button(
            onClick = {
                if (message.isNotBlank()) {
                    coroutineScope.launch {
                        viewModel.sendMessage(message)
                        message = ""
                        keyboardController?.hide()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006400)
            )
        ) {
            Text("Envoyer", color = Color.White)
        }
    }
}