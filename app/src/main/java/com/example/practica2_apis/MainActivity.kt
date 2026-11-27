package com.example.practica2_apis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.practica2_apis.ui.disney.DisneyScreen
import com.example.practica2_apis.ui.starwars.StarwarsScreen
import com.example.practica2_apis.ui.theme.Practica2_APIsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica2_APIsTheme {
                var selectedTab by rememberSaveable { mutableIntStateOf(0) }
                val screenState = rememberSaveableStateHolder()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        PrimaryTabRow(selectedTabIndex = selectedTab) {
                            listOf("Disney", "Star Wars").forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = { Text(title) }
                                )
                            }
                        }
                        screenState.SaveableStateProvider(selectedTab) {
                            when (selectedTab) {
                                0 -> DisneyScreen()
                                else -> StarwarsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
