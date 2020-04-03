package com.amrit.circulargraphview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver

class CircularSeekBar : View {

    private lateinit var paintCircle: Paint

    private var size: Float = 0.0f

    constructor(context: Context) : this(context, null) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(attrs)
    }

    private fun setOnMeasureCallback() {
        val vTO = viewTreeObserver
        vTO.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                removeOnGlobalLayoutListener(this)
                size = (measuredWidth / 2).toFloat()
            }
        })
    }

    private fun removeOnGlobalLayoutListener(layoutListener: ViewTreeObserver.OnGlobalLayoutListener) {
        viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }


    private fun init(set: AttributeSet?) {
        if (set == null) return

        paintCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        paintCircle.color = Color.parseColor("#E74300")
        setOnMeasureCallback()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(size, size, size, paintCircle)
    }
}