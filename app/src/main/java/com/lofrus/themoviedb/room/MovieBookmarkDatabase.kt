package com.lofrus.themoviedb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieBookmarkEntity::class], version = 1, exportSchema = false)
abstract class MovieBookmarkDatabase : RoomDatabase() {
    abstract fun movieBookmarkDao(): MovieBookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: MovieBookmarkDatabase? = null

        @JvmStatic
        fun getAppDatabase(context: Context): MovieBookmarkDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieBookmarkDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MovieBookmarkDatabase::class.java, "room-movie.db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}