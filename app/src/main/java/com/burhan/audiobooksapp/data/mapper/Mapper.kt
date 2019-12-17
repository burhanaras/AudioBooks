package com.burhan.audiobooksapp.data.mapper

import com.burhan.audiobooksapp.data.entity.AudioBookEntity
import com.burhan.audiobooksapp.data.entity.CategoryEntity
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category

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
        category = category
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
        category = category
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