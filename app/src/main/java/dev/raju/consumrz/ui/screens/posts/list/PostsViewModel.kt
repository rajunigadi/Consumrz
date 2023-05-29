package dev.raju.consumrz.ui.screens.posts.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.BaseViewModel
import dev.raju.consumrz.ui.navigation.RouteNavigator
import dev.raju.consumrz.ui.screens.posts.detail.PostDetailRoute
import dev.raju.consumrz.ui.screens.register.RegisterRoute
import dev.raju.domain.utils.UiState
import dev.raju.domain.enitities.Post
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
class PostsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: PostUseCase
): BaseViewModel(), RouteNavigator by routeNavigator {

    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Empty)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    fun loadPosts() {
        job = viewModelScope.launch(dispatcherProvider.main + exceptionHandler) {
            _uiState.value = UiState.Loading
            useCase
                .getPosts()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Failure(ErrorCodable.defaultErrors(e))
                }
                .collect { loginState ->
                    println("aarna: loginState: $loginState")
                    _uiState.value = loginState
                }
        }
    }

    fun navigate(route: String) {
        job = viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            navigateToRoute(route = route)
        }
    }

    fun navigateTpPostDetail(postId: Int) {
        job = viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            navigateToRoute(PostDetailRoute.get(postId))
        }
    }
}