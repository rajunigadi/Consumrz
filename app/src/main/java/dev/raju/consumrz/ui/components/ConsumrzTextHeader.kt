package dev.raju.consumrz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import dev.raju.consumrz.R

/**
 * Created by Rajashekhar Vanahalli on 31 May, 2023
 */

@Composable
fun ConsumrzTextHeader(
    text: String = stringResource(id = R.string.welcome_to_app),
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.welcome_to_app),
        fontSize = 26.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
}