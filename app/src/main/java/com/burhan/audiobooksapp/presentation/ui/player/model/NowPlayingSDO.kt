package com.burhan.audiobooksapp.presentation.ui.player.model

import android.os.Parcelable
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