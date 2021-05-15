package com.bonoogi.picsum.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
/**
 * Sample JSON
 * {
    "id": "1057",
    "author": "Stefan Kunze",
    "width": 6016,
    "height": 4016,
    "url": "https://unsplash.com/photos/_SmZSuZwkHg",
    "download_url": "https://picsum.photos/id/1057/6016/4016"
    }
 */
@Serializable
data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("download_url") val downloadUrl: String
)
