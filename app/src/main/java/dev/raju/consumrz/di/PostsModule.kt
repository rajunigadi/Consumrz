package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.raju.consumrz.data.local.database.CommentDao
import dev.raju.consumrz.data.local.database.PostDao
import dev.raju.consumrz.data.repositories.PostsRepositoryImpl
import dev.raju.consumrz.domain.repositories.PostsRepository
import dev.raju.consumrz.domain.usecases.PostsUseCase
import dev.raju.consumrz.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@Module
@InstallIn(ViewModelComponent::class)
class PostsModule {

    @Provides
    @ViewModelScoped
    fun providesPostsRepository(
        postDao: PostDao,
        commentDao: CommentDao
    ): PostsRepository {
        return PostsRepositoryImpl(postDao = postDao, commentDao = commentDao)
    }

    @Provides
    @ViewModelScoped
    fun providesPostsUseCase(
        postsRepository: PostsRepository,
        dispatcherProvider: DispatcherProvider
    ): PostsUseCase {
        return PostsUseCase(repository = postsRepository, dispatcherProvider = dispatcherProvider)
    }
}