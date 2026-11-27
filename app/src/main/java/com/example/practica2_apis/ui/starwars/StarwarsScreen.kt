package com.example.practica2_apis.ui.starwars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica2_apis.data.modelStarwars.Result
import com.example.practica2_apis.data.modelStarwars.StarwarsCharacter
import com.example.practica2_apis.ui.theme.Practica2_APIsTheme

@Composable
fun StarwarsScreen(
    modifier: Modifier = Modifier,
    viewModel: StarwarsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StarwarsScreen(
        state = state,
        onLoadPage = { viewModel.load(it) },
        modifier = modifier
    )
}

@Composable
fun StarwarsScreen(
    state: StarwarsState,
    onLoadPage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCharacterKey by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Personajes de Star Wars",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))

        when (val current = state) {
            StarwarsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is StarwarsState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Error al cargar los personajes.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onLoadPage(current.page) }) {
                        Text("Reintentar")
                    }
                }
            }

            is StarwarsState.Content -> {
                val characters = current.response.results
                if (characters.isEmpty()) {
                    Text("No se encontraron personajes en esta página.")
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = characters,
                        key = { it.url.ifBlank { it.name } }
                    ) { character ->
                        val characterKey = character.url.ifBlank { character.name }
                        Card(
                            onClick = { selectedCharacterKey = characterKey },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = character.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Nacimiento: ${character.birth_year.ifBlank { "Desconocido" }} • Género: ${character.gender.ifBlank { "Desconocido" }}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Altura: ${character.height.ifBlank { "-" }} cm • Masa: ${character.mass.ifBlank { "-" }} kg",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val totalPages = ((current.response.count + 9) / 10).coerceAtLeast(1)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onLoadPage(current.page - 1) },
                        enabled = current.page > 1 && current.response.previous != null
                    ) {
                        Text("Anterior")
                    }

                    Text("${current.page} / $totalPages")

                    TextButton(
                        onClick = { onLoadPage(current.page + 1) },
                        enabled = current.response.next != null
                    ) {
                        Text("Siguiente")
                    }
                }

                selectedCharacterKey?.let { key ->
                    characters.firstOrNull { it.url.ifBlank { it.name } == key }?.let { character ->
                        AlertDialog(
                            onDismissRequest = { selectedCharacterKey = null },
                            title = { Text(character.name) },
                            text = {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    item {
                                        Text("Año de nacimiento: ${character.birth_year.ifBlank { "Desconocido" }}")
                                        Text("Género: ${character.gender.ifBlank { "Desconocido" }}")
                                        Text("Altura: ${character.height.ifBlank { "Desconocida" }} cm")
                                        Text("Masa: ${character.mass.ifBlank { "Desconocida" }} kg")
                                        Text("Cabello: ${character.hair_color.ifBlank { "Desconocido" }}")
                                        Text("Piel: ${character.skin_color.ifBlank { "Desconocido" }}")
                                        Text("Ojos: ${character.eye_color.ifBlank { "Desconocido" }}")
                                    }

                                    val groups = listOf(
                                        "Películas" to character.films,
                                        "Especies" to character.species,
                                        "Vehículos" to character.vehicles,
                                        "Naves" to character.starships
                                    )
                                    groups.filter { it.second.isNotEmpty() }.forEach { (title, values) ->
                                        item {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = title,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        items(values) { itemUrl ->
                                            Text(
                                                text = "• $itemUrl",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            },
                            confirmButton = {
                                TextButton(onClick = { selectedCharacterKey = null }) {
                                    Text("Cerrar")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private val sampleStarwarsCharacters = listOf(
    Result(
        birth_year = "19BBY",
        created = "2014-12-09T13:50:51.644000Z",
        edited = "2014-12-20T21:17:56.891000Z",
        eye_color = "blue",
        films = listOf("https://swapi.dev/api/films/1/"),
        gender = "male",
        hair_color = "blond",
        height = "172",
        homeworld = "https://swapi.dev/api/planets/1/",
        mass = "77",
        name = "Luke Skywalker",
        skin_color = "fair",
        species = emptyList(),
        starships = listOf("https://swapi.dev/api/starships/12/"),
        url = "https://swapi.dev/api/people/1/",
        vehicles = listOf("https://swapi.dev/api/vehicles/14/")
    ),
    Result(
        birth_year = "41.9BBY",
        created = "2014-12-10T15:18:20.704000Z",
        edited = "2014-12-20T21:17:50.313000Z",
        eye_color = "yellow",
        films = listOf("https://swapi.dev/api/films/1/"),
        gender = "male",
        hair_color = "none",
        height = "202",
        homeworld = "https://swapi.dev/api/planets/1/",
        mass = "136",
        name = "Darth Vader",
        skin_color = "white",
        species = emptyList(),
        starships = listOf("https://swapi.dev/api/starships/13/"),
        url = "https://swapi.dev/api/people/4/",
        vehicles = emptyList()
    )
)

private val sampleStarwarsState = StarwarsState.Content(
    page = 1,
    response = StarwarsCharacter(
        count = 82,
        next = "https://swapi.dev/api/people/?page=2",
        previous = null,
        results = sampleStarwarsCharacters
    )
)

@Preview(showBackground = true)
@Composable
fun StarwarsScreenPreview() {
    Practica2_APIsTheme {
        StarwarsScreen(
            state = sampleStarwarsState,
            onLoadPage = {}
        )
    }
}
