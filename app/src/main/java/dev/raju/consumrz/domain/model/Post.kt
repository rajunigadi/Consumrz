package dev.raju.consumrz.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var userId: Int = 0,
    var username: String = "",
    var title: String,
    var text: String
): Parcelable {
    @Ignore
    @IgnoredOnParcel
    var comments: List<Comment>? = null

    @Ignore
    @IgnoredOnParcel
    var enableModify = false
}