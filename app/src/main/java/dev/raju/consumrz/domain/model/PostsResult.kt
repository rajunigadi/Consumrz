package dev.raju.consumrz.domain.model

import dev.raju.consumrz.utils.Resource

data class PostsResult(
    val error : String? = null,
    val result: Resource<List<Post>>? = null
)