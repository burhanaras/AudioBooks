package com.burhan.audiobooksapp.presentation.ui.player.service

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.burhan.audiobooksapp.domain.model.AudioBook

class PlayerService : Service() {

    private var audioBook: AudioBook? = null
    private lateinit var player: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind()")
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand()")
        intent?.getParcelableExtra<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->

            if (this.audioBook != null) {
                if (this.audioBook?.id != audioBook.id) {
                    play(audioBook.url)
                }
            } else {
                play(audioBook.url)
            }

            this.audioBook = audioBook
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun play(url: String) {
        if (::player.isInitialized){
            player.stop()
            player.release()
        }
        player = MediaPlayer()
        player.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        player.setDataSource(url)
        player.setOnPreparedListener {
            it.start()
        }
        player.prepareAsync()
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
        fun newIntent(callerActivity: Activity, audioBook: AudioBook): Intent =
            Intent(callerActivity, PlayerService::class.java).putExtra(ARG_AUDIO_BOOK, audioBook)


    }
}
