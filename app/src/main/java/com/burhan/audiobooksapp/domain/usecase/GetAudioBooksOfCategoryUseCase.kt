package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import com.google.firebase.firestore.FirebaseFirestore

class GetAudioBooksOfCategoryUseCase {

    private val fireStoreDB = FirebaseFirestore.getInstance()


    fun execute(
        category: Category,
        callback: (audioBooks: List<AudioBook>) -> Unit
    ) {
        val audioBooks = mutableListOf<AudioBook>()
        fireStoreDB.collection(category.id)
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
