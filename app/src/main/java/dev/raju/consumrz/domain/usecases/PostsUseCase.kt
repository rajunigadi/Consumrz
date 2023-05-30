package dev.raju.consumrz.domain.usecases

import dev.raju.consumrz.data.local.models.PostRequest
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.model.PostListResult
import dev.raju.consumrz.domain.model.PostResult
import dev.raju.consumrz.domain.repositories.PostsRepository
import dev.raju.consumrz.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

class PostsUseCase(
    private val repository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun loadPosts(): PostListResult {
        return PostListResult(
            result = repository.loadPosts()
        )
    }

    suspend fun addOrUpdatePost(
        id: Int? = null,
        title: String,
        text: String
    ): PostResult {
        val titleError = if (title.isBlank()) "Title cannot be blank" else null
        val textError = if (text.isBlank()) "Text cannot be blank" else null

        if (titleError != null) {
            return PostResult(
                titleError = titleError
            )
        }

        if (textError != null) {
            return PostResult(
                textError = textError
            )
        }

        val postRequest = PostRequest(
            title = title.trim(),
            text = text.trim()
        )
        return if (id != null) {
            postRequest.id = id
            PostResult(
                result = repository.editPost(postRequest)
            )
        } else {
            PostResult(
                result = repository.addPost(postRequest)
            )
        }
    }

    suspend fun deletePost(post: Post): PostResult {
        return PostResult(
            result = repository.deletePost(post)
        )
    }
}
