package dev.raju.consumrz.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.raju.consumrz.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun findUserByEmail(email: String): List<User>?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Update
    fun updateUserDetails(user: User)

    @Delete
    fun deleteUser(user: User)
}