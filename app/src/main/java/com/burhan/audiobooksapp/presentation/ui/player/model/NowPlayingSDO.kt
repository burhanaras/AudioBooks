package com.burhan.audiobooksapp.presentation.ui.player.model

import android.os.Parcelable
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayList
import kotlinx.android.parcel.Parcelize

/**
 * Developed by tcbaras on 2019-11-13.
 */
@Parcelize
data class NowPlayingSDO(
    val title: String,
    val author: String,
    val imageUrl: String
) : Parcelable

@Parcelize
data class NowPlayingTimeInfoSDO(
    val progress: String,
    val duration: String,
    val seekBarProgress: Int,
    val seekBarMaxValue: Int,
    val playPauseButtonIcon: Int
) : Parcelable

/** Player Broadcast */

enum class PlayStatus {
    PLAYING, IDLE
}

@Parcelize
data class NowPlayingInfo(
    val audioBook: AudioBook,
    val progressSc: Int,
    val durationSc: Int,
    val playStatus: PlayStatus,
    val playList: PlayList
) : Parcelable