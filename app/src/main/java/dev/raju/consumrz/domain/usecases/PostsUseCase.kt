package dev.raju.consumrz.domain.usecases

import dev.raju.consumrz.domain.model.Comment
import dev.raju.consumrz.domain.model.CommentsListResult
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.model.PostListResult
import dev.raju.consumrz.domain.model.PostResult
import dev.raju.consumrz.domain.repositories.PostsRepository

class PostsUseCase(
    private val repository: PostsRepository
) {
    suspend fun loadPosts(): PostListResult {
        return PostListResult(
            result = repository.loadPosts()
        )
    }

    suspend fun addOrUpdatePost(
        post: Post
    ): PostResult {
        val titleError = if (post.title.isBlank()) "Post title cannot be blank" else null
        val textError = if (post.text.isBlank()) "Post text cannot be blank" else null

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

        return PostResult(
            result = if(post.id > 0) repository.editPost(post) else repository.addPost(post)
        )
    }

    suspend fun deletePost(post: Post): PostResult {
        return PostResult(
            result = repository.deletePost(post)
        )
    }

    suspend fun loadComments(postId: Int): CommentsListResult {
        return CommentsListResult(
            result = repository.loadComments(postId = postId)
        )
    }

    suspend fun addComment(
        comment: Comment
    ): PostResult {
        val textError = if (comment.text.isBlank()) "Comment text cannot be blank" else null

        if (textError != null) {
            return PostResult(
                textError = textError
            )
        }

        return PostResult(
            result = repository.addComment(comment = comment)
        )
    }

    suspend fun editComment(
        comment: Comment
    ): PostResult {
        val textError = if (comment.text.isBlank()) "Comment text cannot be blank" else null

        if (textError != null) {
            return PostResult(
                textError = textError
            )
        }

        return PostResult(
            result = repository.editComment(comment = comment)
        )
    }

    suspend fun deleteComment(comment: Comment): PostResult {
        return PostResult(
            result = repository.deleteComment(comment = comment)
        )
    }
}
