package com.burhan.audiobooksapp.domain.model

/**
 * Developed by tcbaras on 2019-10-24.
 */
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String,
    val audioBooks: List<AudioBook>
)