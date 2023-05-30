package dev.raju.consumrz.ui.screens.posts.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.usecases.PostsUseCase
import dev.raju.consumrz.ui.screens.LoaderState
import dev.raju.consumrz.utils.DispatcherProvider
import dev.raju.consumrz.utils.Resource
import dev.raju.consumrz.utils.UiEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val postsUseCase: PostsUseCase
) : ViewModel() {

    private var _loaderState = mutableStateOf(LoaderState())
    val loaderState: State<LoaderState> = _loaderState

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    fun getPosts() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)
            val postsResult = postsUseCase.loadPosts().result
            _loaderState.value = loaderState.value.copy(isLoading = false)

            when (postsResult) {
                is Resource.Success -> {
                    if(postsResult.data.isNullOrEmpty()) {
                        _eventFlow.emit(
                            UiEvents.SnackbarEvent("No posts found")
                        )
                    } else {
                        _posts.emit(postsResult.data)
                    }
                }
                is Resource.Error -> {
                    Timber.tag("aarna").d(postsResult.message)
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            postsResult.message ?: "Error!"
                        )
                    )
                }
                else -> {

                }
            }
        }
    }
}