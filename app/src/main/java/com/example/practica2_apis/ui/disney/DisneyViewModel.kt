package com.example.practica2_apis.ui.disney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica2_apis.data.DisneyRepository
import com.example.practica2_apis.modelDisney.DisneyCharacter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DisneyState {
    data object Loading : DisneyState
    data class Content(val page: Int, val response: DisneyCharacter) : DisneyState
    data class Error(val page: Int) : DisneyState
}

class DisneyViewModel(
    private val repository: DisneyRepository = DisneyRepository.instance
) : ViewModel() {
    private val mutableState = MutableStateFlow<DisneyState>(DisneyState.Loading)
    val state = mutableState.asStateFlow()

    init {
        load(1)
    }

    fun load(page: Int) {
        mutableState.value = DisneyState.Loading
        viewModelScope.launch {
            mutableState.value = try {
                DisneyState.Content(page, repository.characters(page))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                DisneyState.Error(page)
            }
        }
    }
}
