package dev.raju.consumrz.ui.screens.posts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.usecases.PostsUseCase
import dev.raju.consumrz.ui.screens.LoaderState
import dev.raju.consumrz.ui.screens.destinations.PostsScreenDestination
import dev.raju.consumrz.utils.DispatcherProvider
import dev.raju.consumrz.utils.Resource
import dev.raju.consumrz.utils.TextFieldState
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

    // add new post
    private val _titleState = mutableStateOf(TextFieldState())
    val titleState: State<TextFieldState> = _titleState

    fun setTitle(value: String) {
        _titleState.value = titleState.value.copy(text = value, error = null)
    }

    private val _textState = mutableStateOf(TextFieldState())
    val textState: State<TextFieldState> = _textState

    fun setText(value: String) {
        _textState.value = textState.value.copy(text = value, error = null)
    }

    fun getPosts() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)
            val postsResult = postsUseCase.loadPosts().result
            _loaderState.value = loaderState.value.copy(isLoading = false)

            when (postsResult) {
                is Resource.Success -> {
                    if (postsResult.data.isNullOrEmpty()) {
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

    fun addPost(id: Int? = null) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)

            val addPostResult = postsUseCase.addOrUpdatePost(
                id = id,
                title = titleState.value.text,
                text = textState.value.text
            )

            _loaderState.value = loaderState.value.copy(isLoading = false)

            if (addPostResult.titleError != null) {
                _titleState.value = titleState.value.copy(error = addPostResult.titleError)
            }
            if (addPostResult.textError != null) {
                _textState.value = textState.value.copy(error = addPostResult.textError)
            }

            when (addPostResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(PostsScreenDestination.route)
                    )
                }

                is Resource.Error -> {
                    Timber.tag("aarna").d(addPostResult.result.message)
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            addPostResult.result.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)

            val addPostResult = postsUseCase.deletePost(post = post)

            _loaderState.value = loaderState.value.copy(isLoading = false)

            if (addPostResult.titleError != null) {
                _titleState.value = titleState.value.copy(error = addPostResult.titleError)
            }
            if (addPostResult.textError != null) {
                _textState.value = textState.value.copy(error = addPostResult.textError)
            }

            when (addPostResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(PostsScreenDestination.route)
                    )
                }

                is Resource.Error -> {
                    Timber.tag("aarna").d(addPostResult.result.message)
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            addPostResult.result.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }
}