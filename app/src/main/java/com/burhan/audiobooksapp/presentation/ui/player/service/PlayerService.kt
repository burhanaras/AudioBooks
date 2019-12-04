package com.burhan.audiobooksapp.presentation.ui.player.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.burhan.audiobooksapp.presentation.ui.player.model.NowPlayingInfo
import com.burhan.audiobooksapp.presentation.ui.player.model.PlayStatus
import com.burhan.audiobooksapp.presentation.ui.player.notification.NotificationBuilder
import kotlin.math.roundToInt

class PlayerService : LifecycleService() {

    private var playerPlayList = PlayerPlayList(arrayListOf())
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

        intent.getStringExtra(ARG_COMMAND)?.let { command ->
            when (command) {
                CMD_PLAY -> {
                    intent.getParcelableExtra<PlayList>(ARG_AUDIO_BOOK)?.let { playList ->

                        if (playList.audioBooks.isEmpty()) {
                            return Service.START_STICKY
                        }

                        val audioBook = playList.audioBooks.first()

                        when {
                            this.playerPlayList.isEmpty() -> {
                                this.playerPlayList = PlayerPlayList.newInstance(playList)
                                play()
                            }
                            this.playerPlayList.getCurrent()?.id == audioBook.id -> {
                                if (player.isPlaying) {
                                    // Same audio book is already playing. Do nothing.
                                } else {
                                    //This audio has been played and finished and user wants to play again
                                    this.playerPlayList = PlayerPlayList.newInstance(playList)
                                    play()
                                }
                            }
                            else -> {
                                this.playerPlayList = PlayerPlayList.newInstance(playList)
                                play()
                            }
                        }
                    }

                }
                CMD_TOGGLE_PLAY_PAUSE -> {
                    when {
                        player.isPlaying -> player.pause()
                        player.currentPosition < player.duration -> player.start()
                        else -> play()
                    }
                }
                CMD_TIME_SHIFT_TO_PERCENT -> {
                    intent.getIntExtra(ARG_TIME_SHIFT_PERCENTAGE, -1).let { percent ->
                        if (percent > 0) { // percent is between 0 and 100
                            player.seekTo((player.duration.toDouble() * percent / 100).toInt())
                        }
                    }
                }
                CMD_TIME_SHIFT_WITH_AMOUNT -> {
                    intent.getIntExtra(ARG_TIME_SHIFT_SECONDS, 0).let { seconds ->
                        if (seconds != 0) { // amount is seconds. like: 30, -10
                            //TODO: Although we put a negative amount here, it always returns positive amount. So we put minus instead of plus. That is a problem to be solved, otherwise we can't go further with amount
                            player.seekTo((player.currentPosition + (seconds * 1E3)).toInt())
                        }
                    }
                }
                CMD_PLAY_ITEM_OF_PLAYLIST -> {
                    intent.getIntExtra(ARG_PLAYLIST_ITEM_INDEX, -1).let { position ->
                        if (position > 0 && playerPlayList.has(position)) {
                            playerPlayList.goToPosition(position)
                            play()
                        }
                    }
                }
                CMD_BROADCAST_INFO -> {
                    sendNowPlayingTimeInfoBroadcast()
                }
                else -> {
                    Log.e(TAG, "Command not recognised!")
                }
            }
        }

        updateNotification()

