package com.burhan.audiobooksapp.domain.usecase.download

import android.content.Context
import com.burhan.audiobooksapp.data.db.AppDatabase
import com.burhan.audiobooksapp.data.mapper.mapToCategoryEntity
import com.burhan.audiobooksapp.data.mapper.mapToDBEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-12-16.
 */
class DownloadAllDataUseCase(
    val context: Context,
    private var downloadCategoriesUseCase: DownloadCategoriesUseCase = DownloadCategoriesUseCase(),
    private var downloadAudioBooksOfCategoryUseCase: DownloadAudioBooksOfCategoryUseCase = DownloadAudioBooksOfCategoryUseCase()
) {

    fun execute(callback: () -> Unit) {
        downloadCategoriesUseCase.execute { categories ->
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context).audioBookDao()
                    .insertAllCategories(categories.mapToCategoryEntity())
                downloadAudioBooksOfCategoryUseCase.downloadAudioBooksOfCategory(categories) {
                    GlobalScope.launch(Dispatchers.IO) {
                        AppDatabase.getInstance(context).audioBookDao()
                            .insertAll(it.mapToDBEntity())
                        callback()
                    }

                }
            }
        }
    }

    fun executeFromJson(callback: () -> Unit) {

    }
}