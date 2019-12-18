package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.dummydata.DummyData
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Developed by tcbaras on 2019-12-09.
 */
class SearchUseCase {

    private val fireStoreDB = FirebaseFirestore.getInstance()

    fun search(query: String, callback: (result: List<AudioBook>) -> Unit) {
        DummyData().provideTenAudioBooks {
            val books = it.filter {
                it.name.toLowerCase().contains(query.toLowerCase()) ||
                        it.author.toLowerCase().contains(query.toLowerCase())
            }

            callback(books)
        }

//
//        val books = mutableListOf<AudioBook>()
//        fireStoreDB.collection("Comedy").whereArrayContains("name", "Idiot").get()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    it.result?.let {
//                        for (document in it) {
//                            val book = AudioBook(
//                                document["name"].toString(),
//                                document["name"].toString(),
//                                document["imageUrl"].toString(),
//                                document["description"].toString(),
//                                document["author"].toString(),
//                                document["mp3"].toString()
//                            )
//                            books.add(book)
//                        }
//
//                        callback(books)
//                    }
//                }
//            }
    }
}