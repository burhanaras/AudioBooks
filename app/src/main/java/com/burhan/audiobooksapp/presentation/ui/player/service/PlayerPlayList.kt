package com.burhan.audiobooksapp.presentation.ui.player.service

import com.burhan.audiobooksapp.domain.model.AudioBook

/**
 * Developed by tcbaras on 2019-11-28.
 */
data class PlayerPlayList(private var audioBooks: MutableList<AudioBook> = mutableListOf()) {
    private var currentIndex = 0

    fun hasNext(): Boolean {
        return audioBooks.size > currentIndex + 1
    }

    fun goToNext(): AudioBook? {
        if (hasNext()) {
            currentIndex++
            return audioBooks[currentIndex]
        }
        return null
    }

    fun getCurrent(): AudioBook? {
        if (audioBooks.size <= currentIndex) return null
        return audioBooks[currentIndex]
    }

    fun isEmpty(): Boolean {
        return audioBooks.size == 0
    }

    fun export() =
        PlayList(audioBooks = audioBooks.toTypedArray()).apply { currentInd = currentIndex }

    companion object {
        fun newInstance(playList: PlayList): PlayerPlayList {
            return PlayerPlayList().apply {
                audioBooks.addAll(playList.audioBooks)
            }
        }
    }
}