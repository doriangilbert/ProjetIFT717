package com.example.projetift717.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projetift717.model.Place
import com.example.projetift717.viewmodel.PlacesListViewModel
import androidx.compose.runtime.setValue

@Composable
fun PlacesListView(viewModel: PlacesListViewModel, navController: NavController) {
    val places by viewModel.places.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAllPlaces()
    }
    Column {
        Header(viewModel)
        PlaceList(places = places, navController = navController)
    }
    Footer(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(viewModel: PlacesListViewModel) {
    var selectedOption by remember { mutableIntStateOf(-1) }
    val context = LocalContext.current
    Text(
        text = "Liste des lieux",
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFF4CAF50),
        modifier = Modifier.padding(16.dp)
    )
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    Text(
        text = "Trier par :",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(16.dp)
    )
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        SegmentedButton(
            onClick = { selectedOption = 0; viewModel.sortByName() },
            selected = selectedOption == 0,
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)
        ) {
            Text("Nom")
        }
        SegmentedButton(
            onClick = { selectedOption = 1; viewModel.sortByDistance(context = context) },
            selected = selectedOption == 1,
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)
        ) {
            Text("Distance")
        }
        SegmentedButton(
            onClick = { selectedOption = 2; viewModel.sortByDistance(context = context) },
            selected = selectedOption == 2,
            shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)
        ) {
            Text("Horaire idéal")
        }
    }

    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun PlaceList(places: List<Place>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(places) { place ->
            PlaceItem(place) {
                navController.navigate("PlaceDetailsView/${place.id}")
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun PlaceItem(place: Place, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF4CAF50),
            )
            Text(
                text = place.address,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}