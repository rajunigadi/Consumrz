package dev.raju.consumrz.ui.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.components.AppLogo
import dev.raju.consumrz.ui.components.DefaultButton
import dev.raju.consumrz.ui.components.DefaultSpacer
import dev.raju.consumrz.ui.components.LinkText
import dev.raju.consumrz.ui.components.TextHeader
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.screens.login.LoginRoute
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
    RegisterComponent(
        onBackClick = {
            viewModel.onBackClick()
        },
        onRegisterClick = { params ->
            viewModel.register(params)
        },
        onLoginClick = {
            viewModel.navigate(LoginRoute.route)
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterComponent(
    onBackClick: () -> Unit,
    onRegisterClick: (data: Map<String, String>) -> Unit,
    onLoginClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.register)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackClick.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val state by remember { mutableStateOf(FormState()) }

                var password: String by mutableStateOf("")
                var repeatPassword: String by mutableStateOf("")
                //val repeatPassword = remember { mutableStateOf(TextFieldValue()) }

                AppLogo()

                TextHeader(
                    label = stringResource(id = R.string.signing_up),
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
                                validators = listOf(Required()),
                                onValueChange = {
                                    password = it
                                }
                            ),
                            Field(
                                name = stringResource(id = R.string.repeat_password),
                                label = stringResource(id = R.string.repeat_password),
                                placeholder = stringResource(id = R.string.your_repeat_password),
                                keyboardType = KeyboardType.Password,
                                validators = listOf(Required()),
                                onValueChange = {
                                    repeatPassword = it
                                }
                            )
                        )
                    )
                    DefaultButton(
                        label = stringResource(id = R.string.register),
                        onButtonClick = {
                            if (state.validate()) {
                                println("password: $password and repeatPassword: $repeatPassword")
                                if (password == repeatPassword) {
                                    onRegisterClick.invoke(state.getData())
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Password & repeat password should match"
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
                DefaultSpacer(space = 16.dp)

                LinkText(
                    label = stringResource(id = R.string.already_have_account),
                    onClickListener = {
                        onLoginClick.invoke()
                    }
                )
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
            RegisterComponent(
                onBackClick = {

                },
                onRegisterClick = { params ->

                },
                onLoginClick = {

                }
            )
        }
    }
}