package dev.raju.consumrz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import dev.raju.consumrz.R
import dev.raju.consumrz.utils.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumrzPasswordTextField(
    valueState: TextFieldState,
    placeholderText: String,
    labelText: String,
    onValueChanged: (value: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        capitalization = KeyboardCapitalization.Sentences
    ),
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueState.text,
        label = {
            Text(text = labelText)
        },
        onValueChange = {
            onValueChanged.invoke(it)
        },
        placeholder = {
            Text(text = placeholderText)
        },
        keyboardOptions = keyboardOptions,
        isError = valueState.error != null,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            // Localized description for accessibility services
            val description =
                if (passwordVisible) stringResource(id = R.string.hide_password) else stringResource(
                    id = R.string.show_password
                )

            // Toggle button to hide or display password
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
    if (valueState.error != "") {
        Text(
            text = valueState.error ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}