package com.burhan.audiobooksapp.presentation.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.burhan.audiobooksapp.domain.model.Category
import com.burhan.audiobooksapp.domain.usecase.GetCategoriesOfDashboardUseCase

class DashboardFragmentViewModel : ViewModel() {
    internal val data: MutableLiveData<List<Category>> = MutableLiveData()

    private val getCategoriesOfHomeUseCase = GetCategoriesOfDashboardUseCase()

    fun loadData() {
        getCategoriesOfHomeUseCase.loadData { categoriesData ->
            data.postValue(categoriesData)
        }
    }
}
