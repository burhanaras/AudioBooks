package com.burhan.audiobooksapp.presentation.core.extension

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.palette.graphics.Palette

/**
 * Developed by tcbaras on 2019-12-03.
 */

fun Bitmap.createPalette(callback: (palette: Palette) -> Unit) {
    Palette.from(this).generate {
        it?.let { palette ->
            callback(palette)
        }
    }
}

inline fun Bitmap.blurred(
    context: Context,
    callBack: (blurredBitMap: Bitmap) -> Unit
) {
    val inputBitmap =
        Bitmap.createScaledBitmap(this, this.width, this.height, false)
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    val renderScript = RenderScript.create(context)
    val intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    val tempInput = Allocation.createFromBitmap(renderScript, inputBitmap)
    val tempOutput = Allocation.createFromBitmap(renderScript, outputBitmap)
    intrinsicBlur.apply {
        setRadius(25f)
        setInput(tempInput)
        forEach(tempOutput)
    }
    tempOutput.copyTo(outputBitmap)
    renderScript.destroy()
    callBack(outputBitmap)
}