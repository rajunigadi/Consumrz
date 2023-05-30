package dev.raju.consumrz.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.raju.consumrz.data.local.AuthPreferences
import dev.raju.consumrz.data.local.database.CommentDao
import dev.raju.consumrz.data.local.database.ConsumrzDatabase
import dev.raju.consumrz.data.local.database.PostDao
import dev.raju.consumrz.data.local.database.UserDao
import dev.raju.consumrz.utils.DispatcherProvider
import dev.raju.consumrz.utils.DispatcherProviderImpl
import dev.raju.consumrz.utils.Constants.AUTH_PREFERENCES
import dev.raju.consumrz.utils.Constants.CONSUMRZ_DATABASE
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

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(AUTH_PREFERENCES)
            }
        )

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>) =
        AuthPreferences(dataStore)

    @Provides
    @Singleton
    fun providesConsumrzDatabase(@ApplicationContext context: Context): ConsumrzDatabase {
        return Room.databaseBuilder(
            context, ConsumrzDatabase::class.java, CONSUMRZ_DATABASE
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesUserDao(consumrzDatabase: ConsumrzDatabase): UserDao {
        return consumrzDatabase.userDao()
    }

    @Provides
    @Singleton
    fun providesPostDao(consumrzDatabase: ConsumrzDatabase): PostDao {
        return consumrzDatabase.postDao()
    }

    @Provides
    @Singleton
    fun providesCommentDao(consumrzDatabase: ConsumrzDatabase): CommentDao {
        return consumrzDatabase.commentDao()
    }
}