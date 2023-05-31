package dev.raju.consumrz.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

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
    @IgnoredOnParcel
    var enableModify = false
}