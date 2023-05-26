package dev.raju.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.raju.domain.enitities.User


/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class ConsumrzDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}