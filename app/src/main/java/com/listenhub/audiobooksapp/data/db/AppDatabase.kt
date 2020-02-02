package com.listenhub.audiobooksapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.listenhub.audiobooksapp.data.dao.AudioBookDAO
import com.listenhub.audiobooksapp.data.entity.AudioBookEntity
import com.listenhub.audiobooksapp.data.entity.CategoryEntity
import com.listenhub.audiobooksapp.data.entity.DbMetaData

/**
 * Developed by tcbaras on 2019-12-16.
 */
@Database(
    entities = [AudioBookEntity::class, CategoryEntity::class, DbMetaData::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun audioBookDao(): AudioBookDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        @JvmStatic
        fun getInstance(appContext: Context): AppDatabase {
            synchronized(lock) {
                INSTANCE = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "com.burhan.audiobooks-db"
                ).fallbackToDestructiveMigration().build()
            }
            return this.INSTANCE!!
        }
    }
}