package com.burhan.audiobooksapp.presentation.ui.player.service

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingInfo
import com.burhan.audiobooksapp.presentation.ui.player.model.PlayStatus
import com.burhan.audiobooksapp.presentation.ui.player.notification.NotificationBuilder
import kotlin.math.roundToInt

class PlayerService : LifecycleService() {

    private var audioBook: AudioBook? = null
    private lateinit var player: MediaPlayer
    private var countDownTimer: CountDownTimer? = null


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        Log.d(TAG, "onBind()")
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand()")

        intent.getParcelableExtra<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->

            when {
                this.audioBook == null -> {
                    this.audioBook = audioBook
                    play()
                }
                this.audioBook?.id != audioBook.id -> {
                    this.audioBook = audioBook
                    play()
                }
                !player.isPlaying && this.audioBook?.id == audioBook.id -> {
                    this.audioBook = audioBook
                    play()
                }
                else -> {
                    intent.getIntExtra(ARG_TIME_SHIFT_PERCENTAGE, -1).let { percent ->
                        if (percent > 0) { // percent is between 0 and 100
                            player.seekTo(player.duration * percent / 100)
                        }
                    }
                }

            }

            val command = CMD_PLAY
            when (command) {
                CMD_PLAY -> {
                    when {
                        this.audioBook == null -> {
                            this.audioBook = audioBook
                            play()
                        }
                        this.audioBook?.id == audioBook.id -> {
                            if (player.isPlaying) {
                                // Same audio book is already playing. Do nothing.
                            } else {
                                //This audio has been played and finished and user wants to play again
                                this.audioBook = audioBook
                                play()
                            }
                        }
                        else -> {
                            this.audioBook = audioBook
                            play()
                        }
                    }
                }
                CMD_TOGGLE_PLAY_PAUSE -> {
                    if (player.isPlaying) {
                        player.pause()
                    } else {
                        player.start()
                    }
                }
                CMD_TIME_SHIFT_TO_PERCENT -> {
                    intent.getIntExtra(ARG_TIME_SHIFT_PERCENTAGE, -1).let { percent ->
                        if (percent > 0) { // percent is between 0 and 100
                            player.seekTo(player.duration * percent / 100)
                        }
                    }
                }
                CMD_TIME_SHIFT_WITH_AMOUNT -> {
                    intent.getIntExtra(ARG_TIME_SHIFT_SECONDS, 0).let { seconds ->
                        if (seconds != 0) { // amount is seconds. like: 30, -10
                            player.seekTo((player.duration + seconds * 1E3).toInt())
                        }
                    }
                }
            }

