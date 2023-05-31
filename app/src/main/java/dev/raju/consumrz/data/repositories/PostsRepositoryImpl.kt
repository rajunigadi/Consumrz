package dev.raju.consumrz.data.repositories

import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.CommentDao
import dev.raju.consumrz.data.local.database.PostDao
import dev.raju.consumrz.data.local.database.UserDao
import dev.raju.consumrz.domain.model.Comment
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.model.User
import dev.raju.consumrz.domain.repositories.PostsRepository
import dev.raju.consumrz.utils.Resource
import java.io.IOException

class PostsRepositoryImpl(
    private val preferences: AuthPreferences,
    private val userDao: UserDao,
    private val postDao: PostDao,
    private val commentDao: CommentDao
) : PostsRepository {

    override suspend fun loadPosts(): Resource<List<Post>> {
        return try {
            val posts = postDao.getAllPosts()
            if (posts.isEmpty()) {
                Resource.Error("No posts found")
            } else {
                val user = getLoggedInUser()
                posts.forEach { post ->
                    val comments = commentDao.getAllComments(postId = post.id)
                    post.comments = comments
                    if(post.userId == user?.id) {
                        post.enableModify = true
                    }
                }
                Resource.Success(posts)
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun addPost(post: Post): Resource<Unit> {
        return try {
            val user = getLoggedInUser()
            post.userId = user?.id ?: 0
            post.username = "${user?.firstName} ${user?.lastName}"

            val rowId = postDao.addPost(post)
            if (rowId > 0) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Unable to add new post")
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun editPost(post: Post): Resource<Unit> {
        return try {
            postDao.updatePost(post)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun deletePost(post: Post): Resource<Unit> {
        return try {
            postDao.deletePost(post)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun loadComments(postId: Int): Resource<List<Comment>> {
        return try {
            val user = getLoggedInUser()
            val comments = commentDao.getAllComments(postId = postId)
            if (comments.isEmpty()) {
                Resource.Error("No comments found")
            } else {
                comments.forEach { comment ->
                    if(comment.userId == user?.id) {
                        comment.enableModify = true
                    }
                }
                Resource.Success(comments)
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun addComment(comment: Comment): Resource<Unit> {
        return try {
            val user = getLoggedInUser()
            comment.userId = user?.id ?: 0
            comment.username = "${user?.firstName} ${user?.lastName}"

            val rowId = commentDao.addComment(comment = comment)
            if (rowId > 0) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Unable to add new comment")
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun editComment(comment: Comment): Resource<Unit> {
        return try {
            commentDao.updateComment(comment = comment)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }
    override suspend fun deleteComment(comment: Comment): Resource<Unit> {
        return try {
            commentDao.deleteComment(comment = comment)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }


    private suspend fun getLoggedInUser(): User? {
        val email = preferences.getEmail().getOrDefault("")
        val users = userDao.findUserByEmail(email = email)
        return if ((users?.isNotEmpty() == true) && (users[0].email == email)) {
            users[0]
        } else {
            null
        }
    }
}