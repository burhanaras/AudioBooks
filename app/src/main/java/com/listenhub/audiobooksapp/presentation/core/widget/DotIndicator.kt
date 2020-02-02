package com.listenhub.audiobooksapp.presentation.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.listenhub.audiobooksapp.R

/**
 * Developed by tcbaras on 2019-12-03.
 */
class DotIndicator : LinearLayout {

    private var dots: MutableList<View> = mutableListOf()

    constructor(context: Context) : super(context) {
        dots()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        dots()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        dots()
    }

    fun dots(num: Int = 2, smallDots: Boolean = false) {
        orientation = HORIZONTAL
        removeAllViews()
        dots.clear()
        val dotSize = if (smallDots) 12 else 30

        for (index in 0 until num) {
            val dot = View(context)
            dot.setBackgroundResource(if (index == 0) R.drawable.ic_dot_dark else R.drawable.ic_dot_light)
            addView(dot, dotSize, dotSize)
            dots.add(dot)

            val lp = dot.layoutParams as LinearLayout.LayoutParams

            lp.apply {
                leftMargin = 10
                rightMargin = 10
                topMargin = 10
                bottomMargin = 10
                dot.layoutParams = this
            }
        }
    }

    fun setSelected(position: Int) {
        val positionSafe = position % dots.size
        dots.forEach { it.setBackgroundResource(R.drawable.ic_dot_light) }
        dots[positionSafe].setBackgroundResource(R.drawable.ic_dot_dark)

    }
}