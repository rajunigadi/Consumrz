package dev.raju.domain.enitities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Rajashekhar Vanahalli on 27 May, 2023
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

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val postId: Int,
    val commentText: String
)

data class PostParams(val title: String, val text: String) {
    companion object {
        fun from(map: Map<String, String>) = object {
            val title by map
            val text by map

            val data = PostParams(title, text)
        }.data
    }
}
