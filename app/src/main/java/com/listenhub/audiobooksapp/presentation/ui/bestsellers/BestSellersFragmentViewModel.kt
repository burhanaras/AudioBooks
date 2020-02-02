package com.listenhub.audiobooksapp.presentation.ui.bestsellers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.usecase.GetBestSellersUseCase

class BestSellersFragmentViewModel(val app: Application) : AndroidViewModel(app) {
    internal val data: MutableLiveData<List<AudioBook>> = MutableLiveData()

    private val getBestSellersUseCase = GetBestSellersUseCase(app)

    fun loadData() {
        getBestSellersUseCase.loadData { bestSellers ->
            data.postValue(bestSellers)
        }
    }
}
