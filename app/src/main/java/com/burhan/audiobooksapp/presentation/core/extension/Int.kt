package com.burhan.audiobooksapp.presentation.core.extension

/**
 * Developed by tcbaras on 2019-11-14.
 */

fun Int.toPrettyTimeInfoAsMMSS(): String {
    val minutes = this / 60
    val seconds = this % 60
    return "%02d:%02d".format(minutes, seconds)
}