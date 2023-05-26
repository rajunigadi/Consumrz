package dev.raju.consumrz.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.raju.consumrz.ui.screens.login.LoginScreen
import dev.raju.consumrz.ui.screens.splash.SplashScreen

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.Splash.path) {
        composable(NavRoute.Splash.path) {
            SplashScreen(navController = navController)
        }
        composable(NavRoute.Login.path) {
            LoginScreen(navController = navController)
        }
        composable(NavRoute.Register.path) {
            RegisterScreen(navController = navController)
        }
        composable(NavRoute.Posts.path) {
            PostsScreen(navController = navController)
        }
    }
}

// samples
/*@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login Screen")

        Button(onClick = {
            navController.navigate(NavRoute.Register.path)
        }) {
            Text("Register")
        }
    }
}*/

@Composable
fun RegisterScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register Screen")

        Button(onClick = { navController.navigate(NavRoute.Login.path) }) {
            Text("Login")
        }
    }
}

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