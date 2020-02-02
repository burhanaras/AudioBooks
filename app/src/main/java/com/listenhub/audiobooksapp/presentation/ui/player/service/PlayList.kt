package com.listenhub.audiobooksapp.presentation.ui.player.service

import android.os.Parcelable
import com.listenhub.audiobooksapp.domain.model.AudioBook
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Developed by tcbaras on 2019-11-28.
 */

@Parcelize
data class PlayList(val audioBooks: Array<AudioBook> = arrayOf()) : Parcelable {

    @IgnoredOnParcel
    internal var currentInd: Int = 0

    fun get(position: Int): AudioBook? {
        return if (position < audioBooks.size)
            audioBooks[position]
        else null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayList

        if (audioBooks.size != other.audioBooks.size) return false
        audioBooks.forEachIndexed { index, audioBook ->
            if (audioBook.id != other.audioBooks[index].id) {
                return false
            }
        }
        if (!audioBooks.contentEquals(other.audioBooks)) return false

        return true
    }

    override fun hashCode(): Int {
        return audioBooks.contentHashCode()
    }
}