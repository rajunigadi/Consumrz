package dev.raju.consumrz.domain.model

import dev.raju.consumrz.utils.Resource

data class PostListResult(
    val error : String? = null,
    val result: Resource<List<Post>>? = null
)

data class CommentsListResult(
    val error : String? = null,
    val result: Resource<List<Comment>>? = null
)

data class PostResult(
    val titleError : String? = null,
    val textError: String? = null,
    val result: Resource<Unit>? = null
)