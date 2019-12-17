package com.burhan.audiobooksapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Developed by tcbaras on 2019-12-16.
 */
@Entity
data class CategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String
)