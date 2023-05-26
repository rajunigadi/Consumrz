package dev.raju.consumrz.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
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
                Text(message?: stringResource(R.string.common_error_message))
            },
            confirmButton = {
                openDialog.value = false
            }
        )
    }
}