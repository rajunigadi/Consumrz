package dev.raju.consumrz.ui.screens.posts.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.DefaultButton
import dev.raju.consumrz.ui.components.ErrorDialog
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.navigation.getOrThrow
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.consumrz.ui.validators.Field
import dev.raju.consumrz.ui.validators.Form
import dev.raju.consumrz.ui.validators.FormState
import dev.raju.consumrz.ui.validators.Required
import dev.raju.domain.enitities.Post
import dev.raju.domain.enitities.PostParams
import dev.raju.domain.utils.UiState

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

const val KEY_POST_DETAIL = "POST_DETAIL"

object EditPostRoute : NavRoute<EditPostViewModel> {

    override val route = "editpost/{$KEY_POST_DETAIL}/"

    /**
     * Returns the route that can be used for navigating to this page.
     */
    fun get(id: Int): String = route.replace("{$KEY_POST_DETAIL}", "$id")

    fun getIdFrom(savedStateHandle: SavedStateHandle) =
        savedStateHandle.getOrThrow<Int>(KEY_POST_DETAIL)

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_POST_DETAIL) { type = NavType.IntType })

    @Composable
    override fun viewModel(): EditPostViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: EditPostViewModel) = EditPostScreen(viewModel)
}

@Composable
fun EditPostScreen(
    viewModel: EditPostViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
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

        }
    }

    EditPostComponent(
        post = Post(id = 0, title = "", text = ""),
        onSendClick = { params ->
            val postParams = PostParams.from(params)
            viewModel.editPost(postParams = postParams)
        })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun EditPostComponent(
    post: Post,
    onSendClick: (data: Map<String, String>) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val state by remember { mutableStateOf(FormState()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextHeader(
                        label = stringResource(id = R.string.add_post),
                        modifier = Modifier
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column {
                    Form(
                        state = state, fields = listOf(
                            Field(
                                name = stringResource(id = R.string.post_title),
                                value = "Default",
                                label = stringResource(id = R.string.post_title),
                                placeholder = stringResource(id = R.string.your_post_title),
                                keyboardType = KeyboardType.Text,
                                validators = listOf(Required())
                            ),
                            Field(
                                name = stringResource(id = R.string.post_text),
                                label = stringResource(id = R.string.post_text),
                                placeholder = stringResource(id = R.string.your_post_text),
                                keyboardType = KeyboardType.Text,
                                validators = listOf(Required())
                            )
                        )
                    )
                }
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    DefaultButton(
                        label = stringResource(id = R.string.send),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onButtonClick = {
                            if (state.validate()) {
                                onSendClick.invoke(state.getData())
                            }
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EditPostComponent(
                post = Post(id = 0, title = "", text = ""),
                onSendClick = {

                })
        }
    }
}