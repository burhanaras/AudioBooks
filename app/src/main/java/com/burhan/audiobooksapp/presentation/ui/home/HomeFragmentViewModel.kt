package com.burhan.audiobooksapp.presentation.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import com.burhan.audiobooksapp.domain.usecase.GetCategoriesOfHomeUseCase

class HomeFragmentViewModel(app: Application) : AndroidViewModel(app) {
    internal val bannerData: MutableLiveData<List<AudioBook>> = MutableLiveData()
    internal val data: MutableLiveData<List<Category>> = MutableLiveData()

    private val getCategoriesOfHomeUseCase = GetCategoriesOfHomeUseCase(app)

    fun loadData() {
        getCategoriesOfHomeUseCase.loadData { categoriesData ->
            data.postValue(categoriesData)
            if (categoriesData.isNotEmpty() && categoriesData.first().audioBooks.size > 6)
                bannerData.postValue(categoriesData.first().audioBooks.take(6))
        }
    }
}
