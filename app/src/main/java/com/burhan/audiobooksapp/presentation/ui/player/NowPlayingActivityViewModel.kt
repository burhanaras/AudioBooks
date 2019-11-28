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

    private var audioBook: AudioBook? = null
    private var isPlaying: Boolean = false

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

        val audioBook0 = AudioBook(
            id = "0",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook1 = AudioBook(
            id = "1",
            name = "Educated A Memoir",
            imageUrl = "https://m.media-amazon.com/images/I/41dIDDpGepL._SL500_.jpg",
            description = "Born to survivalists in the mountains of Idaho, Tara Westover was 17 the first time she set foot in a classroom. Her family was so isolated from mainstream society that there was no one to ensure the children received an education and no one to intervene when one of Tara’s older brothers became violent. When another brother got himself into college, Tara decided to try a new kind of life. Her quest for knowledge transformed her, taking her over oceans and across continents, to Harvard and to Cambridge University. Only then would she wonder if she’d traveled too far, if there was still a way home.",
            author = "Tara Westover",
            url = "https://samples.audible.com/bk/rand/005325/bk_rand_005325_sample.mp3"
        )
        val playList = PlayList(arrayOf(audioBook0, audioBook1))
        ContextCompat.startForegroundService(app, PlayerService.newIntentForPlay(app, playList))
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
}