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
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.core.extension.toPrettyTimeInfoAsMMSS
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingInfo
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingSDO
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingTimeInfoSDO
import com.burhan.audiobooksapp.presentation.ui.player.model.PlayStatus
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayerService

/**
 * Developed by tcbaras on 2019-11-13.
 */
class NowPlayingActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    internal var nowPlayingSDO = MutableLiveData<NowPlayingSDO>()
    internal var nowPlayingTimeInfoSDO = MutableLiveData<NowPlayingTimeInfoSDO>()

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getParcelableExtra<NowPlayingInfo>("NowPlayingStartInfo")
                ?.let { nowPlayingInfo ->
                    //                    nowPlayingSDO.postValue(nowPlayingData)
                }
            intent.getParcelableExtra<NowPlayingInfo>("NowPlayingInfo")
                ?.let { nowPlayingInfo ->
                    val progress = nowPlayingInfo.progressSc.toPrettyTimeInfoAsMMSS()
                    val duration = nowPlayingInfo.durationSc.toPrettyTimeInfoAsMMSS()
                    val seekBarMaxValue = 100
                    val seekBarProgress = if (nowPlayingInfo.durationSc > 0)
                        ((nowPlayingInfo.progressSc.toDouble() / nowPlayingInfo.durationSc.toDouble()) * seekBarMaxValue).toInt()
                    else 0
                    val playIconRes =
                        if (nowPlayingInfo.playStatus == PlayStatus.PLAYING) R.drawable.ic_pause else R.drawable.ic_play
                    val sdo =
                        NowPlayingTimeInfoSDO(
                            progress,
                            duration,
                            seekBarProgress,
                            seekBarMaxValue,
                            playIconRes
                        )
                    nowPlayingTimeInfoSDO.postValue(sdo)
                }
            intent.getParcelableExtra<NowPlayingInfo>("NowPlayingFinishInfo")
                ?.let { nowPlayingInfo ->
                    val sdo = NowPlayingTimeInfoSDO("", "", 10, 100, R.drawable.ic_play)
//                    nowPlayingTimeInfoSDO.postValue(nowPlayingTimeInfo)
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