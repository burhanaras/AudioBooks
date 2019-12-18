package com.burhan.audiobooksapp.presentation.ui.showall

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import com.burhan.audiobooksapp.domain.usecase.GetAudioBooksOfCategoryUseCase

class ShowAllViewModel : ViewModel() {

    internal var audioBooks: MutableLiveData<List<AudioBook>> = MutableLiveData()

    private var getAudioBooksOfCategoryUseCase = GetAudioBooksOfCategoryUseCase()

    fun loadData(category: Category) {
        getAudioBooksOfCategoryUseCase.execute(category) { audioBookz ->
            audioBooks.postValue(audioBookz)
        }
    }
}
