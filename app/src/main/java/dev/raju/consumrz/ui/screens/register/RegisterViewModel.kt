package dev.raju.consumrz.ui.screens.register

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
class RegisterViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private var _loginState = mutableStateOf(LoaderState())
    val loginState: State<LoaderState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _firstNameState = mutableStateOf(TextFieldState())
    val firstNameState: State<TextFieldState> = _firstNameState

    fun setFirstName(value: String) {
        _firstNameState.value = firstNameState.value.copy(text = value, error = null)
    }

    private val _lastNameState = mutableStateOf(TextFieldState())
    val lastNameState: State<TextFieldState> = _lastNameState

    fun setLastName(value: String) {
        _lastNameState.value = lastNameState.value.copy(text = value, error = null)
    }

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

    private val _repeatPasswordState = mutableStateOf(TextFieldState())
    val repeatPasswordState: State<TextFieldState> = _repeatPasswordState

    fun setRepeatPassword(value: String) {
        _repeatPasswordState.value = repeatPasswordState.value.copy(text = value, error = null)
    }

    fun register() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loginState.value = loginState.value.copy(isLoading = false)

            val registerResult = userUseCase.register(
                firstName = firstNameState.value.text,
                lastName = lastNameState.value.text,
                email = emailState.value.text,
                password = passwordState.value.text,
                repeatPassword = repeatPasswordState.value.text
            )

            _loginState.value = loginState.value.copy(isLoading = false)

            if (registerResult.firstNameError != null) {
                _firstNameState.value = firstNameState.value.copy(error = registerResult.firstNameError)
            }
            if (registerResult.lastNameError != null) {
                _lastNameState.value = lastNameState.value.copy(error = registerResult.lastNameError)
            }
            if (registerResult.emailError != null) {
                _emailState.value = emailState.value.copy(error = registerResult.emailError)
            }
            if (registerResult.passwordError != null) {
                _passwordState.value = passwordState.value.copy(error = registerResult.passwordError)
            }
            if (registerResult.repeatPasswordError != null) {
                _repeatPasswordState.value = repeatPasswordState.value.copy(error = registerResult.repeatPasswordError)
            }
            if (registerResult.passwordMatchError != null) {
                _repeatPasswordState.value = repeatPasswordState.value.copy(error = registerResult.passwordMatchError)
            }

            when (registerResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(PostsScreenDestination.route)
                    )
                }
                is Resource.Error -> {
                    Timber.tag("aarna").d(registerResult.result.message)
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            registerResult.result.message ?: "Error!"
                        )
                    )
                }
                else -> {

                }
            }
        }
    }
}