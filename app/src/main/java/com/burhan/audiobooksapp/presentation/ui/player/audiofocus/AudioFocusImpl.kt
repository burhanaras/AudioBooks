package com.burhan.audiobooksapp.presentation.ui.player.audiofocus

import android.media.AudioManager

/**
 * Developed by tcbaras on 2019-10-25.
 */
class AudioFocusImpl(private val audioManager: AudioManager) : AudioFocus {
    override fun request() {
        audioManager.requestAudioFocus({}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    override fun release() {
        audioManager.abandonAudioFocus { }
    }
}