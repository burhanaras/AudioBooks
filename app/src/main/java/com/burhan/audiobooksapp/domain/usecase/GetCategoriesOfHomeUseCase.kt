package com.burhan.audiobooksapp.domain.usecase

import android.content.Context
import android.util.Log
import com.burhan.audiobooksapp.data.db.AppDatabase
import com.burhan.audiobooksapp.data.mapper.mapToModel
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-10-24.
 */
class GetCategoriesOfHomeUseCase(val context: Context) {

    private val fireStoreDB = FirebaseFirestore.getInstance()

    fun loadData(callback: (categories: List<Category>) -> Unit) {
//        val response = mutableListOf<Category>()
//        getCategories { categories ->
//            categories.forEach { category ->
//                getAudioBooksOfCategory(category) {
//                    response.add(it)
//                    if (response.size == categories.size) {
//                        callback(response)
//                    }
//                }
//            }
//        }

        GlobalScope.launch(Dispatchers.IO) {
            val response = mutableListOf<Category>()
            val categories = AppDatabase.getInstance(context).audioBookDao().getAllCategories()
            categories.forEach { category ->
                val books =
                    AppDatabase.getInstance(context).audioBookDao().getByCategory(category.name, limit = 20)
                val newCategory = category.mapToModel().apply {
                    audioBooks = books.mapToModel()
                }
                if (newCategory.audioBooks.isNotEmpty()){
                    response.add(newCategory)
                }
                if (newCategory.name == categories.last().name){
                    callback(response)
                }
            }
        }
    }

    private fun getCategories(callback: (categories: List<String>) -> Unit) {
        var categories = mutableListOf<String>()
        fireStoreDB.collection("Categories")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        Log.d("GetCategoriesOfHome", "${document.id} ==> ${document.data}")
                        categories.add(
                            document["name"].toString()
                        )
                    }
                    callback(categories)
                } else {
                    Log.e("GetCategoriesOfHome", it.exception.toString())
                    callback(listOf())
                }
            }
    }

    private fun getAudioBooksOfCategory(
        category: String,
        callback: (category: Category) -> Unit
    ) {
        var audioBooks = mutableListOf<AudioBook>()
        fireStoreDB.collection(category.trim()).limit(10)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        Log.d("GetCategoriesOfHome", "${document.id} ==> ${document.data}")
                        audioBooks.add(
                            AudioBook(
                                document["name"].toString(),
                                document["name"].toString(),
                                document["imageUrl"].toString(),
                                document["description"].toString(),
                                document["author"].toString(),
                                document["mp3"].toString()
                            )
                        )
                    }
                    callback(Category("", category, "", audioBooks))
                } else {
                    Log.e("GetCategoriesOfHome", it.exception.toString())
                }
            }
    }

}