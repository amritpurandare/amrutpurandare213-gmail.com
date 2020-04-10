package com.amrit.circulargraphview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import kotlin.math.max
import kotlin.math.min

class CircularGraph : View {

    private var mMaxLimit: Int = 0
    private var mUsedLimit: Int = 0

    private var mFilledColor: Int = 0
    private var mDefaultColor: Int = 0

    private var mFilledWidth: Float = 0f
    private var mDefaultWidth: Float = 0f

    private lateinit var mDefaultCircle: Paint
    private lateinit var mFilledCircle: Paint
    private lateinit var mReachedEdgePaint: Paint

    /* Angle to draw in clock-wise direction*/
    private var mSweepAngle: Double = 0.0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        initializeAttributes(attrs, defStyleAttr)
        initializePadding()
        initializePaintObjects()
    }


    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircularGraph, defStyleAttr, 0)

        mMaxLimit = typedArray.getInt(R.styleable.CircularGraph_circle_max_value, 100)
        mUsedLimit = typedArray.getInt(R.styleable.CircularGraph_circle_filled_value, 100)

        if (mUsedLimit > mMaxLimit) mUsedLimit = mMaxLimit


        mFilledColor = typedArray.getColor(
            R.styleable.CircularGraph_circle_filled_color,
            getColor(context, R.color.def_reached_color)
        )
        mDefaultColor = typedArray.getColor(
            R.styleable.CircularGraph_circle_default_color,
            getColor(context, R.color.def_wheel_color)
        )

        mDefaultWidth =
            typedArray.getDimension(
                R.styleable.CircularGraph_circle_default_width,
                resources.getDimension(R.dimen.def_wheel_width)
            )

        mFilledWidth =
            typedArray.getDimension(
                R.styleable.CircularGraph_circle_filled_width,
                mDefaultWidth
            )

        typedArray.recycle()
    }


    private fun initializePadding() {

        val maxPadding = max(
            paddingLeft,
            max(
                paddingTop,
                max(paddingRight, max(paddingBottom, max(paddingStart, paddingEnd)))
            )
        )

        setPadding(maxPadding, maxPadding, maxPadding, maxPadding)
    }

    private fun initializePaintObjects() {

        mDefaultCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        mDefaultCircle.color = mDefaultColor
        mDefaultCircle.style = Paint.Style.STROKE
        mDefaultCircle.strokeWidth = mDefaultWidth

        mFilledCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        mFilledCircle.color = mFilledColor
        mFilledCircle.style = Paint.Style.STROKE
        mFilledCircle.strokeWidth = mFilledWidth

        mReachedEdgePaint = Paint(mFilledCircle)
        mReachedEdgePaint.style = Paint.Style.FILL
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        val min = min(width, height)
        setMeasuredDimension(min, min)

        refreshCirclePositions()
    }

    private fun refreshCirclePositions() {
        mSweepAngle = mUsedLimit.toDouble() / mMaxLimit * 360.0
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val left = paddingLeft + mDefaultWidth / 2
        val top = paddingTop + mDefaultWidth / 2
        val right = canvas!!.width - paddingRight - mDefaultWidth / 2
        val bottom = canvas.height - paddingBottom - mDefaultWidth / 2
        val xCoordinate = (left + right) / 2
        val yCoordinate = (top + bottom) / 2
        val circleRadius = (canvas.width - paddingLeft - paddingRight) / 2 - mDefaultWidth / 2

        canvas.drawCircle(xCoordinate, yCoordinate, circleRadius, mDefaultCircle)

        canvas.drawArc(
            RectF(left, top, right, bottom),
            -90f,
            mSweepAngle.toFloat(),
            false,
            mFilledCircle
        )

    }

    fun getMaxLimit() = mMaxLimit

    fun setMaxLimit(maxLimit: Int) {
        mMaxLimit = maxLimit
        refreshCirclePositions()
        invalidate()
    }

    fun getUsedLimit() = mUsedLimit

    fun setUsedLimit(usedLimit: Int) {
        mUsedLimit = min(usedLimit, mMaxLimit)
        refreshCirclePositions()
        invalidate()
    }

    fun getDefaultColor() = mDefaultColor

    fun setDefaultColor(defaultColor: Int) {
        mDefaultColor = defaultColor
        mDefaultCircle.color = defaultColor
        invalidate()
    }

    fun getFilledColor() = mFilledColor

    fun setFilledColor(filledColor: Int) {
        mFilledColor = filledColor
        mFilledCircle.color = filledColor
        mReachedEdgePaint.color = filledColor
        invalidate()
    }

    fun getDefaultWidth() = mDefaultWidth

    fun setDefaultWidth(defaultWidth: Float) {
        mDefaultWidth = defaultWidth
        mDefaultCircle.strokeWidth = defaultWidth
        invalidate()
    }

    fun getFilledWidth() = mFilledWidth

    fun setFilledWidth(filledWidth: Float) {
        mFilledWidth = filledWidth
        mFilledCircle.strokeWidth = filledWidth
        mReachedEdgePaint.strokeWidth = filledWidth
        invalidate()
    }
}