package dev.raju.consumrz.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.raju.data.local.ConsumrzDatabase
import dev.raju.data.local.UserDao
import dev.raju.data.repositories.DatastoreRepositoryImpl
import dev.raju.domain.repositories.DatastoreRepository
import javax.inject.Singleton

/**
 * Created by Rajashekhar Vanahalli on 26 May, 2023
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    @Provides
    @Singleton
    fun providesConsumrzDatabase(@ApplicationContext context: Context): ConsumrzDatabase {
        return Room.databaseBuilder(
            context, ConsumrzDatabase::class.java, "ConsumrzDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesUserDao(consumrzDatabase: ConsumrzDatabase): UserDao {
        return consumrzDatabase.userDao()
    }

    @Provides
    @Singleton
    fun providesDatastoreRepository(@ApplicationContext context: Context): DatastoreRepository {
        return DatastoreRepositoryImpl(context.userPreferencesDataStore)
    }
}