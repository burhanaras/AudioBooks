package com.burhan.audiobooksapp.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Developed by tcbaras on 2019-10-24.
 */
class MainActivityViewModel : ViewModel() {
    var lastActiveFragmentTag: String? = null

    var fabMiniPlayerVisibility : MutableLiveData<Boolean> = MutableLiveData()

    init {
        fabMiniPlayerVisibility.postValue(false)
    }
}