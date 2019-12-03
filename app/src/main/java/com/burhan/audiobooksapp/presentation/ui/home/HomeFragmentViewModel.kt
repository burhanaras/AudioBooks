package com.burhan.audiobooksapp.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import com.burhan.audiobooksapp.domain.usecase.GetCategoriesOfHomeUseCase

class HomeFragmentViewModel : ViewModel() {
    internal val bannerData: MutableLiveData<List<AudioBook>> = MutableLiveData()
    internal val data: MutableLiveData<List<Category>> = MutableLiveData()

    private val getCategoriesOfHomeUseCase = GetCategoriesOfHomeUseCase()

    fun loadData() {
        getCategoriesOfHomeUseCase.loadData { categoriesData ->
            data.postValue(categoriesData)
            bannerData.postValue(categoriesData.first().audioBooks)
        }
    }
}
