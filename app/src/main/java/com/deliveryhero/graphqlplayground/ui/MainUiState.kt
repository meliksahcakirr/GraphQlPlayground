package com.deliveryhero.graphqlplayground.ui

import androidx.compose.material.SnackbarDuration
import androidx.paging.PagingData
import com.deliveryhero.graphqlplayground.domain.LaunchItem
import kotlinx.coroutines.flow.Flow

data class MainUiState(
    val paginationFlow: Flow<PagingData<LaunchItem>>,
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val errorActionMessage: String? = null,
    val errorDuration: SnackbarDuration = SnackbarDuration.Short,
    val errorAction: (() -> Unit)? = null,
)