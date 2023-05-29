package dev.raju.consumrz.ui.screens.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.BaseViewModel
import dev.raju.consumrz.ui.navigation.RouteNavigator
import dev.raju.consumrz.ui.screens.posts.list.PostsRoute
import dev.raju.domain.utils.UiState
import dev.raju.domain.enitities.LoginState
import dev.raju.domain.enitities.SignInParams
import dev.raju.domain.usecases.UserUseCase
import dev.raju.domain.utils.DispatcherProvider
import dev.raju.domain.utils.ErrorCodable
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
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: UserUseCase
) : BaseViewModel(), RouteNavigator by routeNavigator {

    private val _uiState = MutableStateFlow<UiState<LoginState>>(UiState.Empty)
    val uiState: StateFlow<UiState<LoginState>> = _uiState.asStateFlow()

    fun signIn(params: Map<String, String>) {
        job = viewModelScope.launch(dispatcherProvider.main + exceptionHandler) {
            val signInParams = SignInParams.from(params)
            Log.d("aarna", "Email: ${signInParams.email} and password: ${signInParams.password}")
            _uiState.value = UiState.Loading
            useCase
                .signIn(params = SignInParams(email = signInParams.email, password = signInParams.password))
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Failure(errrors = ErrorCodable.defaultErrors(e))
                }
                .collect { loginState ->
                    println("aarna: loginState: $loginState")
                    when (loginState) {
                        is UiState.Empty -> {

                        }

                        is UiState.Loading -> {

                        }

                        is UiState.Failure -> {

                        }

                        is UiState.Success -> {
                            navigateToRoutePopUpTo(
                                route = PostsRoute.route,
                                popUpToRoute = LoginRoute.route
                            )
                        }
                    }
                }
        }
    }

    fun navigate(route: String) {
        job = viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            navigateToRoute(route)
        }
    }
}