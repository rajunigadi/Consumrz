package dev.raju.consumrz.ui.screens.posts.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.screens.posts.add.AddPostRoute
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.domain.enitities.Post
import kotlinx.coroutines.launch

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

object PostsRoute : NavRoute<PostsViewModel> {

    override val route = "posts"

    @Composable
    override fun viewModel(): PostsViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: PostsViewModel) = PostsScreen(viewModel)
}

@Composable
fun PostsScreen(
    viewModel: PostsViewModel
) {
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    PostsComponent(
        posts = posts,
        onNewPostClick = {
            viewModel.navigate(AddPostRoute.route)
        },
        onItemClick = {
            viewModel.navigateTpPostDetail(it.id)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostsComponent(
    posts: List<Post>?,
    onNewPostClick: () -> Unit,
    onItemClick: (post: Post) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextHeader(
                        label = stringResource(id = R.string.posts),
                        modifier = Modifier
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onNewPostClick.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            if (posts.isNullOrEmpty()) {
                Column(
                    Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_posts_found),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn(state = listState) {
                        items(items = posts) { post ->
                            PostItem(
                                post = post,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                                    .selectable(
                                        selected = post.id == selectedIndex,
                                        onClick = {
                                            selectedIndex = if (selectedIndex != post.id)
                                                post.id else -1
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Selected index: $selectedIndex")
                                            }
                                            onItemClick.invoke(post)
                                        })
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Transparent),
        modifier = modifier,
        shape = RectangleShape
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = post.text,
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
            PostsComponent(
                posts = emptyList(),
                onNewPostClick = {

                },
                onItemClick = {

                }
            )
        }
    }
}