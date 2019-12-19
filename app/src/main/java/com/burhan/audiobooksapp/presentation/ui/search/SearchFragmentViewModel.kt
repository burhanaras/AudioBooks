package com.burhan.audiobooksapp.presentation.ui.search

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.usecase.SearchUseCase
import com.google.firebase.analytics.FirebaseAnalytics

class SearchFragmentViewModel(app: Application) : AndroidViewModel(app) {

    internal var searchResults: MutableLiveData<List<AudioBook>> = MutableLiveData()

    private var searchUseCase = SearchUseCase(app)

    private var fireBaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(app)

    fun search(query: String) {
        searchUseCase.search(query) {
            searchResults.postValue(it)
        }

        fireBaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SEARCH,
            Bundle().apply { putString(FirebaseAnalytics.Param.CONTENT, query) })
    }
}
