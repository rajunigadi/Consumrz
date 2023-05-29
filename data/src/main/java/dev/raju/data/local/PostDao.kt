package dev.raju.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.raju.domain.enitities.Post
import dev.raju.domain.enitities.User

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPost(post: Post): Long

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<Post>

    @Update
    fun updatePost(post: Post)

    @Delete
    fun deletePost(post: Post)
}