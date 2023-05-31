package dev.raju.consumrz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.raju.consumrz.R
import dev.raju.consumrz.ui.theme.Purple700

/**
 * Created by Rajashekhar Vanahalli on 31 May, 2023
 */
@Composable
fun ConsumrzButton(
    text: String = stringResource(id = R.string.send),
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple700,
            contentColor = Color.White
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}