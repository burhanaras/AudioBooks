package com.listenhub.audiobooksapp.presentation.ui

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.ui.player.model.NowPlayingInfo
import com.listenhub.audiobooksapp.presentation.ui.player.model.PlayStatus
import com.listenhub.audiobooksapp.presentation.ui.player.service.PlayerService

/**
 * Developed by tcbaras on 2019-10-24.
 */
class MainActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    var lastActiveFragmentTag: String? = null
    var currentAudioBook: AudioBook? = null

    var fabMiniEqualizerVisibility: MutableLiveData<Pair<Boolean, Boolean>> =
        MutableLiveData() // (Visibility, isAnimating)
    var goToPlayingNowActivity: MutableLiveData<AudioBook> = MutableLiveData()

    init {
        fabMiniEqualizerVisibility.postValue(Pair(first = false, second = false))
        val intentFilter = PlayerService.IntentFilter
        LocalBroadcastManager.getInstance(app).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getParcelableExtra<NowPlayingInfo>(PlayerService.NowPlayingInfo)
                    ?.let { nowPlayingInfo ->

                        currentAudioBook = nowPlayingInfo.audioBook
                        val newAnimatingStatus = nowPlayingInfo.playStatus == PlayStatus.PLAYING
                        val currentAnimatingStatus = fabMiniEqualizerVisibility.value?.second

                        if (newAnimatingStatus != currentAnimatingStatus)
                            fabMiniEqualizerVisibility.postValue(Pair(true, newAnimatingStatus))
                    }
            }
        }, intentFilter)
    }

    fun onClickMiniEqualizedFab() {
        goToPlayingNowActivity.postValue(currentAudioBook)
    }
}