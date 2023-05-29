package dev.raju.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Rajashekhar Vanahalli on 28 May, 2023
 */

abstract class TypeConverters<T> {

    @TypeConverter
    fun listToString(value: List<T>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToList(value: String?): List<T>? {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(value, type)
    }
}

class StringListConverter: TypeConverters<String>()