package dev.raju.consumrz.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Rajashekhar Vanahalli on 30 May, 2023
 */
@Parcelize
@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var userId: Int = 0,
    var username: String = "",
    var postId: Int,
    var text: String
): Parcelable {
    @Ignore
    var enableModify = false
}