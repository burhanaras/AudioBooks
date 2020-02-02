package com.listenhub.audiobooksapp.presentation.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.model.Category
import com.listenhub.audiobooksapp.domain.usecase.GetBannerDataUseCase
import com.listenhub.audiobooksapp.domain.usecase.GetCategoriesOfHomeUseCase

class HomeFragmentViewModel(app: Application) : AndroidViewModel(app) {
    internal val bannerData: MutableLiveData<List<AudioBook>> = MutableLiveData()
    internal val data: MutableLiveData<List<Category>> = MutableLiveData()
    internal val loadingProgressVisibility: MutableLiveData<Boolean> = MutableLiveData()

    private val getCategoriesOfHomeUseCase = GetCategoriesOfHomeUseCase(app)
    private val getBannerDataUseCase = GetBannerDataUseCase(app)

    fun loadData() {
        loadingProgressVisibility.postValue(true)
        getCategoriesOfHomeUseCase.loadData { categoriesData ->
            data.postValue(categoriesData)
            loadingProgressVisibility.postValue(false)
        }
        getBannerDataUseCase.loadData {
            bannerData.postValue(it)
        }
    }
}
