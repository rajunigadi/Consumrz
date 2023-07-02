package dev.raju.consumrz.utils

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp

/**
 * Created by Rajashekhar Vanahalli on 01 June, 2023
 */
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence.isValidPassword() = this.length in 4..8



@Composable
fun Modifier.defaultFillScreen() = this.then(Modifier
    .fillMaxWidth()
    .verticalScroll(rememberScrollState())
    .padding(16.dp))