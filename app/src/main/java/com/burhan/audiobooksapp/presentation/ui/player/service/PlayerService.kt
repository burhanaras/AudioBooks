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

            if (this.audioBook != null) {
                if (this.audioBook?.id != audioBook.id) {
                    this.audioBook = audioBook
                    play()
                }
            } else {
                this.audioBook = audioBook
                play()
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
                        Log.d("BURHAN", "Finish()")
                        sendNowPlayingFinishBroadcast()
                    }

                    override fun onTick(millisUntilFinish: Long) {
                        val seconds =
                            (player.duration.toLong() - millisUntilFinish).toDouble().roundToInt() / 1000
                        Log.d("BURHAN", "$seconds")

                        sendNowPlayingTimeInfoBroadcast(
                            seconds,
                            (player.duration / 1E3).toInt(), player.isPlaying
                        )

                    }

                }.start()

            }
            player.setOnCompletionListener {
                countDownTimer = null
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
        private const val ARG_AUDIO_BOOK = "ARG_AUDIO_BOOK"
        fun newIntent(callerContext: Context, audioBook: AudioBook): Intent =
            Intent(callerContext, PlayerService::class.java).putExtra(ARG_AUDIO_BOOK, audioBook)
    }
}
