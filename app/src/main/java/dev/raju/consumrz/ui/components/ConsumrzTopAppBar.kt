package dev.raju.consumrz.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.raju.consumrz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumrzTopAppBar(
    text: String = stringResource(id = R.string.register),
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = text,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                //navigator.navigateUp()
                onNavigationIconClick.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}