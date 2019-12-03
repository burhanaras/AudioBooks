package com.burhan.audiobooksapp.presentation.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.burhan.audiobooksapp.R

/**
 * Developed by tcbaras on 2019-12-03.
 */
class DotIndicator : LinearLayout {

    private lateinit var dot0: View
    private lateinit var dot1: View

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initViews()
    }

    private fun initViews() {
        orientation = HORIZONTAL
        dot0 = View(context)
        dot0.setBackgroundResource(R.drawable.ic_dot_dark)
        addView(dot0, 30, 30)

        dot1 = View(context)
        dot1.setBackgroundResource(R.drawable.ic_dot_light)
        addView(dot1, 30, 30)

        val lp = dot0.layoutParams as LinearLayout.LayoutParams

        lp.apply {
            leftMargin = 10
            rightMargin = 10
            topMargin = 10
            bottomMargin = 10
            dot0.layoutParams = this
        }

        val lp0 = dot1.layoutParams as LinearLayout.LayoutParams
        lp0.apply {
            leftMargin = 10
            rightMargin = 10
            topMargin = 10
            bottomMargin = 10
            dot1.layoutParams = this
        }

    }

    fun setSelected(position: Int) {
        when (position) {
            0 -> {
                dot0.setBackgroundResource(R.drawable.ic_dot_dark)
                dot1.setBackgroundResource(R.drawable.ic_dot_light)
            }
            else -> {
                dot0.setBackgroundResource(R.drawable.ic_dot_light)
                dot1.setBackgroundResource(R.drawable.ic_dot_dark)
            }
        }
    }
}