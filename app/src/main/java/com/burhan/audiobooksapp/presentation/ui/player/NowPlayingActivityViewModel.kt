package com.burhan.audiobooksapp.presentation.ui.player

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingSDO
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingTimeInfoSDO
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayerService

/**
 * Developed by tcbaras on 2019-11-13.
 */
class NowPlayingActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    internal var nowPlayingSDO = MutableLiveData<NowPlayingSDO>()
    internal var nowPlayingTimeInfoSDO = MutableLiveData<NowPlayingTimeInfoSDO>()

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getParcelableExtra<NowPlayingSDO>("nowPlayingSDO")?.let { nowPlayingData ->
                nowPlayingSDO.postValue(nowPlayingData)
            }
            intent.getParcelableExtra<NowPlayingTimeInfoSDO>("nowPlayingTimeInfoSDO")?.let { nowPlayingTimeInfo ->
                nowPlayingTimeInfoSDO.postValue(nowPlayingTimeInfo)
            }
        }
    }

    init {
        val intentFilter = IntentFilter("com.burhan.audiobooks.player.info.receive")
        LocalBroadcastManager.getInstance(app).registerReceiver(broadcastReceiver, intentFilter)
    }

    fun play(audioBook: AudioBook) {
        ContextCompat.startForegroundService(app, PlayerService.newIntent(app, audioBook))
    }

    fun playPause() {

    }

    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(app).unregisterReceiver(broadcastReceiver)
    }
}