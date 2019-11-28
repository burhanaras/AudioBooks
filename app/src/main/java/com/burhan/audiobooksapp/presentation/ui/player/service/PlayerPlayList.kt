package com.burhan.audiobooksapp.presentation.ui.player.service

import com.burhan.audiobooksapp.domain.model.AudioBook

/**
 * Developed by tcbaras on 2019-11-28.
 */
class PlayerPlayList {

    private var audioBooks: MutableList<AudioBook> = mutableListOf()
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

    companion object {
        fun newInstance(playList: PlayList): PlayerPlayList {
            val playerPlayList = PlayerPlayList()
            playerPlayList.audioBooks.clear()
            playerPlayList.audioBooks.addAll(playList.audioBooks)

            return playerPlayList
        }
    }
}