package com.listenhub.audiobooksapp.presentation.ui.nowplaying.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Developed by tcbaras on 2019-12-04.
 */
class NowPlayingbottomSheetSharedViewModel : ViewModel() {
    internal var expandImage: MutableLiveData<Boolean> = MutableLiveData()
    internal var collapseImage: MutableLiveData<Boolean> = MutableLiveData()

    fun bottomSheetStateChanged(newState: Int) {
//        when (newState) {
//            BottomSheetBehavior.STATE_COLLAPSED -> collapseImage.postValue(true)
//            BottomSheetBehavior.STATE_EXPANDED -> expandImage.postValue(true)
//        }
    }

}