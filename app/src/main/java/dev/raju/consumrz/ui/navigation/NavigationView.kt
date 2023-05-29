package dev.raju.consumrz.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.raju.consumrz.ui.screens.login.LoginRoute
import dev.raju.consumrz.ui.screens.posts.PostsRoute
import dev.raju.consumrz.ui.screens.posts.add.AddPostRoute
import dev.raju.consumrz.ui.screens.register.RegisterRoute
import dev.raju.consumrz.ui.screens.splash.SplashRoute

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Composable
fun NavigationView(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute.route,
    ) {
        SplashRoute.composable(this, navController)
        LoginRoute.composable(this, navController)
        RegisterRoute.composable(this, navController)
        PostsRoute.composable(this, navController)
        AddPostRoute.composable(this, navController)
    }
}

// samples
@Composable
fun PostsScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Posts Screen")
    }
}