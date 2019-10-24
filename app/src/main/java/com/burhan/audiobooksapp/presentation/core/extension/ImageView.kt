package com.burhan.audiobooksapp.presentation.core.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Developed by tcbaras on 2019-10-24.
 */

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context).load(url).into(this)
}