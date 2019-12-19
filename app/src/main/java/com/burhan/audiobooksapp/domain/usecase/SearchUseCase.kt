package com.burhan.audiobooksapp.domain.usecase

import android.content.Context
import com.burhan.audiobooksapp.data.db.AppDatabase
import com.burhan.audiobooksapp.data.mapper.mapToModel
import com.burhan.audiobooksapp.domain.model.AudioBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-12-09.
 */
class SearchUseCase(val context: Context) {

    fun search(query: String, callback: (result: List<AudioBook>) -> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            val queryWithWildCard = "%$query%"
            val audioBookEntities = AppDatabase.getInstance(context).audioBookDao().search(queryWithWildCard)
            val audioBooks = audioBookEntities.map { it.mapToModel() }
            callback(audioBooks)
        }
    }
}