package dev.raju.consumrz.domain.repositories

import dev.raju.consumrz.data.local.models.PostRequest
import dev.raju.consumrz.domain.model.Comment
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.utils.Resource

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
interface PostsRepository {
    suspend fun loadPosts(): Resource<List<Post>>
    suspend fun addPost(postRequest: PostRequest): Resource<Unit>
    suspend fun editPost(postRequest: PostRequest): Resource<Unit>
    suspend fun deletePost(post: Post): Resource<Unit>

    suspend fun loadComments(postId: Int): Resource<List<Comment>>
    suspend fun addComment(comment: Comment): Resource<Unit>
    suspend fun editComment(comment: Comment): Resource<Unit>
    suspend fun deleteComment(comment: Comment): Resource<Unit>
}