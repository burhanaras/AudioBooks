package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Developed by tcbaras on 2019-12-16.
 */
class DownloadAudioBooksOfCategoryUseCase {

    private val fireStoreDB = FirebaseFirestore.getInstance()

    fun downloadAudioBooksOfCategory(
        categories: List<Category>,
        callback: (categories: List<AudioBook>) -> Unit
    ) {
        val allBooks = mutableListOf<AudioBook>()
        var counter = 0
        categories.forEach { category ->
            downloadAudioBooksOfCategory(category) {
                allBooks.addAll(it)
                counter++
                if (counter == categories.size) {
                    callback(allBooks)
                }
            }
        }
    }

    fun downloadAudioBooksOfCategory(
        category: Category,
        callback: (audioBooks: List<AudioBook>) -> Unit
    ) {
        val audioBooks = mutableListOf<AudioBook>()
        fireStoreDB.collection(category.name)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        audioBooks.add(
                            AudioBook(
                                id = document["name"].toString(),
                                name = document["name"].toString(),
                                imageUrl = document["imageUrl"].toString(),
                                description = document["description"].toString(),
                                author = document["author"].toString(),
                                url = document["mp3"].toString(),
                                category = category.name
                            )
                        )
                    }
                    callback(audioBooks)
                } else {
                    callback(listOf())
                }
            }
    }
}