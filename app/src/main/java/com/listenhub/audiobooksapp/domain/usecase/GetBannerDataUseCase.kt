package com.listenhub.audiobooksapp.domain.usecase

import android.content.Context
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.model.Category
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-12-31.
 */
class GetBannerDataUseCase(val context: Context) {

    fun loadData(callback: (List<AudioBook>) -> Unit) {
        GlobalScope.launch {
            val userFavoritesCategory = Category(userFavoritesTag, userFavoritesTag)
            GetAudioBooksOfCategoryUseCase(context).execute(userFavoritesCategory) {
                callback(it.shuffled().take(6))
            }
        }
    }

    companion object {
        const val userFavoritesTag = "User Favorites"
    }
}