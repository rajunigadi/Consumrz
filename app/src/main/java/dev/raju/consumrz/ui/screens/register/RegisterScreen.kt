package dev.raju.consumrz.ui.screens.register

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import dev.raju.consumrz.ui.components.ConsumrzButton
import dev.raju.consumrz.ui.components.ConsumrzPasswordTextField
import dev.raju.consumrz.ui.components.ConsumrzTextButton
import dev.raju.consumrz.ui.components.ConsumrzTextField
import dev.raju.consumrz.ui.components.ConsumrzTopAppBar
import dev.raju.consumrz.ui.screens.destinations.LoginScreenDestination
import dev.raju.consumrz.ui.screens.destinations.RegisterScreenDestination
import dev.raju.consumrz.ui.theme.ConsumrzTheme
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
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val loaderState = viewModel.loaderState.value

    val firstNameState = viewModel.firstNameState.value
    val lastNameState = viewModel.lastNameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val repeatPasswordState = viewModel.repeatPasswordState.value

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
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
                        message = "Registration Successful",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ConsumrzTopAppBar(
                text = stringResource(id = R.string.sign_up),
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

                    Spacer(modifier = Modifier.height(16.dp))

                    ConsumrzTextField(
                        valueState = firstNameState,
                        placeholderText = stringResource(id = R.string.your_firstname),
                        labelText = stringResource(id = R.string.your_firstname),
                        onValueChanged = {
                            viewModel.setFirstName(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzTextField(
                        valueState = lastNameState,
                        placeholderText = stringResource(id = R.string.your_lastname),
                        labelText = stringResource(id = R.string.your_lastname),
                        onValueChanged = {
                            viewModel.setLastName(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzTextField(
                        valueState = emailState,
                        placeholderText = stringResource(id = R.string.your_email),
                        labelText = stringResource(id = R.string.your_email),
                        onValueChanged = {
                            viewModel.setEmail(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzPasswordTextField(
                        valueState = passwordState,
                        placeholderText = stringResource(id = R.string.your_password),
                        labelText = stringResource(id = R.string.your_password),
                        onValueChanged = {
                            viewModel.setPassword(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzPasswordTextField(
                        valueState = repeatPasswordState,
                        placeholderText = stringResource(id = R.string.your_repeat_password),
                        labelText = stringResource(id = R.string.your_repeat_password),
                        onValueChanged = {
                            viewModel.setRepeatPassword(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumrzButton(
                        text = stringResource(id = R.string.sign_up),
                        onClick = {
                            viewModel.register()
                        }
                    )

                    ConsumrzTextButton(
                        text = stringResource(R.string.already_have_an_account),
                        styledText = stringResource(R.string.sign_in),
                        onClick = {
                            navigator.popBackStack()
                            navigator.navigate(LoginScreenDestination)
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
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            RegisterScreen(EmptyDestinationsNavigator)
        }
    }
}
