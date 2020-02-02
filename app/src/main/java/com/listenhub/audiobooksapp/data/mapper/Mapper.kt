package com.listenhub.audiobooksapp.data.mapper

import com.listenhub.audiobooksapp.data.entity.AudioBookEntity
import com.listenhub.audiobooksapp.data.entity.CategoryEntity
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.domain.model.Category
import com.google.gson.Gson

/**
 * Developed by tcbaras on 2019-12-16.
 */

fun AudioBook.mapToDBEntity(): AudioBookEntity {
    return AudioBookEntity(
        id = id,
        name = name,
        author = author,
        imageUrl = imageUrl,
        url = url,
        description = description,
        category = category,
        isSample = isSample,
        length = length,
        language = language,
        chapters = Gson().toJson(chapters)
    )
}

fun List<AudioBook>.mapToDBEntity(): List<AudioBookEntity> {
    return this.map {
        it.mapToDBEntity()
    }
}

fun AudioBookEntity.mapToModel(): AudioBook {
    return AudioBook(
        id = id,
        name = name,
        author = author,
        imageUrl = imageUrl,
        url = url,
        description = description,
        category = category,
        isSample = isSample,
        length = length,
        language = language,
        chapters = Gson().fromJson(chapters, MutableList()<Chapter>::class.java)
    )
}

fun List<AudioBookEntity>.mapToModel(): List<AudioBook> {
    return this.map {
        it.mapToModel()
    }
}


fun Category.mapToDBEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name
    )
}

fun List<Category>.mapToCategoryEntity(): List<CategoryEntity> {
    return this.map {
        it.mapToDBEntity()
    }
}

fun CategoryEntity.mapToModel(): Category {
    return Category(
        id = id,
        name = name,
        imageUrl = "",
        audioBooks = listOf()
    )
}

fun List<CategoryEntity>.mapToCategory(): List<Category> {
    return this.map {
        it.mapToModel()
    }
}
