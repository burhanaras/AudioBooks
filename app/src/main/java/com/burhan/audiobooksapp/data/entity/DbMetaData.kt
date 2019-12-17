package com.burhan.audiobooksapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Developed by tcbaras on 2019-12-17.
 */
@Entity
data class DbMetaData(
    @PrimaryKey var id: String = "1",
    @ColumnInfo var dbVersion: String
)