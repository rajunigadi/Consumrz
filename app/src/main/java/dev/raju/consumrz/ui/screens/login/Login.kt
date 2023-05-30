package dev.raju.consumrz.ui.screens.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.AppLogo
import dev.raju.consumrz.ui.components.DefaultButton
import dev.raju.consumrz.ui.components.DefaultSpacer
import dev.raju.consumrz.ui.components.LinkText
import dev.raju.consumrz.ui.components.ProgressButton
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.screens.posts.list.PostsRoute
import dev.raju.consumrz.ui.screens.register.RegisterRoute
import dev.raju.consumrz.ui.theme.ConsumrzTheme
import dev.raju.consumrz.ui.validators.Email
import dev.raju.consumrz.ui.validators.Field
import dev.raju.consumrz.ui.validators.Form
import dev.raju.consumrz.ui.validators.FormState
import dev.raju.consumrz.ui.validators.Required
import kotlinx.coroutines.launch

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

object LoginRoute : NavRoute<LoginViewModel> {

    override val route = "login"

    @Composable
    override fun viewModel(): LoginViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: LoginViewModel) = LoginScreen(viewModel)
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    LoginComponent(onLoginClick = { params ->
        viewModel.signIn(params = params)
    }, onRegisterClick = {
        viewModel.navigate(RegisterRoute.route)
    }, onForgotPasswordClick = {
        viewModel.navigate(RegisterRoute.route)
    }, onPrivacyClick = {
        viewModel.navigate(PostsRoute.route)
    })
}

@Composable
private fun LoginComponent(
    onLoginClick: (data: Map<String, String>) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize()
    ) {
        val state by remember { mutableStateOf(FormState()) }

        AppLogo()

        TextHeader(
            label = stringResource(id = R.string.welcome_to_app),
        )

        Column {
            Form(
                state = state, fields = listOf(
                    Field(
                        name = stringResource(id = R.string.email),
                        label = stringResource(id = R.string.email),
                        placeholder = stringResource(id = R.string.your_email),
                        keyboardType = KeyboardType.Email,
                        validators = listOf(Required(), Email())
                    ),
                    Field(
                        name = stringResource(id = R.string.password),
                        label = stringResource(id = R.string.password),
                        placeholder = stringResource(id = R.string.your_password),
                        keyboardType = KeyboardType.Password,
                        validators = listOf(Required())
                    )
                )
            )
            DefaultButton(
                label = stringResource(id = R.string.login),
                onButtonClick = {
                    if (state.validate()) {
                        onLoginClick.invoke(state.getData())
                    }
                }
            )
        }

        DefaultSpacer(space = 16.dp)

        LinkText(label = stringResource(id = R.string.register), onClickListener = {
            onRegisterClick.invoke()
        })

        LinkText(label = stringResource(id = R.string.forgot_password), onClickListener = {
            onForgotPasswordClick.invoke()
        })

        LinkText(label = stringResource(id = R.string.privacy), onClickListener = {
            onPrivacyClick.invoke()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            LoginComponent(onLoginClick = { data ->

            }, onRegisterClick = {

            }, onForgotPasswordClick = {

            }, onPrivacyClick = {

            })
        }
    }
}