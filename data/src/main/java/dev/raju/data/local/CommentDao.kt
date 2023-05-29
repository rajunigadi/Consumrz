package dev.raju.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.raju.domain.enitities.Comment

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addComment(comment: Comment): Long

    @Query("SELECT * FROM comments WHERE postId = :postId")
    fun getAllComments(postId: Int): List<Comment>

    @Update
    fun updateComment(comment: Comment)

    @Delete
    fun deleteComment(comment: Comment)
}