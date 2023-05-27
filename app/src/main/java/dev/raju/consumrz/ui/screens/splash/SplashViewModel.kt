package dev.raju.consumrz.ui.screens.splash

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.BaseViewModel
import dev.raju.consumrz.ui.navigation.RouteNavigator
import dev.raju.consumrz.ui.screens.login.LoginRoute
import dev.raju.consumrz.ui.screens.posts.PostsRoute
import dev.raju.domain.utils.ResponseCodable
import dev.raju.domain.enitities.LoginState
import dev.raju.domain.usecases.UserUseCase
import dev.raju.domain.utils.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: UserUseCase
) : BaseViewModel(), RouteNavigator by routeNavigator {

    private val _uiState = MutableStateFlow<ResponseCodable<LoginState>>(ResponseCodable.Empty)
    val uiState: StateFlow<ResponseCodable<LoginState>> = _uiState.asStateFlow()

    fun checkLogin() {
        viewModelScope.launch() {
            useCase
                .checkLogin()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = ResponseCodable.Failure(e.message ?: "Something went wrong")
                }
                .collect { loginState ->
                    println("aarna: loginState: $loginState")
                    when (loginState) {
                        is ResponseCodable.Empty -> {

                        }

                        is ResponseCodable.Loading -> {

                        }

                        is ResponseCodable.Failure -> {

                        }

                        is ResponseCodable.Success -> {
                            val route = if (loginState.data?.state == true) {
                                PostsRoute.route
                            } else {
                                LoginRoute.route
                            }
                            Log.d("aarna", "route: $route")
                            navigateToRoutePopUpTo(route = route, popUpToRoute = SplashRoute.route)
                        }
                    }
                }
        }
    }
}