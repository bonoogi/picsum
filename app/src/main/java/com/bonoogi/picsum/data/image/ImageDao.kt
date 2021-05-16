package com.bonoogi.picsum.data.image

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@Dao
interface ImageDao {
    @Query("SELECT * FROM image WHERE id = :id")
    fun get(id: String): Maybe<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<Image>): Completable
}