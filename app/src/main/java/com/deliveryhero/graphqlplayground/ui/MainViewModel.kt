package com.deliveryhero.graphqlplayground.ui

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliveryhero.graphqlplayground.domain.FetchLaunchesUseCase
import com.deliveryhero.graphqlplayground.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: FetchLaunchesUseCase) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    private val _uiEvents = MutableStateFlow<MainUiEvent>(MainUiEvent.Empty)

    private var cursor = ""
    private var hasMore = true

    init {
        viewModelScope.launch {
            merge(fetchLaunchesStream()).collectLatest {

            }
        }
    }

    private fun fetchLaunchesStream(): Flow<MainUiEvent> {
        return _uiEvents.filterIsInstance<MainUiEvent.FetchLaunches>()
            .onStart { emit(MainUiEvent.FetchLaunches) }
            .flatMapLatest {
                useCase.fetchLaunches().onStart {
                    uiState = MainUiState(true, uiState.data)
                }
            }.map { result ->
                when (result) {
                    is Result.Success -> {
                        cursor = result.data.cursor
                        hasMore = result.data.hasMore
                        uiState = MainUiState(false, result.data.launches)
                    }
                    is Result.Error -> {
                        uiState = MainUiState(
                            false,
                            emptyList(),
                            result.exception.message,
                            "Try Again",
                            SnackbarDuration.Short,
                            this::fetchLaunches
                        )
                    }
                }
                MainUiEvent.Empty
            }
    }

    private fun fetchLaunches() {
        _uiEvents.tryEmit(MainUiEvent.FetchLaunches)
    }
}