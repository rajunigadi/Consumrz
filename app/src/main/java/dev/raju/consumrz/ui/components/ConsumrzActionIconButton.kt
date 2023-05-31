package dev.raju.consumrz.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import dev.raju.consumrz.R

@Composable
fun ConsumrzActionIconButton(
    imageVector: ImageVector = Icons.Filled.Add,
    contentDescription: String = stringResource(R.string.add),
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick.invoke()
        }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}