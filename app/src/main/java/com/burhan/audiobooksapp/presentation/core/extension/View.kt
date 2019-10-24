package com.burhan.audiobooksapp.presentation.core.extension

import android.view.View

/**
 * Developed by tcbaras on 2019-10-24.
 */

inline fun View.setSingleClickListener(throttleTime: Long = 600L, crossinline action: (v: View) -> Unit) {
    setOnClickListener(generateSingleClickListener(throttleTime, action))
}

inline fun generateSingleClickListener(
    throttleTime: Long = 600L,
    crossinline action: (v: View) -> Unit
): View.OnClickListener? {
    return object : View.OnClickListener {
        private var theFirstClickTime: Long = 0
        override fun onClick(view: View) {
            if (System.currentTimeMillis() - theFirstClickTime > throttleTime) {
                theFirstClickTime = System.currentTimeMillis()
                action(view)
            }

        }

    }
}
