package dev.raju.consumrz.ui.screens.posts.list

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.raju.consumrz.R
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.ui.components.ConsumrzActionIconButton
import dev.raju.consumrz.ui.screens.destinations.AddPostScreenDestination
import dev.raju.consumrz.ui.screens.destinations.LoginScreenDestination
import dev.raju.consumrz.ui.screens.destinations.PostDetailScreenDestination
import dev.raju.consumrz.ui.screens.posts.PostsViewModel
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.consumrz.ui.theme.PurpleBg
import dev.raju.consumrz.utils.UiEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Destination
@Composable
fun PostsScreen(
    navigator: DestinationsNavigator,
    viewModel: PostsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf(-1) }
    val loaderState = viewModel.loaderState.value
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.loadPosts()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                is UiEvents.NavigateEvent -> {
                    if (event.route == LoginScreenDestination.route) {
                        navigator.popBackStack()
                    }
                    navigator.navigate(event.route)
                }

                is UiEvents.NavigateUp -> {
                    navigator.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.posts),
                        fontSize = 26.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                },
                actions = {
                    ConsumrzActionIconButton(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = stringResource(R.string.logout),
                        onClick = {
                            viewModel.logout()
                        }
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                containerColor = PurpleBg,
                contentColor = Color.White,
                onClick = {
                    navigator.navigate(AddPostScreenDestination(post = null))
                }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End, // FabPosition.Center
    ) { paddingValues ->
        if (loaderState.isLoading) {
            Column {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        } else {
            if (posts.isEmpty()) {
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
                Column {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LazyColumn(state = listState) {
                            items(items = posts) { post ->
                                PostItem(
                                    navigator = navigator,
                                    viewModel = viewModel,
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
                                                navigator.navigate(PostDetailScreenDestination(post))
                                            })
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(
    navigator: DestinationsNavigator,
    viewModel: PostsViewModel,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f, fill = false),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (post.enableModify) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                    ) {
                        ConsumrzActionIconButton(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.edit),
                            onClick = {
                                navigator.navigate(AddPostScreenDestination(post = post))
                            }
                        )
                        ConsumrzActionIconButton(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete),
                            onClick = {
                                viewModel.deletePost(post = post)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = post.text,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 4.dp),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PostsScreen(navigator = EmptyDestinationsNavigator)
        }
    }
}
