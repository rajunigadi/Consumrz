package dev.raju.consumrz.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.raju.consumrz.domain.model.Post

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPost(post: Post): Long

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<Post>

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    fun getPostById(id: Int): List<Post>

    @Update
    fun updatePost(post: Post)

    @Delete
    fun deletePost(post: Post)
}