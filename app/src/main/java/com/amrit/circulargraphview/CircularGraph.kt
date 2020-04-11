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

/*
 * The circular Graph view
 * This class draws circular type seek-bar on the the canvas
 * It draws two types of circles -
 *  a. default circle
 *  b. Filled circle
 */
class CircularGraph : View {

    private var mMaxLimit: Int = 0
    private var mUsedLimit: Int = 0

    private var mDefaultColor: Int = 0
    private var mFilledColor: Int = 0

    private var mDefaultWidth: Float = 0f
    private var mFilledWidth: Float = 0f

    private lateinit var mDefaultCircle: Paint
    private lateinit var mFilledCircle: Paint
//    private lateinit var mReachedEdgePaint: Paint

    /* Angle to draw in clock-wise direction */
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


    /**
     * This function get the values defined in XML and Initializes them. These attributes will be used to draw the circular shape.
     */
    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircularGraph, defStyleAttr, 0)

        mMaxLimit = typedArray.getInt(R.styleable.CircularGraph_circle_max_limit, DEFAULT_MAX_LIMIT)
        mUsedLimit = typedArray.getInt(R.styleable.CircularGraph_circle_used_limit, DEFAULT_MAX_LIMIT)

        if (mUsedLimit > mMaxLimit) mUsedLimit = mMaxLimit

        mFilledColor = typedArray.getColor(
            R.styleable.CircularGraph_circle_filled_color,
            getColor(context, R.color.filled_circumference_color)
        )
        mDefaultColor = typedArray.getColor(
            R.styleable.CircularGraph_circle_default_color,
            getColor(context, R.color.def_circumference_color)
        )

        mDefaultWidth =
            typedArray.getDimension(
                R.styleable.CircularGraph_circle_default_width,
                resources.getDimension(R.dimen.default_circumference_width)
            )

        mFilledWidth =
            typedArray.getDimension(
                R.styleable.CircularGraph_circle_filled_width,
                mDefaultWidth
            )

        typedArray.recycle()
    }

    /*
     * This function sets the equal padding from all sides of the circle.
     * We're considering the max value among all the default padding values
     */
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

    /**
     * This function creates the Paint objects those will be drawn on canvas
     */
    private fun initializePaintObjects() {

        mDefaultCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        mDefaultCircle.color = mDefaultColor
        mDefaultCircle.style = Paint.Style.STROKE
        mDefaultCircle.strokeWidth = mDefaultWidth

        mFilledCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        mFilledCircle.color = mFilledColor
        mFilledCircle.style = Paint.Style.STROKE
        mFilledCircle.strokeWidth = mFilledWidth

//        mReachedEdgePaint = Paint(mFilledCircle)
//        mReachedEdgePaint.style = Paint.Style.FILL
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        val min = min(width, height)
        setMeasuredDimension(min, min)

        refreshCirclePositions()
    }

    /*
     * This function changes the sweep angle of the circle.
     * The used limit is used to calculate the sweep angle
     */
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

    /*
     * This function sets the max limit and redraws the circular shape
     */
    fun setMaxLimit(maxLimit: Int) {
        mMaxLimit = maxLimit
        refreshCirclePositions()
        invalidate()
    }

    fun getUsedLimit() = mUsedLimit

    /*
     * This function sets the used limit out of max limit and redraws the circular shape
     */
    fun setUsedLimit(usedLimit: Int) {
        mUsedLimit = min(usedLimit, mMaxLimit)
        refreshCirclePositions()
        invalidate()
    }

    fun getDefaultColor() = mDefaultColor

    /*
     * This function sets the default color and redraws the circular shape
     */
    fun setDefaultColor(defaultColor: Int) {
        mDefaultColor = defaultColor
        mDefaultCircle.color = defaultColor
        invalidate()
    }

    fun getFilledColor() = mFilledColor

    /*
     * This function sets the filled color and redraws the circular shape
     */
    fun setFilledColor(filledColor: Int) {
        mFilledColor = filledColor
        mFilledCircle.color = filledColor
//        mReachedEdgePaint.color = filledColor
        invalidate()
    }

    fun getDefaultWidth() = mDefaultWidth

    /*
     * This function sets the default width and redraws the circular shape
     * This value has to be dimension value (value in dp)
     * Use function {resources.getDimension(resource_id)} to set it
     */
    fun setDefaultWidth(defaultWidth: Float) {
        mDefaultWidth = defaultWidth
        mDefaultCircle.strokeWidth = defaultWidth
        invalidate()
    }

    fun getFilledWidth() = mFilledWidth

    /*
     * This function sets the filled width and redraws the circular shape
     * This value has to be dimension value (value in dp)
     * Use function {resources.getDimension(resource_id)} to set it
     */
    fun setFilledWidth(filledWidth: Float) {
        mFilledWidth = filledWidth
        mFilledCircle.strokeWidth = filledWidth
//        mReachedEdgePaint.strokeWidth = filledWidth
        invalidate()
    }

    companion object {
        private const val DEFAULT_MAX_LIMIT = 100
    }
}