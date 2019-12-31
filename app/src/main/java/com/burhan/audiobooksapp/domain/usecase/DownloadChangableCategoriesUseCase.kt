package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.model.Category
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Developed by tcbaras on 2019-12-16.
 */
class DownloadChangeableCategoriesUseCase {

    private val fireStoreDB = FirebaseFirestore.getInstance()

    fun execute(callback: (categories: List<Category>) -> Unit) {
        val categories = mutableListOf<Category>()
        fireStoreDB.collection("CategoriesChangeable")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        if (document["order"] != "-1") {
                            categories.add(
                                Category(
                                    id = document["name"].toString(),
                                    name = document["name"].toString(),
                                    imageUrl = "",
                                    audioBooks = listOf(),
                                    order = document["order"].toString()
                                )
                            )
                        }
                    }
                    categories.sortBy {
                        it.order.toInt()
                    }
                    callback(categories)
                } else {
                    callback(listOf())
                }
            }
    }
}