package com.listenhub.audiobookwebcrawler

/**
 * Developed by tcbaras on 2019-12-31.
 */
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String = "",
    var audioBooks: List<AudioBook> = listOf(),
    var order: String = "1000"
)