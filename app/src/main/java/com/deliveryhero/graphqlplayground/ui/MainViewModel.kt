package com.deliveryhero.graphqlplayground.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.deliveryhero.graphqlplayground.domain.FetchLaunchesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: FetchLaunchesPagingUseCase) :
    ViewModel() {


    private val _uiEvents = MutableStateFlow<MainUiEvent>(MainUiEvent.Empty)

    private val fetchLaunchesFlow = _uiEvents
        .filterIsInstance<MainUiEvent.FetchLaunches>()
        .distinctUntilChanged()
        .onStart { emit(MainUiEvent.FetchLaunches) }

    private val launchItemsFlow = fetchLaunchesFlow.flatMapLatest {
        useCase.fetchLaunches()
    }.cachedIn(viewModelScope)

    var uiState by mutableStateOf(MainUiState(paginationFlow = launchItemsFlow))
        private set
}