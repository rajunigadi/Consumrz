package dev.raju.consumrz.ui.validators

/**
 * Created by Rajashekhar Vanahalli on 27 May, 2023
 */
private const val EMAIL_MESSAGE = "invalid email address"
private const val REQUIRED_MESSAGE = "this field is required"

sealed interface Validator
open class Email(var message: String = EMAIL_MESSAGE): Validator
open class Required(var message: String = REQUIRED_MESSAGE): Validator
