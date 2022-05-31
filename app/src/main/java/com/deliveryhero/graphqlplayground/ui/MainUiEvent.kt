package com.deliveryhero.graphqlplayground.ui

sealed class MainUiEvent {
    object FetchLaunches : MainUiEvent()
    data class ShowError(val message: String) : MainUiEvent()
    object Empty : MainUiEvent()
}
