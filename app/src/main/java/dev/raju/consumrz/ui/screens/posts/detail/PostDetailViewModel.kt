package dev.raju.consumrz.ui.screens.posts.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.BaseViewModel
import dev.raju.consumrz.ui.navigation.RouteNavigator
import dev.raju.consumrz.ui.screens.posts.add.AddPostRoute
import dev.raju.consumrz.ui.screens.posts.addcomment.AddCommentRoute
import dev.raju.consumrz.ui.screens.posts.edit.EditPostRoute
import dev.raju.domain.enitities.Post
import dev.raju.domain.utils.UiState
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
class PostDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: PostUseCase
): BaseViewModel(), RouteNavigator by routeNavigator {

    private val _uiState = MutableStateFlow<UiState<Post>>(UiState.Empty)
    val uiState: StateFlow<UiState<Post>> = _uiState.asStateFlow()

    private val postId = savedStateHandle.get<Int>(KEY_POST_DETAIL)

    fun getPost() {
        job = viewModelScope.launch(dispatcherProvider.main + exceptionHandler) {
            _uiState.value = UiState.Loading
            if (postId != null) {
                useCase
                    .getPost(postId)
                    .flowOn(dispatcherProvider.io)
                    .catch { e ->
                        _uiState.value = UiState.Failure(ErrorCodable.defaultErrors(e))
                    }
                    .collect { state ->
                        println("aarna: state: $state")
                        _uiState.value = state
                    }
            } else {
                _uiState.value = UiState.Failure(ErrorCodable.defaultErrors())
            }
        }
    }

    fun navigateToUpdatePost(postId: Int) {
        job = viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            navigateToRoute(EditPostRoute.get(postId))
        }
    }

    fun navigateToNewComment() {
        job = viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            if (postId != null) {
                navigateToRoute(AddCommentRoute.get(postId))
            } else {
                _uiState.value = UiState.Failure(ErrorCodable.defaultErrors())
            }
        }
    }
}