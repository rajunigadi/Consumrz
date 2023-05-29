package dev.raju.domain.repositories

import dev.raju.domain.enitities.Comment
import dev.raju.domain.enitities.Post
import dev.raju.domain.enitities.PostParams
import dev.raju.domain.utils.ResponseCodable

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
interface PostRepository {
    suspend fun getPosts(): ResponseCodable<List<Post>>
    suspend fun addPost(postParams: PostParams): ResponseCodable<Unit>
    suspend fun updatePost(post: Post): ResponseCodable<Unit>
    suspend fun addComment(comment: Comment): ResponseCodable<Unit>
    suspend fun updateComment(comment: Comment): ResponseCodable<Unit>
}