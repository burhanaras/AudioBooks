package com.burhan.audiobooksapp.presentation.core.widget

/**
 * Developed by tcbaras on 2019-11-27.
 */
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.burhan.audiobooksapp.R


class MiniEqualizerView : LinearLayout {

    internal lateinit var musicBar0: View
    internal lateinit var musicBar1: View
    internal lateinit var musicBar2: View

    private var playingSet: AnimatorSet? = null
    private var stopSet: AnimatorSet? = null
    var isAnimating: Boolean? = false
        internal set

    private var foregroundColor: Int = 0

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setAttrs(context, attrs)
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setAttrs(context, attrs)
        initViews()
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MiniEqualizerView,
            0, 0
        )

        try {
            foregroundColor = a.getInt(R.styleable.MiniEqualizerView_foreGroundColor, Color.BLACK)
        } finally {
            a.recycle()
        }
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.view_mini_equalizer, this, true).apply {
            musicBar0 = findViewById(R.id.viewMiniEqualizerBar0)
            musicBar1 = findViewById(R.id.viewMiniEqualizerBar1)
            musicBar2 = findViewById(R.id.viewMiniEqualizerBar2)
        }

        musicBar0.setBackgroundColor(foregroundColor)
        musicBar1.setBackgroundColor(foregroundColor)
        musicBar2.setBackgroundColor(foregroundColor)
        setPivots()
    }


    private fun setPivots() {
        musicBar0.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (musicBar0.height > 0) {
                    musicBar0.pivotY = musicBar0.height.toFloat()
                    musicBar0.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
        musicBar1.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (musicBar1.height > 0) {
                    musicBar1.pivotY = musicBar1.height.toFloat()
                    musicBar1.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
        musicBar2.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (musicBar2.height > 0) {
                    musicBar2.pivotY = musicBar2.height.toFloat()
                    musicBar2.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
    }

    fun animateBars() {
        isAnimating = true
        if (playingSet == null) {
            val scaleYBar0 = ObjectAnimator.ofFloat(
                musicBar0,
                "scaleY",
                0.2f,
                0.8f,
                0.1f,
                0.1f,
                0.3f,
                0.1f,
                0.2f,
                0.8f,
                0.7f,
                0.2f,
                0.4f,
                0.9f,
                0.7f,
                0.6f,
                0.1f,
                0.3f,
                0.1f,
                0.4f,
                0.1f,
                0.8f,
                0.7f,
                0.9f,
                0.5f,
                0.6f,
                0.3f,
                0.1f
            ).apply { repeatCount = ValueAnimator.INFINITE }

            val scaleYBar1 = ObjectAnimator.ofFloat(
                musicBar1,
                "scaleY",
                0.2f,
                0.5f,
                1.0f,
                0.5f,
                0.3f,
                0.1f,
                0.2f,
                0.3f,
                0.5f,
                0.1f,
                0.6f,
                0.5f,
                0.3f,
                0.7f,
                0.8f,
                0.9f,
                0.3f,
                0.1f,
                0.5f,
                0.3f,
                0.6f,
                1.0f,
                0.6f,
                0.7f,
                0.4f,
                0.1f
            ).apply { repeatCount = ValueAnimator.INFINITE }

            val scaleYBar2 = ObjectAnimator.ofFloat(
                musicBar2,
                "scaleY",
                0.6f,
                0.5f,
                1.0f,
                0.6f,
                0.5f,
                1.0f,
                0.6f,
                0.5f,
                1.0f,
                0.5f,
                0.6f,
                0.7f,
                0.2f,
                0.3f,
                0.1f,
                0.5f,
                0.4f,
                0.6f,
                0.7f,
                0.1f,
                0.4f,
                0.3f,
                0.1f,
                0.4f,
                0.3f,
                0.7f
            ).apply { repeatCount = ValueAnimator.INFINITE }

            playingSet = AnimatorSet().apply {
                playTogether(scaleYBar0, scaleYBar1, scaleYBar2)
                duration = 3000
                interpolator = LinearInterpolator()
                start()
            }

        } else {
            if (playingSet?.isPaused!!) {
                playingSet?.resume()
            }
        }

    }

    fun stopBars() {
        isAnimating = false
        if (playingSet != null && playingSet!!.isRunning && playingSet!!.isStarted) {
            playingSet!!.pause()
        }

        if (stopSet == null) {
            // Animate stopping bars
            val scaleY0 = ObjectAnimator.ofFloat(musicBar0, "scaleY", 0.1f)
            val scaleY1 = ObjectAnimator.ofFloat(musicBar1, "scaleY", 0.1f)
            val scaleY2 = ObjectAnimator.ofFloat(musicBar2, "scaleY", 0.1f)
            stopSet = AnimatorSet().apply {
                playTogether(scaleY2, scaleY1, scaleY0)
                duration = 200
                start()
            }
        } else if (!stopSet?.isStarted!!) {
            stopSet?.start()
        }
    }


}