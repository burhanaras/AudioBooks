package com.burhan.audiobooksapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Developed by tcbaras on 2019-10-24.
 */
@Parcelize
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String = "",
    var audioBooks: List<AudioBook> = listOf(),
    var order: String = "1000"
) : Parcelable