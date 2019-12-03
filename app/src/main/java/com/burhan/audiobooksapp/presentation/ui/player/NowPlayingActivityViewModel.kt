package com.burhan.audiobooksapp.presentation.ui.player

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.usecase.GetPlayListUseCase
import com.burhan.audiobooksapp.presentation.core.extension.toPrettyTimeInfoAsMMSS
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingInfo
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingSDO
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingTimeInfoSDO
import com.burhan.audiobooksapp.presentation.ui.player.model.PlayStatus
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayList
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayerService

/**
 * Developed by tcbaras on 2019-11-13.
 */
class NowPlayingActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    internal var nowPlayingSDO = MutableLiveData<NowPlayingSDO>()
    internal var nowPlayingTimeInfoSDO = MutableLiveData<NowPlayingTimeInfoSDO>()
    internal var nowPlayingPlayListSDO = MutableLiveData<PlayList>()

    private var audioBook: AudioBook? = null
    private var playList: PlayList? = null
    private var isPlaying: Boolean = false

    private val getPlayListUseCase = GetPlayListUseCase()

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getParcelableExtra<NowPlayingInfo>(PlayerService.NowPlayingStartInfo)
                ?.let { nowPlayingInfo ->
                    val sdo = NowPlayingSDO(
                        nowPlayingInfo.audioBook.name,
                        nowPlayingInfo.audioBook.author,
                        nowPlayingInfo.audioBook.imageUrl
                    )
                    nowPlayingSDO.postValue(sdo)
                    nowPlayingPlayListSDO.postValue(nowPlayingInfo.playList)
                    isPlaying = true
                }
            intent.getParcelableExtra<NowPlayingInfo>(PlayerService.NowPlayingInfo)
                ?.let { nowPlayingInfo ->

                    if (audioBook == null || audioBook?.id != nowPlayingInfo.audioBook.id) {
                        // Activity is opened while audiobook is already playing || A new audio book
                        bindScreen(nowPlayingInfo.audioBook)
                    }

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
                    if (playList == null || !playList?.equals(nowPlayingInfo.playList)!!) {
                        nowPlayingPlayListSDO.postValue(nowPlayingInfo.playList)
                        playList = nowPlayingInfo.playList
                    }
                    isPlaying = nowPlayingInfo.playStatus == PlayStatus.PLAYING
                }
            intent.getParcelableExtra<NowPlayingInfo>(PlayerService.NowPlayingFinishInfo)
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
                    nowPlayingPlayListSDO.postValue(nowPlayingInfo.playList)
                    isPlaying = nowPlayingInfo.playStatus == PlayStatus.PLAYING
                }
        }
    }

    private fun bindScreen(audioBook: AudioBook) {
        this.audioBook = audioBook
        nowPlayingSDO.postValue(NowPlayingSDO(audioBook.name, audioBook.author, audioBook.imageUrl))
    }

    init {
        val intentFilter = PlayerService.IntentFilter
        LocalBroadcastManager.getInstance(app).registerReceiver(broadcastReceiver, intentFilter)
    }

    fun play(audioBook: AudioBook) {
        bindScreen(audioBook)
        getPlayListUseCase.getPlayListFor(audioBook) { playList ->
            ContextCompat.startForegroundService(app, PlayerService.newIntentForPlay(app, playList))
        }
    }

    fun togglePlayPause() {
        isPlaying = !isPlaying
        this.audioBook?.let {
            ContextCompat.startForegroundService(
                app,
                PlayerService.newIntentForTogglePlayPause(app)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(app).unregisterReceiver(broadcastReceiver)
    }

    fun seekBarProgressChanged(progress: Int, fromUser: Boolean) {
        this.audioBook?.let {
            ContextCompat.startForegroundService(
                app,
                PlayerService.newIntentForTimeShiftToPercentage(app, progress)
            )
        }
    }

    fun audioBookSelectedInPlayList(selectedAudioBookPosition: Int) {
        ContextCompat.startForegroundService(
            app,
            PlayerService.newIntentForPlayItemOfPlayList(app, selectedAudioBookPosition)
        )
    }
}