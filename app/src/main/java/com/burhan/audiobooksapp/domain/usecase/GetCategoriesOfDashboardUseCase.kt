package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.dummydata.DummyData
import com.burhan.audiobooksapp.domain.model.Category

/**
 * Developed by tcbaras on 2019-10-24.
 */
class GetCategoriesOfDashboardUseCase {
    fun loadData(callback: (categories: List<Category>) -> Unit) {
        dummy { callback(it) }
    }

    private fun dummy(callback: (categories: List<Category>) -> Unit) {

        DummyData().provideTenAudioBooks { audioBooks ->
            var categories: MutableList<Category> = mutableListOf()
            for (index in 0..40) {
                val category = Category(
                    id = "9",
                    name = "The Best of 2019",
                    imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
                    audioBooks = audioBooks.shuffled() + audioBooks.shuffled() + audioBooks.shuffled()
                )
                categories.add(category)
            }

            callback(categories.shuffled())
        }


    }


}