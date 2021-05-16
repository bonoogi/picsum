package com.bonoogi.picsum.data.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.net.URLEncoder

/**
 * @author 구본욱(bnoo1333@gmail.com)
 * Sample JSON
    {
        "id": "1057",
        "author": "Stefan Kunze",
        "width": 6016,
        "height": 4016,
        "url": "https://unsplash.com/photos/_SmZSuZwkHg",
        "download_url": "https://picsum.photos/id/1057/6016/4016"
    }
 */
@Entity
@Serializable
data class Image(
    @PrimaryKey val id: String,
    @ColumnInfo(name="author") val author: String,
    @ColumnInfo(name="width") val width: Int,
    @ColumnInfo(name="height") val height: Int,
    @ColumnInfo(name="url") val url: String,
    @ColumnInfo(name="download_url") @SerialName("download_url") val downloadUrl: String
) {

    val thumbnailUrl: String get() = imageUrlWith(300)

    val constraintRatio: String get() {
        //val value = width.toFloat() / height.toFloat()
        return "$width:$height"
    }

    fun imageUrlWith(width: Int, height: Int? = null): String {
        val _height = height ?: width
        return "https://picsum.photos/id/$id/$width/$_height"
    }
}
