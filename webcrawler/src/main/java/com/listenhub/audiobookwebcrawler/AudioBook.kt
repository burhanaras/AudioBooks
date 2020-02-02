package com.listenhub.audiobookwebcrawler

/**
 * Developed by tcbaras on 2019-12-10.
 */
data class AudioBook(
    var id: String = "",
    var name: String = "",
    var author: String = "",
    var imageUrl: String = "",
    var mp3: String = "",
    var description: String = "",
    var category: String = "",
    var isFavorite: Boolean = false,
    var isSample: Boolean = false,
    var length: String,
    var language: String,
    var chapters: MutableList<Chapter> = mutableListOf()
)

data class Chapter(
    var name: String,
    var url: String
)