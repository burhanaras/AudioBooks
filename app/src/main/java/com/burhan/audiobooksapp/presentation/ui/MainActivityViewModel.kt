package com.burhan.audiobooksapp.presentation.ui

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayerService

/**
 * Developed by tcbaras on 2019-10-24.
 */
class MainActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    var lastActiveFragmentTag: String? = null

    var fabMiniPlayerVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fabMiniPlayerVisibility.postValue(false)
        val intentFilter = PlayerService.IntentFilter
        LocalBroadcastManager.getInstance(app).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (fabMiniPlayerVisibility.value == false)
                    fabMiniPlayerVisibility.postValue(true)
            }
        }, intentFilter)
    }
}