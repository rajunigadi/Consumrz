package dev.raju.consumrz.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val postId: Int,
    val text: String
)