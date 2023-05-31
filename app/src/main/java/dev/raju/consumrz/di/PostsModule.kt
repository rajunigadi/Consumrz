package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.CommentDao
import dev.raju.consumrz.data.local.database.PostDao
import dev.raju.consumrz.data.local.database.UserDao
import dev.raju.consumrz.data.repositories.PostsRepositoryImpl
import dev.raju.consumrz.domain.repositories.PostsRepository
import dev.raju.consumrz.domain.usecases.PostsUseCase

@Module
@InstallIn(ViewModelComponent::class)
class PostsModule {

    @Provides
    @ViewModelScoped
    fun providesPostsRepository(
        authPreferences: AuthPreferences,
        userDao: UserDao,
        postDao: PostDao,
        commentDao: CommentDao
    ): PostsRepository {
        return PostsRepositoryImpl(
            preferences = authPreferences,
            userDao = userDao,
            postDao = postDao,
            commentDao = commentDao
        )
    }

    @Provides
    @ViewModelScoped
    fun providesPostsUseCase(
        postsRepository: PostsRepository
    ): PostsUseCase {
        return PostsUseCase(repository = postsRepository)
    }
}