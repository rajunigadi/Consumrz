package dev.raju.consumrz.ui.navigation

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
sealed class NavRoute(val path: String) {

    object Splash : NavRoute("splash")
    object Login : NavRoute("login")
    object Register : NavRoute("register")
    object Posts : NavRoute("posts")

    object PostDetails : NavRoute("post_details") {
        val id = "id"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}
