package dev.raju.consumrz.domain.usecases

import dev.raju.consumrz.domain.model.PostsResult
import dev.raju.consumrz.domain.repositories.PostsRepository
import dev.raju.consumrz.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

class PostsUseCase(
    private val repository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun loadPosts(): PostsResult {
        return PostsResult(
            result = repository.loadPosts()
        )
    }
}
