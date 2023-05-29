package dev.raju.consumrz.ui.screens.posts.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.ErrorDialog
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.navigation.getOrThrow
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.domain.enitities.Comment
import dev.raju.domain.enitities.Post
import dev.raju.domain.utils.UiState
import kotlinx.coroutines.launch

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

const val KEY_POST_DETAIL = "POST_DETAIL"

object PostDetailRoute : NavRoute<PostDetailViewModel> {

    override val route = "postdetail/{$KEY_POST_DETAIL}/"

    /**
     * Returns the route that can be used for navigating to this page.
     */
    fun get(id: Int): String = route.replace("{$KEY_POST_DETAIL}", "$id")

    fun getIdFrom(savedStateHandle: SavedStateHandle) =
        savedStateHandle.getOrThrow<Int>(KEY_POST_DETAIL)

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_POST_DETAIL) { type = NavType.IntType })

    @Composable
    override fun viewModel(): PostDetailViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: PostDetailViewModel) = PostDetailScreen(viewModel)
}

@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(state) {
        viewModel.getPost()
    }

    when (state) {
        is UiState.Empty -> {

        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Failure -> {
            ErrorDialog(message = "No data found")
        }

        is UiState.Success -> {
            Log.d("aarna", "posts success")
            if (state.data != null) {
                PostDetailComponent(
                    post = state.data!!,
                    onEditPostClick = {
                        viewModel.navigateToUpdatePost(it.id)
                    },
                    onCommentClick = {

                    },
                    onNewCommentClick = {
                        viewModel.navigateToNewComment()
                    }
                )
            } else {
                ErrorDialog(message = "No data found")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun PostDetailComponent(
    post: Post,
    onEditPostClick: (post: Post) -> Unit,
    onCommentClick: (comment: Comment) -> Unit,
    onNewCommentClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf(-1) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues.run {
                        16.dp
                    }),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextHeader(
                        label = stringResource(id = R.string.lorem_ipsum_title),
                        modifier = Modifier.padding(2.dp)
                    )

                    IconButton(
                        onClick = {
                            onEditPostClick.invoke(post)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit"
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = stringResource(id = R.string.lorem_ipsum_description), //post.text,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextHeader(
                        label = stringResource(id = R.string.comments),
                        modifier = Modifier.padding(2.dp)
                    )

                    IconButton(
                        onClick = {
                            onNewCommentClick.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }
                }

                if (post.comments.isNullOrEmpty()) {
                    Column(
                        Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        //verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No comments found",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                } else {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        /*val comments = mutableListOf<Comment>()
                        for (i in 1..20) {
                            comments.add(
                                Comment(
                                    id = 1,
                                    postId = post.id,
                                    commentText = stringResource(id = R.string.lorem_ipsum_comment)
                                )
                            )
                        }*/

                        LazyColumn(state = listState) {
                            items(items = post.comments!!) { comment ->
                                CommentItem(
                                    comment = comment,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .selectable(
                                            selected = post.id == selectedIndex,
                                            onClick = {
                                                selectedIndex = if (selectedIndex != post.id)
                                                    post.id else -1
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Selected index: $selectedIndex")
                                                }
                                                onCommentClick.invoke(comment)
                                            })
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CommentItem(
    comment: Comment,
    modifier: Modifier
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RectangleShape,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = comment.commentText,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val post = Post(
                id = 0,
                title = stringResource(id = R.string.lorem_ipsum_title),
                text = stringResource(id = R.string.lorem_ipsum_description)
            )
            val comments = mutableListOf<Comment>()
            for (i in 1..20) {
                comments.add(
                    Comment(
                        id = 1,
                        postId = post.id,
                        commentText = stringResource(id = R.string.lorem_ipsum_comment)
                    )
                )
            }
            post.comments = comments
            PostDetailComponent(
                post = post,
                onEditPostClick = {

                },
                onCommentClick = {

                },
                onNewCommentClick = {

                }
            )
        }
    }
}