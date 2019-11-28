package com.burhan.audiobooksapp.presentation.ui.player.service

import android.os.Parcelable
import com.burhan.audiobooksapp.domain.model.AudioBook
import kotlinx.android.parcel.Parcelize

/**
 * Developed by tcbaras on 2019-11-28.
 */

@Parcelize
data class PlayList(val audioBooks: Array<AudioBook> = arrayOf()) : Parcelable {

    internal var currentInd: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayList

        if (!audioBooks.contentEquals(other.audioBooks)) return false

        return true
    }

    override fun hashCode(): Int {
        return audioBooks.contentHashCode()
    }
}