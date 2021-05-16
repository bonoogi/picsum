package com.bonoogi.picsum.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bonoogi.picsum.data.image.Image
import com.bonoogi.picsum.data.image.ImageDao

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@Database(entities = [Image::class], version = 1)
abstract class PicsumDatabase: RoomDatabase() {
    abstract fun imageDao(): ImageDao
}