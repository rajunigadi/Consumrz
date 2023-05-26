package dev.raju.domain.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password")
    val password: String)