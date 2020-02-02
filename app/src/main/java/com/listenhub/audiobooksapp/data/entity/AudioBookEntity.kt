package com.listenhub.audiobooksapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Developed by tcbaras on 2019-12-16.
 */
@Entity
data class AudioBookEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val author: String,
    @ColumnInfo val imageUrl: String,
    @ColumnInfo val url: String,
    @ColumnInfo val description: String,
    @ColumnInfo val category: String,
    @ColumnInfo var isSample: Boolean = false,
    @ColumnInfo var length: String,
    @ColumnInfo var language: String,
    @ColumnInfo var chapters: String
)