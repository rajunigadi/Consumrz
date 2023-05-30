package dev.raju.consumrz.utils

import dev.raju.consumrz.domain.model.Post

sealed class UiEvents {
    data class SnackbarEvent(val message : String) : UiEvents()
    data class NavigateEvent(val route: String) : UiEvents()
}