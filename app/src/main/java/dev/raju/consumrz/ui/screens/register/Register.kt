package dev.raju.consumrz.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.AppLogo
import dev.raju.consumrz.ui.components.DefaultButton
import dev.raju.consumrz.ui.components.DefaultSpacer
import dev.raju.consumrz.ui.components.EmailComponent
import dev.raju.consumrz.ui.components.LinkText
import dev.raju.consumrz.ui.components.PasswordComponent
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.screens.login.LoginScreen
import dev.raju.consumrz.ui.screens.login.LoginViewModel
import dev.raju.consumrz.ui.theme.ConsumrzTheme

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

object RegisterRoute : NavRoute<RegisterViewModel> {

    override val route = "register"

    @Composable
    override fun viewModel(): RegisterViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: RegisterViewModel) = RegisterScreen(viewModel)
}

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel
) {
    val context = LocalContext.current
    RegisterComponent(
        onRegisterClick = { email, password, repeatPassword ->
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
            if (password.isEmpty() and repeatPassword.isEmpty()) {
                //Toast.makeText(context, R.string.email_password_empty, Toast.LENGTH_SHORT).show()
            }
            if (password != repeatPassword) {
                //Toast.makeText(context, R.string.email_password_empty, Toast.LENGTH_SHORT).show()
            }
            if (email.isNotEmpty() and password.isNotEmpty()) {
                viewModel.register(email, password)
            }
        },
        onLoginClick = {
            /*navController.navigate(NavRoute.Login.path) {
                popUpTo(NavRoute.Register.path) {
                    inclusive = true
                }
            }*/
        }
    )
}

@Composable
private fun RegisterComponent(
    onRegisterClick: (email: String, password: String, repeatPassword: String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {

        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val repeatPassword = remember { mutableStateOf(TextFieldValue()) }

        AppLogo()

        TextHeader(
            label = stringResource(id = R.string.signing_up),
        )

        EmailComponent(onValueChange = {
            email.value = it
        })

        PasswordComponent(onValueChange = {
            password.value = it
        })

        PasswordComponent(
            label = stringResource(id = R.string.repeat_password),
            placeholder = stringResource(id = R.string.your_repeat_password),
            onValueChange = {
                repeatPassword.value = it
            })

        DefaultButton(
            label = stringResource(id = R.string.register),
            onButtonClick = {
                onRegisterClick.invoke(
                    email.value.text,
                    password.value.text,
                    repeatPassword.value.text
                )
            }
        )

        DefaultSpacer(space = 16.dp)

        LinkText(
            label = stringResource(id = R.string.already_have_account),
            onClickListener = {
                onLoginClick.invoke()
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
            RegisterComponent(
                onRegisterClick = { email, password, repeatPassword ->

                },
                onLoginClick = {

                }
            )
        }
    }
}