        return START_STICKY
    }

    private fun updateNotification() {
        this.playerPlayList.getCurrent()?.let { audioBook ->
            NotificationBuilder(this).buildMediaNotification(
                audioBook,
                player.isPlaying
            ) { notification ->
                startForeground(99, notification)
            }
        }
    }

    private fun play() {

        this.playerPlayList.getCurrent()?.let { audioBook ->

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
                updateNotification()

                countDownTimer?.cancel()
                countDownTimer = object : CountDownTimer(player.duration.toLong(), 1000) {
                    override fun onFinish() {
                        sendNowPlayingFinishBroadcast()
                    }

                    override fun onTick(millisUntilFinish: Long) {
                        sendNowPlayingTimeInfoBroadcast()
                        player.currentPosition

                    }

                }.start()

            }

            player.setOnCompletionListener {
                countDownTimer?.cancel()
                countDownTimer = null

                sendNowPlayingFinishBroadcast()

                if (playerPlayList.hasNext()) {
                    playerPlayList.goToNext()
                    play()
                } else {
                    updateNotification()
                }
            }
            player.prepareAsync()

        }

    }

    private fun sendNowPlayingStartBroadcast() {
        this.playerPlayList.getCurrent()?.let { audioBook ->
            val intent = Intent(broadCastActionName)
            intent.putExtra(
                NowPlayingStartInfo,
                NowPlayingInfo(
                    audioBook,
                    0,
                    (player.duration / 1E3).toInt(),
                    PlayStatus.PLAYING,
                    playerPlayList.export()
                )
            )
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    private fun sendNowPlayingTimeInfoBroadcast() {

        val progress = (player.currentPosition).toDouble().roundToInt() / 1000
        val duration = (player.duration / 1E3).toInt()
        val playing = player.isPlaying

        this.playerPlayList.getCurrent()?.let { audioBook ->
            val intent = Intent(broadCastActionName)
            intent.putExtra(
                NowPlayingInfo, NowPlayingInfo(
                    audioBook,
                    progress,
                    duration,
                    if (playing) PlayStatus.PLAYING else PlayStatus.IDLE,
                    playerPlayList.export()
                )
            )
            LocalBroadcastManager.getInstance(this@PlayerService).sendBroadcast(intent)
        }


    }

    private fun sendNowPlayingFinishBroadcast() {
        this.playerPlayList.getCurrent()?.let { audioBook ->
            val intent = Intent(broadCastActionName)
            intent.putExtra(
                NowPlayingFinishInfo,
                NowPlayingInfo(
                    audioBook,
                    (player.duration / 1E3).toInt(),
                    (player.duration / 1E3).toInt(),
                    PlayStatus.IDLE,
                    playerPlayList.export()
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
        const val CMD_PLAY_ITEM_OF_PLAYLIST = "CMD_PLAY_ITEM_OF_PLAYLIST"
        const val CMD_BROADCAST_INFO = "CMD_BROADCAST_INFO"

        private const val ARG_AUDIO_BOOK = "ARG_AUDIO_BOOK"
        private const val ARG_COMMAND = "ARG_COMMAND"

        private const val ARG_PLAYLIST_ITEM_INDEX = "ARG_PLAYLIST_ITEM_INDEX"
        private const val ARG_TIME_SHIFT_PERCENTAGE = "ARG_TIME_SHIFT_PERCENTAGE"
        private const val ARG_TIME_SHIFT_SECONDS = "ARG_TIME_SHIFT_SECONDS"

        const val NowPlayingStartInfo = "NowPlayingStartInfo"
        const val NowPlayingInfo = "NowPlayingInfo"
        const val NowPlayingFinishInfo = "NowPlayingFinishInfo"

        private const val broadCastActionName = "com.burhan.audiobooks.player.info.receive"
        val IntentFilter: IntentFilter = IntentFilter(broadCastActionName)

        fun newIntentForPlay(callerContext: Context, audioBooks: PlayList): Intent =
            Intent(callerContext, PlayerService::class.java)
                .putExtra(ARG_COMMAND, CMD_PLAY)
                .putExtra(ARG_AUDIO_BOOK, audioBooks)

        fun newIntentForTogglePlayPause(
            callerContext: Context
        ): Intent =
            Intent(callerContext, PlayerService::class.java)
                .putExtra(
                    ARG_COMMAND,
                    CMD_TOGGLE_PLAY_PAUSE
                )


        fun newIntentForTimeShiftToPercentage(
            callerContext: Context,
            progress: Int
        ): Intent =
            Intent(
                callerContext,
                PlayerService::class.java
            ).putExtra(ARG_COMMAND, CMD_TIME_SHIFT_TO_PERCENT)
                .putExtra(ARG_TIME_SHIFT_PERCENTAGE, progress)

        fun newIntentForTimeShiftWithAmount(
            callerContext: Context,
            seconds: Int
        ): Intent =
            Intent(callerContext, PlayerService::class.java)
                .putExtra(
                    ARG_COMMAND,
                    CMD_TIME_SHIFT_WITH_AMOUNT
                )
                .putExtra(
                    ARG_TIME_SHIFT_SECONDS,
                    seconds
                )

        fun newIntentForPlayItemOfPlayList(
            callerContext: Context,
            selectedAudioBookPosition: Int
        ): Intent = Intent(callerContext, PlayerService::class.java).putExtra(
            ARG_COMMAND,
            CMD_PLAY_ITEM_OF_PLAYLIST
        ).putExtra(ARG_PLAYLIST_ITEM_INDEX, selectedAudioBookPosition)

        fun newIntentForPlayerInfo(callerContext: Context): Intent =
            Intent(callerContext, PlayerService::class.java)
                .putExtra(ARG_COMMAND, CMD_BROADCAST_INFO)
    }
}
