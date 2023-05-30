package dev.raju.consumrz.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.domain.usecases.UserUseCase
import dev.raju.consumrz.ui.screens.destinations.LoginScreenDestination
import dev.raju.consumrz.ui.screens.destinations.PostsScreenDestination
import dev.raju.consumrz.utils.Resource
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
class SplashViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val loginResult = userUseCase.isUserLoggedIn()
            when (loginResult.result) {
                is Resource.Success -> {
                    Timber.tag("aarna").d("Success")
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(PostsScreenDestination.route)
                    )
                }
                is Resource.Error -> {
                    Timber.tag("aarna").d("Error")
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(LoginScreenDestination.route)
                    )
                }
                else -> {
                    Timber.tag("aarna").d("Else")
                }
            }
        }
    }
}