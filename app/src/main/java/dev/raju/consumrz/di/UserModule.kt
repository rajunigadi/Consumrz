package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.repositories.UserRepositoryImpl
import dev.raju.consumrz.domain.repositories.UserRepository
import dev.raju.consumrz.domain.usecases.UserUseCase
import dev.raju.consumrz.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 26 May, 2023
 */
@Module
@InstallIn(ViewModelComponent::class)
class UserModule {

    @Provides
    @ViewModelScoped
    fun providesUserRepository(
        preferences: AuthPreferences
    ): UserRepository {
        return UserRepositoryImpl(preferences = preferences)
    }

    @Provides
    @ViewModelScoped
    fun providesUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ): UserUseCase {
        return UserUseCase(repository = userRepository, dispatcherProvider = dispatcherProvider)
    }
}