package dev.raju.consumrz.ui.screens.posts.comments

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.raju.consumrz.R
import dev.raju.consumrz.domain.model.Comment
import dev.raju.consumrz.ui.components.ConsumrzButton
import dev.raju.consumrz.ui.components.ConsumrzTextField
import dev.raju.consumrz.ui.components.ConsumrzTopAppBar
import dev.raju.consumrz.ui.screens.posts.PostsViewModel
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.consumrz.ui.theme.Purple700
import dev.raju.consumrz.utils.UiEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddCommentScreen(
    navigator: DestinationsNavigator,
    viewModel: PostsViewModel = hiltViewModel(),
    postId: Int,
    comment: Comment? = null
) {
    val loaderState = viewModel.loaderState.value
    val textState = viewModel.textState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isEdit by rememberSaveable { mutableStateOf(comment != null) }

    LaunchedEffect(key1 = true) {
        if (comment != null) {
            viewModel.setText(comment.text)
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
                    navigator.popBackStack() // clear login stack
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
                text = if (isEdit) stringResource(R.string.edit_comment) else stringResource(
                    R.string.new_comment
                ),
                onNavigationIconClick = {
                    navigator.navigateUp()
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        if (loaderState.isLoading) {
            Column(modifier = Modifier.padding(paddingValues)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ConsumrzTextField(
                        valueState = textState,
                        placeholderText = stringResource(id = R.string.your_comment_text),
                        labelText = stringResource(id = R.string.your_comment_text),
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
                            if(isEdit) {
                                if (comment != null) {
                                    comment?.text = textState.text
                                    viewModel.editComment(comment)
                                }
                            } else {
                                viewModel.addComment(Comment(postId = postId, text = textState.text))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //AddCommentScreen(EmptyDestinationsNavigator, 0, Comment(0, 1, "",0, ""))
        }
    }
}
