package com.burhan.audiobooksapp.domain.dummydata

import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Chapter

/**
 * Developed by tcbaras on 2019-12-01.
 */
class DummyData {
    fun provide100AudioBooks(callback: (audioBooks: List<AudioBook>) -> Unit) {

        var audioBooks = mutableListOf<AudioBook>()

        var chapters = mutableListOf<Chapter>()
        chapters.add(
            Chapter(
                "Chapter 1",
                "https://samples.audible.com/bk/peng/003245/bk_peng_003245_sample.mp3",
                "30:00"
            )
        )
        chapters.add(
            Chapter(
                "Chapter 2",
                "https://samples.audible.com/bk/rand/005085/bk_rand_005085_sample.mp3",
                "20:00"
            )
        )
        chapters.add(
            Chapter(
                "Chapter 3",
                "https://samples.audible.com/bk/peng/004118/bk_peng_004118_sample.mp3",
                "25:00"
            )
        )

        repeat(100) {

            val audioBook = AudioBook(
                id = "1",
                name = "Educated A Memoir",
                imageUrl = "https://m.media-amazon.com/images/I/41dIDDpGepL._SL500_.jpg",
                description = "Born to survivalists in the mountains of Idaho, Tara Westover was 17 the first time she set foot in a classroom. Her family was so isolated from mainstream society that there was no one to ensure the children received an education and no one to intervene when one of Tara’s older brothers became violent. When another brother got himself into college, Tara decided to try a new kind of life. Her quest for knowledge transformed her, taking her over oceans and across continents, to Harvard and to Cambridge University. Only then would she wonder if she’d traveled too far, if there was still a way home.",
                author = "Tara Westover",
                url = "https://samples.audible.com/bk/rand/005325/bk_rand_005325_sample.mp3",
                category = "Science-Fiction",
                isSample = false,
                length = "03:20:15",
                language = "English",
                chapters = chapters
            )

            audioBooks.add(audioBook)
        }


        callback(audioBooks.shuffled())
    }
}