            NotificationBuilder(this).buildMediaNotification(audioBook) { notification ->
                startForeground(99, notification)
            }
        }

        return START_STICKY
    }

    private fun play() {

        this.audioBook?.let { audioBook ->

            if (::player.isInitialized) {
                player.stop()
                player.release()
            }

            player = MediaPlayer()
            player.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            player.setDataSource(audioBook.url)
            player.setOnPreparedListener {
                it.start()

                sendNowPlayingStartBroadcast()

                countDownTimer?.cancel()
                countDownTimer = object : CountDownTimer(player.duration.toLong(), 1000) {
                    override fun onFinish() {
                        sendNowPlayingFinishBroadcast()
                    }

                    override fun onTick(millisUntilFinish: Long) {
                        val seconds =
                            (player.currentPosition).toDouble().roundToInt() / 1000

                        sendNowPlayingTimeInfoBroadcast(
                            seconds,
                            (player.duration / 1E3).toInt(), player.isPlaying
                        )
                        player.currentPosition

                    }

                }.start()

            }

            player.setOnCompletionListener {
                countDownTimer?.cancel()
                countDownTimer = null
                sendNowPlayingFinishBroadcast()
            }
            player.prepareAsync()

        }

    }

    private fun sendNowPlayingStartBroadcast() {
        this.audioBook?.let { audioBook ->
            val intent = Intent("com.burhan.audiobooks.player.info.receive")
            intent.putExtra(
                "NowPlayingStartInfo",
                NowPlayingInfo(
                    audioBook,
                    0,
                    (player.duration / 1E3).toInt(),
                    PlayStatus.PLAYING
                )
            )
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    private fun sendNowPlayingTimeInfoBroadcast(
        progress: Int,
        duration: Int,
        playing: Boolean
    ) {
        this.audioBook?.let { audioBook ->
            val intent = Intent("com.burhan.audiobooks.player.info.receive")
            intent.putExtra(
                "NowPlayingInfo", NowPlayingInfo(
                    audioBook,
                    progress,
                    duration,
                    if (playing) PlayStatus.PLAYING else PlayStatus.IDLE
                )
            )
            LocalBroadcastManager.getInstance(this@PlayerService).sendBroadcast(intent)
        }


    }

    private fun sendNowPlayingFinishBroadcast() {
        this.audioBook?.let { audioBook ->
            val intent = Intent("com.burhan.audiobooks.player.info.receive")
            intent.putExtra(
                "NowPlayingFinishInfo",
                NowPlayingInfo(
                    audioBook,
                    (player.duration / 1E3).toInt(),
                    (player.duration / 1E3).toInt(),
                    PlayStatus.IDLE
                )
            )
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind()")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        player.stop()
        player.release()
        super.onDestroy()
    }

    companion object {
        val TAG: String = PlayerService::class.java.simpleName

        const val CMD_PLAY = "CMD_PLAY"
        const val CMD_TOGGLE_PLAY_PAUSE = "CMD_TOGGLE_PLAY_PAUSE"
        const val CMD_TIME_SHIFT_TO_PERCENT = "CMD_TIME_SHIFT_TO_PERCENT"
        const val CMD_TIME_SHIFT_WITH_AMOUNT = "CMD_TIME_SHIFT_WITH_AMOUNT"

        private const val ARG_AUDIO_BOOK = "ARG_AUDIO_BOOK"
        private const val ARG_COMMAND = "ARG_COMMAND"

        private const val ARG_TOGGLE_PLAY_PAUSE = "ARG_TOGGLE_PLAY_PAUSE"
        private const val ARG_TIME_SHIFT_PERCENTAGE = "ARG_TIME_SHIFT_PERCENTAGE"
        private const val ARG_TIME_SHIFT_SECONDS = "ARG_TIME_SHIFT_SECONDS"

        fun newIntentForPlay(callerContext: Context, audioBook: AudioBook): Intent =
            Intent(callerContext, PlayerService::class.java)
                .putExtra(ARG_COMMAND, CMD_PLAY)
                .putExtra(ARG_AUDIO_BOOK, audioBook)

        fun newIntentForTogglePlayPause(
            callerContext: Context,
            audioBook: AudioBook,
            isPlay: Boolean
        ): Intent =
            Intent(callerContext, PlayerService::class.java).putExtra(
                ARG_TOGGLE_PLAY_PAUSE,
                CMD_TOGGLE_PLAY_PAUSE
            ).putExtra(ARG_AUDIO_BOOK, audioBook)
                .putExtra(ARG_TOGGLE_PLAY_PAUSE, isPlay)

        fun newIntentForTimeShiftToPercentage(
            callerContext: Context,
            audioBook: AudioBook,
            progress: Int
        ): Intent =
            Intent(
                callerContext,
                PlayerService::class.java
            ).putExtra(ARG_COMMAND, CMD_TIME_SHIFT_TO_PERCENT)
                .putExtra(ARG_AUDIO_BOOK, audioBook)
                .putExtra(ARG_TIME_SHIFT_PERCENTAGE, progress)

        fun newIntentForTimeShiftWithAmount(
            callerContext: Context,
            audioBook: AudioBook,
            seconds: Int
        ): Intent =
            Intent(callerContext, PlayerService::class.java).putExtra(
                ARG_COMMAND,
                CMD_TIME_SHIFT_WITH_AMOUNT
            ).putExtra(
                ARG_TIME_SHIFT_SECONDS,
                seconds
            )
    }
}
