package dev.raju.consumrz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.raju.consumrz.R

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Composable
fun ErrorDialog(message: String?) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.app_name))
            },
            text = {
                Text(message ?: stringResource(R.string.common_error_message))
            },
            confirmButton = {
                openDialog.value = false
            }
        )
    }
}

@Composable
fun DefaultButton(
    label: String = stringResource(id = R.string.login),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
    onButtonClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = {
            onButtonClick.invoke()
        },
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(text = label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailComponent(
    label: String = stringResource(id = R.string.email),
    placeholder: String = stringResource(id = R.string.your_email),
    onValueChange: (email: TextFieldValue) -> Unit,
) {
    val email = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = label,
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
            placeholder = { Text(text = placeholder) },
            value = email.value,
            onValueChange = {
                email.value = it
                onValueChange.invoke(email.value)
            },
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordComponent(
    label: String = stringResource(id = R.string.password),
    placeholder: String = stringResource(id = R.string.your_password),
    onValueChange: (password: TextFieldValue) -> Unit,
) {
    val password = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = label,
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
            placeholder = { Text(text = placeholder) },
            value = password.value,
            onValueChange = {
                password.value = it
                onValueChange.invoke(password.value)
            },
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
    }
}

@Composable
fun LinkText(
    label: String = stringResource(id = R.string.register),
    onClickListener: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ClickableText(
                text = AnnotatedString(text = label),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                onClick = {
                    onClickListener.invoke()
                },
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
    }
}

@Composable
fun DefaultSpacer(
    space: Dp = 16.dp
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(space)
    )
}