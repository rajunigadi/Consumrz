package dev.raju.consumrz.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.domain.utils.ResponseCodable

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(state) {
        viewModel.checkLogin()
        when (state) {
            is ResponseCodable.Empty -> {

            }

            is ResponseCodable.Loading -> {

            }

            is ResponseCodable.Failure -> {

            }

            is ResponseCodable.Success -> {
                val path = if(state.data?.state == true) {
                    NavRoute.Posts.path
                } else {
                    NavRoute.Login.path
                }
                navController.navigate(path) {
                    popUpTo(NavRoute.Splash.path) {
                        inclusive = true
                    }
                }
            }
        }
    }
}