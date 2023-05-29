package dev.raju.data.entities

/**
 * Created by Rajashekhar Vanahalli on 27 May, 2023
 */
data class PostDto(
    val id: Int,
    val title: String,
    val text: String,
    val comments: List<CommentDto>
)

data class CommentDto(
    val id: Int,
    val postId: Int,
    val commentText: String
)