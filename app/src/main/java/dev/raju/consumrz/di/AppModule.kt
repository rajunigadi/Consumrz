package dev.raju.consumrz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.raju.domain.utils.DispatcherProvider
import dev.raju.domain.utils.DispatcherProviderImpl
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}