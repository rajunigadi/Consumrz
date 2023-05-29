package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.raju.data.local.CommentDao
import dev.raju.data.local.PostDao
import dev.raju.data.repositories.PostRepositoryImpl
import dev.raju.domain.repositories.PostRepository
import dev.raju.domain.usecases.PostUseCase
import dev.raju.domain.usecases.PostUseCaseImpl
import dev.raju.domain.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 26 May, 2023
 */
@Module
@InstallIn(ViewModelComponent::class)
class PostModule {

    @Provides
    @ViewModelScoped
    fun providesPostRepository(
        postDao: PostDao,
        commentDao: CommentDao
    ): PostRepository {
        return PostRepositoryImpl(postDao = postDao, commentDao = commentDao)
    }

    @Provides
    @ViewModelScoped
    fun providesPostUseCase(
        postRepository: PostRepository,
        dispatcherProvider: DispatcherProvider
    ): PostUseCase {
        return PostUseCaseImpl(repository = postRepository, dispatcherProvider = dispatcherProvider)
    }
}