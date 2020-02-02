package com.listenhub.audiobooksapp.domain.usecase.download

import android.content.Context
import com.listenhub.audiobooksapp.data.db.AppDatabase
import com.listenhub.audiobooksapp.data.mapper.mapToCategoryEntity
import com.listenhub.audiobooksapp.data.mapper.mapToDBEntity
import com.listenhub.audiobooksapp.domain.dummydata.DummyData
import com.listenhub.audiobooksapp.domain.model.Category
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-12-16.
 */
class DownloadAllDataUseCase(
    val context: Context
) {



    fun executeFromJson(callback: () -> Unit) {

        GlobalScope.launch {
            val category = Category(id = "01", name = "Science-Fiction")
            val categories = listOf(category)
            AppDatabase.getInstance(context).audioBookDao()
                .insertAllCategories(categories.mapToCategoryEntity())

            DummyData().provide100AudioBooks {
                AppDatabase.getInstance(context).audioBookDao()
                    .insertAll(it.mapToDBEntity())
                callback()
            }
        }
    }
}