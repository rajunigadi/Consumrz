package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.UserDao
import dev.raju.consumrz.data.repositories.UserRepositoryImpl
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.domain.usecases.UserUseCase

@Module
@InstallIn(ViewModelComponent::class)
class UserModule {

    @Provides
    @ViewModelScoped
    fun providesUserRepository(
        userDao: UserDao,
        preferences: AuthPreferences
    ): UserRepository {
        return UserRepositoryImpl(userDao = userDao, preferences = preferences)
    }

    @Provides
    @ViewModelScoped
    fun providesUserUseCase(
        userRepository: UserRepository
    ): UserUseCase {
        return UserUseCase(repository = userRepository)
    }
}