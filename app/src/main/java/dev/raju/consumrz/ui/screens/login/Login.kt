package dev.raju.consumrz.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.AppLogo
import dev.raju.consumrz.ui.components.DefaultButton
import dev.raju.consumrz.ui.components.DefaultSpacer
import dev.raju.consumrz.ui.components.EmailComponent
import dev.raju.consumrz.ui.components.LinkText
import dev.raju.consumrz.ui.components.PasswordComponent
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.screens.posts.PostsRoute
import dev.raju.consumrz.ui.screens.register.RegisterRoute
import dev.raju.consumrz.ui.theme.ConsumrzTheme

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
    LoginComponent(
        onLoginClick = { email, password ->
            println("email: $email and password: $password")
            if (email.isEmpty() and password.isNotEmpty()) {
                Toast.makeText(context, R.string.email_empty, Toast.LENGTH_SHORT).show()
            }
            if (password.isEmpty() and email.isNotEmpty()) {
                Toast.makeText(context, R.string.password_empty, Toast.LENGTH_SHORT).show()
            }
            if (email.isEmpty() and password.isEmpty()) {
                Toast.makeText(context, R.string.email_password_empty, Toast.LENGTH_SHORT).show()
            }
            if (email.isNotEmpty() and password.isNotEmpty()) {
                viewModel.signIn(email, password)
            }
        },
        onRegisterClick = {
            viewModel.navigate(RegisterRoute.route)
        },
        onForgotPasswordClick = {
            viewModel.navigate(RegisterRoute.route)
        },
        onPrivacyClick = {
            viewModel.navigate(PostsRoute.route)
        }
    )
}

@Composable
private fun LoginComponent(
    onLoginClick: (email: String, password: String) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {

        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

        AppLogo()

        TextHeader(
            label = stringResource(id = R.string.welcome_to_app),
        )

        EmailComponent(onValueChange = {
            email.value = it
        })

        PasswordComponent(onValueChange = {
            password.value = it
        })

        DefaultButton(
            label = stringResource(id = R.string.login),
            onButtonClick = {
                onLoginClick.invoke(email.value.text, password.value.text)
            }
        )

        DefaultSpacer(space = 16.dp)

        LinkText(
            label = stringResource(id = R.string.register),
            onClickListener = {
                onRegisterClick.invoke()
            }
        )

        LinkText(
            label = stringResource(id = R.string.forgot_password),
            onClickListener = {
                onForgotPasswordClick.invoke()
            }
        )

        LinkText(
            label = stringResource(id = R.string.privacy),
            onClickListener = {
                onPrivacyClick.invoke()
            }
        )
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
            LoginComponent(
                onLoginClick = { email, password ->

                },
                onRegisterClick = {

                },
                onForgotPasswordClick = {

                },
                onPrivacyClick = {

                }
            )
        }
    }
}