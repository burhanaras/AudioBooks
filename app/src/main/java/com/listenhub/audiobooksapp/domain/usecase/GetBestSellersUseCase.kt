package com.listenhub.audiobooksapp.domain.usecase

import android.content.Context
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.model.Category
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-10-24.
 */
class GetBestSellersUseCase(val context: Context) {
    fun loadData(callback: (audioBooks: List<AudioBook>) -> Unit) {
        GlobalScope.launch {
            val bestSellersCategory = Category(bestSellersId, bestSellersId)
            GetAudioBooksOfCategoryUseCase(context).execute(category = bestSellersCategory) {
                callback(it)
            }
        }
    }

    companion object {
        const val bestSellersId = "Best Sellers"
    }
}