package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.dummydata.DummyData
import com.burhan.audiobooksapp.domain.model.AudioBook

/**
 * Developed by tcbaras on 2019-12-09.
 */
class SearchUseCase {

    fun search(query: String, callback: (result: List<AudioBook>) -> Unit) {
        DummyData().provideTenAudioBooks {
            val books = it.filter {
                it.name.toLowerCase().contains(query.toLowerCase()) ||
                        it.author.toLowerCase().contains(query.toLowerCase())
            }

            callback(books)
        }

    }
}