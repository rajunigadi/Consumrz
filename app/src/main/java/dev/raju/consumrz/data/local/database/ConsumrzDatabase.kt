package dev.raju.consumrz.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.raju.consumrz.domain.model.Comment
import dev.raju.consumrz.domain.model.Post
import dev.raju.consumrz.domain.model.User

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Database(entities = [User::class, Post::class, Comment::class], version = 1, exportSchema = false)
abstract class ConsumrzDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}