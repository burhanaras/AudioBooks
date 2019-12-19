package com.burhan.audiobooksapp.domain.usecase

import android.content.Context
import com.burhan.audiobooksapp.data.db.AppDatabase
import com.burhan.audiobooksapp.data.mapper.mapToModel
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GetAudioBooksOfCategoryUseCase(val context: Context) {

    fun execute(
        category: Category,
        callback: (audioBooks: List<AudioBook>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val audioBooksOfCategory =
                AppDatabase.getInstance(context).audioBookDao().getAllByCategory(category.id)
            val books = audioBooksOfCategory.map { it.mapToModel() }
            callback(books)
        }


    }
}
