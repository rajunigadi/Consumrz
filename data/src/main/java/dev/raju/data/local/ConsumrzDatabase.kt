package dev.raju.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.raju.domain.StringListConverter
import dev.raju.domain.enitities.Comment
import dev.raju.domain.enitities.Post
import dev.raju.domain.enitities.User

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Database(entities = [User::class, Post::class, Comment::class], version = 2, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class ConsumrzDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}