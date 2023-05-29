package dev.raju.data.repositories

import dev.raju.data.local.CommentDao
import dev.raju.data.local.PostDao
import dev.raju.domain.enitities.Comment
import dev.raju.domain.enitities.Post
import dev.raju.domain.enitities.PostParams
import dev.raju.domain.repositories.PostRepository
import dev.raju.domain.utils.ErrorCodable
import dev.raju.domain.utils.ResponseCodable

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
class PostRepositoryImpl(
    private val postDao: PostDao,
    private val commentDao: CommentDao
) : PostRepository {

    override suspend fun getPosts(): ResponseCodable<List<Post>> {
        return try {
            val posts = postDao.getAllPosts()
            for(post in posts) {
                val comments = commentDao.getAllComments(post.id)
                post.comments = comments
            }
            ResponseCodable(data = posts)
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong!"
                    )
                )
            )
        }
    }

    override suspend fun addPost(postParams: PostParams): ResponseCodable<Unit> {
        return try {
            val rowId = postDao.addPost(post = Post(title = postParams.title, text = postParams.text))
            if (rowId > 0) {
                ResponseCodable(data = Unit)
            } else {
                ResponseCodable(
                    errors = arrayOf(
                        ErrorCodable(
                            errorCode = 100,
                            errorMessage = "Unable to add new post"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong!"
                    )
                )
            )
        }
    }

    override suspend fun updatePost(post: Post): ResponseCodable<Unit> {
        return try {
            val rowId = postDao.updatePost(post)
            ResponseCodable(data = rowId)
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong!"
                    )
                )
            )
        }
    }

    override suspend fun addComment(comment: Comment): ResponseCodable<Unit> {
        return try {
            val rowId = commentDao.addComment(comment)
            if (rowId > 0) {
                ResponseCodable(data = Unit)
            } else {
                ResponseCodable(
                    errors = arrayOf(
                        ErrorCodable(
                            errorCode = 100,
                            errorMessage = "Unable to add new comment"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong!"
                    )
                )
            )
        }
    }

    override suspend fun updateComment(comment: Comment): ResponseCodable<Unit> {
        return try {
            val rowId = commentDao.updateComment(comment)
            ResponseCodable(data = rowId)
        } catch (e: Exception) {
            ResponseCodable(
                errors = arrayOf(
                    ErrorCodable(
                        errorCode = 100,
                        errorMessage = "Something went wrong!"
                    )
                )
            )
        }
    }
}