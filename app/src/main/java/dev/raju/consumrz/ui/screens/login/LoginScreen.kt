package dev.raju.consumrz.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.theme.ConsumrzTheme

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LoginComponent(
        onLoginClick = { email, password ->
            println("email: $email and password: $password")
            if(email.isEmpty() and password.isNotEmpty()){
                Toast.makeText(context, R.string.email_empty, Toast.LENGTH_SHORT).show()
            }
            if (password.isEmpty() and email.isNotEmpty()){
                Toast.makeText(context, R.string.password_empty, Toast.LENGTH_SHORT).show()
            }
            if(email.isEmpty() and password.isEmpty()){
                Toast.makeText(context, R.string.email_password_empty, Toast.LENGTH_SHORT).show()
            }
            if(email.isNotEmpty() and password.isNotEmpty()){
                viewModel.signIn(email, password)
            }
        },
        onRegisterClick = {
            navController.navigate(NavRoute.Register.path)
        },
        onForgotPasswordClick = {
            navController.navigate(NavRoute.Register.path)
        },
        onPrivacyClick = {
            navController.navigate(NavRoute.Register.path)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(24.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.welcome_to_app),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = stringResource(id = R.string.email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        OutlinedTextField(
            placeholder = { Text(text = stringResource(id = R.string.your_email)) },
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(4.dp),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(
            text = stringResource(id = R.string.password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        OutlinedTextField(
            placeholder = { Text(text = stringResource(id = R.string.your_password)) },
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(4.dp),
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                onLoginClick.invoke(email.value.text, password.value.text)
            }
        ) {
            Text(text = stringResource(id = R.string.login))
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            ClickableText(
                text = AnnotatedString(text = stringResource(id = R.string.register)),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                onClick = {
                    onForgotPasswordClick.invoke()
                },
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            ClickableText(
                text = AnnotatedString(text = stringResource(id = R.string.forgot_password)),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                onClick = {
                    onPrivacyClick.invoke()
                },
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            ClickableText(
                text = AnnotatedString(text = stringResource(id = R.string.privacy)),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                onClick = {
                    onPrivacyClick.invoke()
                },
                style = MaterialTheme.typography.labelLarge
            )
        }
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