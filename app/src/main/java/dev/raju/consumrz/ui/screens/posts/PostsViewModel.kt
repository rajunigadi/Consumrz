package dev.raju.consumrz.ui.screens.posts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.raju.consumrz.domain.model.Comment
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.usecases.PostsUseCase
import dev.raju.consumrz.domain.usecases.UserUseCase
import dev.raju.consumrz.ui.screens.LoaderState
import dev.raju.consumrz.ui.screens.destinations.LoginScreenDestination
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
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val userUseCase: UserUseCase,
    private val postsUseCase: PostsUseCase,
) : ViewModel() {

    private var _loaderState = mutableStateOf(LoaderState())
    val loaderState: State<LoaderState> = _loaderState

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

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

    fun loadPosts() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)
            val postsResult = postsUseCase.loadPosts().result
            _loaderState.value = loaderState.value.copy(isLoading = false)

            when (postsResult) {
                is Resource.Success -> {
                    if (postsResult.data.isNullOrEmpty()) {
                        _posts.emit(emptyList())
                    } else {
                        _posts.emit(postsResult.data)
                    }
                }

                is Resource.Error -> {
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

    fun logout() {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)
            val logoutResult = userUseCase.logout().result
            _loaderState.value = loaderState.value.copy(isLoading = false)

            when (logoutResult) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(LoginScreenDestination.route)
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            logoutResult.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }

    fun addPost(post: Post?) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)

            val newPost = if (post == null) {
                Post(
                    title = titleState.value.text,
                    text = textState.value.text
                )
            } else {
                post.title = titleState.value.text
                post.text = textState.value.text
                post
            }

            val addPostResult = postsUseCase.addOrUpdatePost(newPost)

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

    fun loadComments(postId: Int) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)
            val commentsResult = postsUseCase.loadComments(postId = postId).result
            _loaderState.value = loaderState.value.copy(isLoading = false)

            when (commentsResult) {
                is Resource.Success -> {
                    if (commentsResult.data.isNullOrEmpty()) {
                        _eventFlow.emit(
                            UiEvents.SnackbarEvent("No posts found")
                        )
                    } else {
                        _comments.emit(commentsResult.data)
                    }
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            commentsResult.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }

    fun addComment(comment: Comment) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)

            val addCommentResult = postsUseCase.addComment(
                comment = comment
            )

            _loaderState.value = loaderState.value.copy(isLoading = false)

            if (addCommentResult.textError != null) {
                _textState.value = textState.value.copy(error = addCommentResult.textError)
            }

            when (addCommentResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateUp
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            addCommentResult.result.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }

    fun editComment(comment: Comment) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)

            val editResult = postsUseCase.editComment(
                comment = comment
            )

            _loaderState.value = loaderState.value.copy(isLoading = false)

            if (editResult.textError != null) {
                _textState.value = textState.value.copy(error = editResult.textError)
            }

            when (editResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateUp
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            editResult.result.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(dispatcherProvider.io) {
            _loaderState.value = loaderState.value.copy(isLoading = false)

            val deleteResult = postsUseCase.deleteComment(comment = comment)

            _loaderState.value = loaderState.value.copy(isLoading = false)

            when (deleteResult.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateUp
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            deleteResult.result.message ?: "Error!"
                        )
                    )
                }

                else -> {

                }
            }
        }
    }
}