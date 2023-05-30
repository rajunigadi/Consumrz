package dev.raju.consumrz.destinations

/**
 * Class generated if any Composable is annotated with `@Destination`.
 * It aggregates all [TypedDestination]s in their [NavGraph]s.
 */
object NavGraphs {

    val root = NavGraph(
        route = "root",
        startRoute = SplashScreenDestination,
        destinations = listOf(
            SplashScreenDestination,
            LoginScreenDestination,
            ForgotPasswordScreenDestination,
            PrivacyScreenDestination,
            RegisterScreenDestination,
            PostsScreenDestination,
            AddPostScreenDestination
        )
    )
}