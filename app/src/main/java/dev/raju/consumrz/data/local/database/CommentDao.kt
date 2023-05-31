package dev.raju.consumrz.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.raju.consumrz.domain.model.Comment

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