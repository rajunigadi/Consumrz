package dev.raju.consumrz.destinations

import androidx.annotation.RestrictTo
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.spec.*
import dev.raju.consumrz.ui.screens.login.LoginScreen

object LoginScreenDestination : DirectionDestination {

    operator fun invoke() = this

    @get:RestrictTo(RestrictTo.Scope.SUBCLASSES)
    override val baseRoute = "login_screen"

    override val route = baseRoute

    @Composable
    override fun DestinationScope<Unit>.Content(
        dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<Unit>.() -> Unit
    ) {
        LoginScreen(
            navigator = destinationsNavigator
        )
    }
}