package com.listenhub.audiobooksapp.domain.usecase

import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.ui.player.service.PlayList

/**
 * Developed by tcbaras on 2019-12-01.
 */
class GetPlayListUseCase {

    fun getPlayListFor(audioBook: AudioBook, callBack: (playList: PlayList) -> Unit) {
        mutableListOf<AudioBook>().apply {
            add(audioBook)
            callBack(PlayList(this.toTypedArray()))
        }
    }
}