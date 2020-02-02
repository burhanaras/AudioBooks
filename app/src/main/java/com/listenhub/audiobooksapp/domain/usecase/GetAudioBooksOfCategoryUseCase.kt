package com.listenhub.audiobooksapp.domain.usecase

import android.content.Context
import com.listenhub.audiobooksapp.data.db.AppDatabase
import com.listenhub.audiobooksapp.data.mapper.mapToModel
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.model.Category
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
