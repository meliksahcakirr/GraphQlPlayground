package com.deliveryhero.graphqlplayground.ui

import androidx.compose.material.SnackbarDuration
import com.deliveryhero.graphqlplayground.domain.LaunchItem

data class MainUiState(
    val loading: Boolean = false,
    val data: List<LaunchItem> = emptyList(),
    val errorMessage: String? = null,
    val errorActionMessage: String? = null,
    val errorDuration: SnackbarDuration = SnackbarDuration.Short,
    val errorAction: (() -> Unit)? = null,
)