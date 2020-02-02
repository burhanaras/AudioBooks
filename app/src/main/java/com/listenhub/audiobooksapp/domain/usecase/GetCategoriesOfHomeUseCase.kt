package com.listenhub.audiobooksapp.domain.usecase

import android.content.Context
import com.listenhub.audiobooksapp.data.db.AppDatabase
import com.listenhub.audiobooksapp.data.mapper.mapToModel
import com.listenhub.audiobooksapp.domain.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-10-24.
 */
class GetCategoriesOfHomeUseCase(val context: Context) {

    fun loadData(callback: (categories: List<Category>) -> Unit) {

        val response = mutableListOf<Category>()

        GlobalScope.launch(Dispatchers.IO) {
            val categories = AppDatabase.getInstance(context).audioBookDao().getAllCategories()
            categories.forEach { category ->
                val books =
                    AppDatabase.getInstance(context).audioBookDao()
                        .getByCategory(category.name, limit = 20)
                val newCategory = category.mapToModel().apply {
                    audioBooks = books.mapToModel()
                }
                if (newCategory.audioBooks.isNotEmpty()) {
                    response.add(newCategory)
                }
                if (newCategory.name == categories.last().name) {
                    callback(response)
                }
            }
        }
    }
}