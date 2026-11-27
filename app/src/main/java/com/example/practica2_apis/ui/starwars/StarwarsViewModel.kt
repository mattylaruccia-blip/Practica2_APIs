package com.example.practica2_apis.ui.starwars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica2_apis.data.StarwarsRepository
import com.example.practica2_apis.data.modelStarwars.StarwarsCharacter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface StarwarsState {
    data object Loading : StarwarsState
    data class Content(val page: Int, val response: StarwarsCharacter) : StarwarsState
    data class Error(val page: Int) : StarwarsState
}

class StarwarsViewModel(
    private val repository: StarwarsRepository = StarwarsRepository.instance
) : ViewModel() {
    private val mutableState = MutableStateFlow<StarwarsState>(StarwarsState.Loading)
    val state = mutableState.asStateFlow()

    init {
        load(1)
    }

    fun load(page: Int) {
        mutableState.value = StarwarsState.Loading
        viewModelScope.launch {
            mutableState.value = try {
                StarwarsState.Content(page, repository.characters(page))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                StarwarsState.Error(page)
            }
        }
    }
}
