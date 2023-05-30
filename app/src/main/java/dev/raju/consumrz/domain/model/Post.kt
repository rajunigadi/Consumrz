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
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val text: String,
): Parcelable {
    @Ignore
    var comments: List<Comment>? = null
}