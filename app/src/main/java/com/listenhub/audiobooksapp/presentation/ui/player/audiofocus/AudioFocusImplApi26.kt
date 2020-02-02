package com.listenhub.audiobooksapp.presentation.ui.player.audiofocus

import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Developed by tcbaras on 2019-10-25.
 */
@RequiresApi(Build.VERSION_CODES.O)
class AudioFocusImplApi26(private val audioManager: AudioManager) : AudioFocus {

    private var focusRequest: AudioFocusRequest? = null

    override fun request() {

        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {

            setAudioAttributes(AudioAttributes.Builder().run {
                setUsage(AudioAttributes.USAGE_MEDIA)
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                build()
            })
            setAcceptsDelayedFocusGain(true)
            setOnAudioFocusChangeListener {}
            build()
        }
        audioManager.requestAudioFocus(focusRequest)
    }


    override fun release() {
        focusRequest?.apply { audioManager.abandonAudioFocusRequest(this) }
    }
}