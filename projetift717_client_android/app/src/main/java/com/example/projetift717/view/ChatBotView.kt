package com.example.projetift717.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatBotView(viewModel: ChatViewModel, navController: NavController) {
    val chatHistory by viewModel.chatHistory.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(chatHistory) {
        if (chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
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