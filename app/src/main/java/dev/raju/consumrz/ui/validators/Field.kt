package dev.raju.consumrz.ui.validators

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.raju.consumrz.R

/**
 * Created by Rajashekhar Vanahalli on 27 May, 2023
 */

class Field(
    val name: String,
    val label: String,
    val placeholder: String,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val validators: List<Validator>,
    val onValueChange: (value: String) -> Unit = {
        // do nothing
    }
) {
    var textState: String by mutableStateOf("")
    var labelState: String by mutableStateOf(label)
    var errorState: Boolean by mutableStateOf(false)

    fun clear() {
        textState = ""
    }

    private fun showError(error: String) {
        errorState = true
        labelState = error
    }

    private fun hideError() {
        labelState = label
        errorState = false
    }

    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        var password: String by mutableStateOf(stringResource(id = R.string.password))
        var repeatPassword: String by mutableStateOf(stringResource(id = R.string.repeat_password))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            OutlinedTextField(
                value = textState,
                isError = errorState,
                label = { Text(text = labelState) },
                onValueChange = { value ->
                    hideError()
                    textState = value
                    if (label == password || label == repeatPassword) {
                        onValueChange.invoke(value)
                    }
                },
                placeholder = { Text(text = placeholder) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                shape = RoundedCornerShape(4.dp),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
        }
    }

    fun validate(): Boolean {
        return validators.map {
            when (it) {
                is Email -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(textState).matches()) {
                        showError(it.message)
                        return@map false
                    }
                    true
                }

                is Required -> {
                    if (textState.isEmpty()) {
                        showError(it.message)
                        return@map false
                    }
                    true
                }
            }
        }.all { it }
    }
}