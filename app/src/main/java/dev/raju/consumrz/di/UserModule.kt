package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.raju.data.local.UserDao
import dev.raju.data.repositories.UserRepositoryImpl
import dev.raju.domain.repositories.DatastoreRepository
import dev.raju.domain.repositories.UserRepository
import dev.raju.domain.usecases.UserUseCase
import dev.raju.domain.usecases.UserUseCaseImpl
import dev.raju.domain.utils.DispatcherProvider

/**
 * Created by Rajashekhar Vanahalli on 26 May, 2023
 */
@Module
@InstallIn(ViewModelComponent::class)
class UserModule {

    @Provides
    @ViewModelScoped
    fun providesUserRepository(
        userDao: UserDao,
        datastoreRepository: DatastoreRepository
    ): UserRepository {
        return UserRepositoryImpl(userDao = userDao, datastoreRepository = datastoreRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesUserUseCase(
        userRepository: UserRepository,
        dispatcherProvider: DispatcherProvider
    ): UserUseCase {
        return UserUseCaseImpl(repository = userRepository, dispatcherProvider = dispatcherProvider)
    }
}