package dev.raju.consumrz.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val text: String,
) {
    @Ignore
    var comments: List<Comment>? = null
}