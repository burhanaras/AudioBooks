package com.burhan.audiobooksapp.presentation.ui.player.audiofocus

import android.media.AudioManager
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class AudioFocusObserver(private val audioManager: AudioManager) : LifecycleObserver {

    private var audioFocus: AudioFocus? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        audioFocus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            AudioFocusImplApi26(audioManager)
        } else {
            AudioFocusImpl(audioManager)
        }
        audioFocus?.request()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        audioFocus?.release()
    }
}
