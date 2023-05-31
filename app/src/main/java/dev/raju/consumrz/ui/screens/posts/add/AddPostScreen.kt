package dev.raju.consumrz.ui.screens.posts.add

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.raju.consumrz.R
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.ui.components.ConsumrzButton
import dev.raju.consumrz.ui.components.ConsumrzTextField
import dev.raju.consumrz.ui.components.ConsumrzTopAppBar
import dev.raju.consumrz.ui.screens.posts.PostsViewModel
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.consumrz.utils.UiEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddPostScreen(
    navigator: DestinationsNavigator,
    viewModel: PostsViewModel = hiltViewModel(),
    post: Post? = null
) {
    val loaderState = viewModel.loaderState.value
    val titleState = viewModel.titleState.value
    val textState = viewModel.textState.value

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isEdit by rememberSaveable { mutableStateOf(post != null) }

    LaunchedEffect(key1 = true) {
        if (post != null) {
            viewModel.setTitle(post.title)
            viewModel.setText(post.text)
        }
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
                    navigator.popBackStack()
                    navigator.navigate(event.route)
                    snackbarHostState.showSnackbar(
                        message = "New post added successfully",
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEvents.NavigateUp -> {
                    navigator.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ConsumrzTopAppBar(
                text = if (isEdit) stringResource(R.string.edit_post) else stringResource(R.string.new_post),
                onNavigationIconClick = {
                    navigator.navigateUp()
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        if (loaderState.isLoading) {
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        } else {
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ConsumrzTextField(
                        valueState = titleState,
                        placeholderText = stringResource(id = R.string.title),
                        labelText = stringResource(id = R.string.title),
                        onValueChanged = {
                            viewModel.setTitle(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzTextField(
                        valueState = textState,
                        placeholderText = stringResource(id = R.string.text),
                        labelText = stringResource(id = R.string.text),
                        onValueChanged = {
                            viewModel.setText(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzButton(
                        text = stringResource(id = R.string.send),
                        onClick = {
                            viewModel.addPost(post)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPostScreenPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddPostScreen(EmptyDestinationsNavigator)
        }
    }
}
