package dev.raju.consumrz.ui.validators

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.toLowerCase

/**
 * Created by Rajashekhar Vanahalli on 27 May, 2023
 */
@Composable
fun Form(state: FormState, fields: List<Field>){
    state.fields = fields

    Column {
        fields.forEach {
            it.Content()
        }
    }
}

class FormState {
    var fields: List<Field> = listOf()
        set(value) { field = value }

    fun validate(): Boolean {
        var valid = true
        for (field in fields) if (!field.validate()) {
            valid = false
            break
        }
        return valid
    }

    fun getData(): Map<String, String> = fields.map { it.name.lowercase() to it.textState }.toMap()
}