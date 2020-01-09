package com.burhan.audiobookwebcrawler

/**
 * Developed by tcbaras on 2019-12-10.
 */
data class AudioBook(
    var name: String = "",
    var author: String = "",
    var imageUrl: String = "",
    var mp3: String = "",
    var description: String = ""
)