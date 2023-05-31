package dev.raju.consumrz.utils

sealed class UiEvents {
    data class SnackbarEvent(val message : String) : UiEvents()
    data class NavigateEvent(val route: String) : UiEvents()
    object NavigateUp : UiEvents()
}