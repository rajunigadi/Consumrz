package dev.raju.consumrz.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.domain.usecases.UserUseCase
import dev.raju.consumrz.ui.screens.LoaderState
import dev.raju.consumrz.ui.screens.destinations.PostsScreenDestination
import dev.raju.consumrz.utils.DispatcherProvider
import dev.raju.consumrz.utils.Resource
import dev.raju.consumrz.utils.TextFieldState
import dev.raju.consumrz.utils.UiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private var _loginState = mutableStateOf(LoaderState())
    val loginState: State<LoaderState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    fun setEmail(value: String) {
        _emailState.value = emailState.value.copy(text = value, error = null)
    }

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setPassword(value: String) {
        _passwordState.value = passwordState.value.copy(text = value, error = null)
    }

    fun signInUser() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loginState.value = loginState.value.copy(isLoading = false)

            val loginResult = userUseCase.signIn(
                email = emailState.value.text,
                password = passwordState.value.text
            )

            _loginState.value = loginState.value.copy(isLoading = false)

            if (loginResult.emailError != null) {
                _emailState.value = emailState.value.copy(error = loginResult.emailError)
            }
            if (loginResult.passwordError != null) {
                _passwordState.value = passwordState.value.copy(error = loginResult.passwordError)
            }

            when (loginResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(PostsScreenDestination.route)
                    )
                }
                is Resource.Error -> {
                    Timber.tag("aarna").d(loginResult.result.message)
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            loginResult.result.message ?: "Error!"
                        )
                    )
                }
                else -> {

                }
            }
        }
    }
}