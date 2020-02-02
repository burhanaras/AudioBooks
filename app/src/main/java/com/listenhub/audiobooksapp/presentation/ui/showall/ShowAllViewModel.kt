package com.listenhub.audiobooksapp.presentation.ui.showall

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.model.Category
import com.listenhub.audiobooksapp.domain.usecase.GetAudioBooksOfCategoryUseCase

class ShowAllViewModel(val app: Application) : AndroidViewModel(app) {

    internal var audioBooks: MutableLiveData<List<AudioBook>> = MutableLiveData()

    private var getAudioBooksOfCategoryUseCase = GetAudioBooksOfCategoryUseCase(app)

    fun loadData(category: Category) {
        getAudioBooksOfCategoryUseCase.execute(category) { audioBookz ->
            audioBooks.postValue(audioBookz)
        }
    }
}
