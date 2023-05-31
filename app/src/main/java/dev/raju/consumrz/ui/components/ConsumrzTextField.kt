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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import dev.raju.consumrz.R
import dev.raju.consumrz.utils.TextFieldState

/**
 * Created by Rajashekhar Vanahalli on 31 May, 2023
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumrzTextField(
    valueState: TextFieldState,
    placeholderText: String,
    labelText: String,
    onValueChanged: (value: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        capitalization = KeyboardCapitalization.Sentences
    ),
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueState.text,
        label = {
            Text(text = labelText)
        },
        onValueChange = {
            onValueChanged.invoke(it)
            //viewModel.setFirstName(it)
        },
        placeholder = {
            Text(text = placeholderText)
        },
        keyboardOptions = keyboardOptions,
        isError = valueState.error != null,
        trailingIcon = trailingIcon
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