package dev.raju.consumrz.domain.repositories

import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.utils.Resource

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
interface PostsRepository {
    suspend fun loadPosts(): Resource<List<Post>>
}