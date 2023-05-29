package dev.raju.consumrz.ui.screens.posts.add

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.BaseViewModel
import dev.raju.consumrz.ui.navigation.RouteNavigator
import dev.raju.domain.utils.UiState
import dev.raju.domain.enitities.PostParams
import dev.raju.domain.usecases.PostUseCase
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
class AddPostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: PostUseCase
): BaseViewModel(), RouteNavigator by routeNavigator {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    fun addPost(postParams: PostParams) {
        job = viewModelScope.launch(dispatcherProvider.main + exceptionHandler) {
            _uiState.value = UiState.Loading
            Log.d("aarna", "post: ${postParams.title}")
            useCase
                .addPost(postParams = postParams)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Failure(ErrorCodable.defaultErrors(e))
                }
                .collect { state ->
                    println("aarna: state: $state")
                    _uiState.value = state
                }
        }
    }
}