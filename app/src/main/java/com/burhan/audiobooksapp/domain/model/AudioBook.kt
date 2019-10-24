package com.burhan.audiobooksapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Developed by tcbaras on 2019-10-24.
 */
@Parcelize
data class AudioBook(
    val id: String,
    var name: String,
    var imageUrl: String,
    var description: String,
    var author: String
) : Parcelable
