package dev.raju.consumrz.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import dev.raju.consumrz.ui.components.ConsumrzTextHeader
import dev.raju.consumrz.ui.screens.destinations.ForgotPasswordScreenDestination
import dev.raju.consumrz.ui.screens.destinations.PrivacyScreenDestination
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
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val loaderState = viewModel.loaderState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value

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
                    navigator.popBackStack() // clear login stack
                    navigator.navigate(event.route)
                    snackbarHostState.showSnackbar(
                        message = "Login Successful",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.padding(48.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loaderState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                Spacer(modifier = Modifier.height(16.dp))

                ConsumrzTextHeader(
                    text = stringResource(id = R.string.welcome_to_app)
                )

                Spacer(modifier = Modifier.height(32.dp))

                ConsumrzTextField(
                    valueState = emailState,
                    placeholderText = stringResource(id = R.string.your_email),
                    labelText = stringResource(id = R.string.your_email),
                    onValueChanged = {
                        viewModel.setEmail(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = {
                        navigator.navigate(PrivacyScreenDestination)
                    }) {
                        Text(
                            text = stringResource(id = R.string.privacy),
                            color = Color.Black
                        )
                    }

                    TextButton(onClick = {
                        navigator.navigate(ForgotPasswordScreenDestination)
                    }) {
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ConsumrzButton(
                    text = stringResource(id = R.string.login),
                    onClick = {
                        viewModel.signInUser()
                    }
                )

                ConsumrzTextButton(
                    text = stringResource(R.string.don_t_have_an_account),
                    styledText = stringResource(R.string.sign_up),
                    onClick = {
                        navigator.popBackStack()
                        navigator.navigate(RegisterScreenDestination)
                    }
                )
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
            LoginScreen(EmptyDestinationsNavigator)
        }
    }
}
