package dev.raju.consumrz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import dev.raju.consumrz.ui.theme.PurpleBg

@Composable
fun ConsumrzTextButton(
    text: String,
    styledText: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.Black)
                ) {
                    append(text)
                }
                append(" ")
                withStyle(
                    style = SpanStyle(color = PurpleBg, fontWeight = FontWeight.Bold)
                ) {
                    append(styledText)
                }
            },
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}