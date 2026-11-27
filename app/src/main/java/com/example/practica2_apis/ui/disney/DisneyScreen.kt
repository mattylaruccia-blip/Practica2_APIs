package com.example.practica2_apis.ui.disney

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.practica2_apis.modelDisney.Data
import com.example.practica2_apis.modelDisney.DisneyCharacter
import com.example.practica2_apis.modelDisney.Info
import com.example.practica2_apis.ui.theme.Practica2_APIsTheme

@Composable
fun DisneyScreen(
    modifier: Modifier = Modifier,
    viewModel: DisneyViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DisneyScreen(
        state = state,
        onLoadPage = { viewModel.load(it) },
        modifier = modifier
    )
}

@Composable
fun DisneyScreen(
    state: DisneyState,
    onLoadPage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedId by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Personajes de Disney",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))

        when (val current = state) {
            DisneyState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DisneyState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No se pudo cargar el catálogo.")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { onLoadPage(current.page) }) {
                        Text("Reintentar")
                    }
                }
            }

            is DisneyState.Content -> {
                val characters = current.response.data
                if (characters.isEmpty()) {
                    Text("No hay personajes en esta página.")
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(characters, key = { it._id }) { character ->
                        Card(
                            onClick = { selectedId = character._id },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = character.imageUrl,
                                    contentDescription = character.name,
                                    modifier = Modifier.size(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = character.name,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onLoadPage(current.page - 1) },
                        enabled = current.page > 1
                    ) {
                        Text("Anterior")
                    }
                    Text("${current.page} / ${current.response.info.totalPages}")
                    TextButton(
                        onClick = { onLoadPage(current.page + 1) },
                        enabled = current.response.info.nextPage != null
                    ) {
                        Text("Siguiente")
                    }
                }

                characters.firstOrNull { it._id == selectedId }?.let { character ->
                    AlertDialog(
                        onDismissRequest = { selectedId = null },
                        title = { Text(character.name) },
                        text = {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                item {
                                    AsyncImage(
                                        model = character.imageUrl,
                                        contentDescription = character.name,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(160.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                val groups = listOf(
                                    "Películas" to character.films,
                                    "Series" to character.tvShows,
                                    "Cortos" to character.shortFilms,
                                    "Videojuegos" to character.videoGames,
                                    "Atracciones" to character.parkAttractions,
                                    "Aliados" to character.allies,
                                    "Enemigos" to character.enemies
                                )
                                groups.filter { it.second.isNotEmpty() }.forEach { (title, values) ->
                                    item { Text(title, style = MaterialTheme.typography.titleMedium) }
                                    items(values) { Text(it.toString()) }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = { selectedId = null }) {
                                Text("Cerrar")
                            }
                        }
                    )
                }
            }
        }
    }
}

private val sampleCharacterList = listOf(
    Data(
        _id = 112,
        allies = listOf("Pluto", "Donald Duck"),
        enemies = listOf("Pete"),
        films = listOf("Steamboat Willie", "Fantasia"),
        imageUrl = "https://static.wikia.nocookie.net/disney/images/7/7f/Mickey_Mouse_Pose_02.png",
        name = "Mickey Mouse",
        parkAttractions = listOf("Mickey & Minnie's Runaway Railway"),
        shortFilms = listOf("Get a Horse!"),
        tvShows = listOf("Mickey Mouse Clubhouse"),
        url = "https://api.disneyapi.dev/characters/112",
        videoGames = listOf("Kingdom Hearts", "Epic Mickey")
    ),
    Data(
        _id = 113,
        allies = listOf("Mickey Mouse"),
        enemies = listOf("Pete"),
        films = listOf("The Wise Little Hen"),
        imageUrl = "https://static.wikia.nocookie.net/disney/images/6/6b/Donald_Duck_pose.png",
        name = "Donald Duck",
        parkAttractions = listOf("Mickey's PhilharMagic"),
        shortFilms = listOf("Der Fuehrer's Face"),
        tvShows = listOf("DuckTales"),
        url = "https://api.disneyapi.dev/characters/113",
        videoGames = listOf("Kingdom Hearts")
    )
)

private val sampleDisneyState = DisneyState.Content(
    page = 1,
    response = DisneyCharacter(
        data = sampleCharacterList,
        info = Info(
            count = 2,
            nextPage = "https://api.disneyapi.dev/character?page=2&pageSize=50",
            previousPage = null,
            totalPages = 149
        )
    )
)

@Preview(showBackground = true)
@Composable
fun DisneyScreenPreview() {
    Practica2_APIsTheme {
        DisneyScreen(
            state = sampleDisneyState,
            onLoadPage = {}
        )
    }
}
