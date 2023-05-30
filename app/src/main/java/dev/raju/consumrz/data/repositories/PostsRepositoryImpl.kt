package dev.raju.consumrz.data.repositories

import coil.network.HttpException
import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.PostDao
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.repositories.PostsRepository
import dev.raju.consumrz.utils.Resource
import java.io.IOException

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
class PostsRepositoryImpl(
    private val postDao: PostDao
) : PostsRepository {

    override suspend fun loadPosts(): Resource<List<Post>> {
        return try {
            val posts = postDao.getAllPosts()
            if (posts.isEmpty()) {
                Resource.Error("No posts found")
            } else {
                Resource.Success(posts)
            }
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }
}