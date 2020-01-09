package com.burhan.audiobooksapp.data.dao

import androidx.annotation.WorkerThread
import androidx.room.*
import com.burhan.audiobooksapp.data.entity.AudioBookEntity
import com.burhan.audiobooksapp.data.entity.CategoryEntity
import com.burhan.audiobooksapp.data.entity.DbMetaData

/**
 * Developed by tcbaras on 2019-12-16.
 */
@Dao
interface AudioBookDAO {

    @Query("SELECT * FROM DbMetaData")
    @WorkerThread
    fun getDbMetaData(): DbMetaData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertDbMetaData(dbMetaData: DbMetaData)

    @Query("SELECT * FROM AudioBookEntity WHERE name LIKE :query OR author LIKE :query")
    @WorkerThread
    fun search(query: String): List<AudioBookEntity>

    @Query("SELECT * FROM AudioBookEntity WHERE id == :id")
    @WorkerThread
    fun getAudioBook(id: String): AudioBookEntity?

    @Query("SELECT * FROM AudioBookEntity WHERE category == :category LIMIT :limit")
    @WorkerThread
    fun getByCategory(category: String, limit: Int): List<AudioBookEntity>

    @Query("SELECT * FROM AudioBookEntity WHERE category == :category")
    @WorkerThread
    fun getAllByCategory(category: String): List<AudioBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertAll(audioBooks: List<AudioBookEntity>)

    @Delete
    @WorkerThread
    fun delete(audioBook: AudioBookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertAllCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM CategoryEntity")
    @WorkerThread
    fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT count(*) FROM AudioBookEntity")
    @WorkerThread
    fun getAudioBookTotalcount(): Int
}