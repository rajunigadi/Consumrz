package dev.raju.consumrz.utils

import android.util.Patterns

/**
 * Created by Rajashekhar Vanahalli on 01 June, 2023
 */
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence.isValidPassword() = this.length in 4..8