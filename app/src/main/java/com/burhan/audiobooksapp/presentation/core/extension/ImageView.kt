package com.burhan.audiobooksapp.presentation.core.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Developed by tcbaras on 2019-10-24.
 */

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun ImageView.loadBitmapFromUrl(url: String, callback: (bitmap: Bitmap) -> Unit) {
    Glide.with(context).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
        override fun onLoadCleared(placeholder: Drawable?) {

        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            callback(resource)
        }
    })
}

fun ImageView.loadFromUrlBlurredImage(url: String) {
    Glide.with(context).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
        override fun onLoadCleared(placeholder: Drawable?) {

        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            resource.blurred(context) { blurredImage ->
                setImageBitmap(blurredImage)
            }
        }
    })
}
