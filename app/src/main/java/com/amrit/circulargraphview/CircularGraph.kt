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

    private var mMaxProcess: Int = 0
    private var mCurProcess: Int = 0

    private var mReachedColor: Int = 0
    private var mUnreachedColor: Int = 0

    private var mReachedWidth: Float = 0f
    private var mUnreachedWidth: Float = 0f

    private lateinit var mWheelPaint: Paint
    private lateinit var mReachedPaint: Paint
    private lateinit var mReachedEdgePaint: Paint

    private var mCurAngle: Double = 0.0

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

        mMaxProcess = typedArray.getInt(R.styleable.CircularGraph_circle_max_value, 100)
        mCurProcess = typedArray.getInt(R.styleable.CircularGraph_circle_filled_value, 100)

        if (mCurProcess > mMaxProcess) mCurProcess = mMaxProcess


        mReachedColor = typedArray.getColor(
            R.styleable.CircularGraph_circle_filled_color,
            getColor(context, R.color.def_reached_color)
        )
        mUnreachedColor = typedArray.getColor(
            R.styleable.CircularGraph_circle_default_color,
            getColor(context, R.color.def_wheel_color)
        )

        mUnreachedWidth =
            typedArray.getDimension(
                R.styleable.CircularGraph_circle_filled_width,
                resources.getDimension(R.dimen.def_wheel_width)
            )

        mReachedWidth =
            typedArray.getDimension(
                R.styleable.CircularGraph_circle_default_width,
                mUnreachedWidth
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

        mWheelPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mWheelPaint.color = mUnreachedColor
        mWheelPaint.style = Paint.Style.STROKE
        mWheelPaint.strokeWidth = mUnreachedWidth

        mReachedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mReachedPaint.color = mReachedColor
        mReachedPaint.style = Paint.Style.STROKE
        mReachedPaint.strokeWidth = mReachedWidth

        mReachedEdgePaint = Paint(mReachedPaint)
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
        mCurAngle = mCurProcess.toDouble() / mMaxProcess * 360.0
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val left = paddingLeft + mUnreachedWidth / 2
        val top = paddingTop + mUnreachedWidth / 2
        val right = canvas!!.width - paddingRight - mUnreachedWidth / 2
        val bottom = canvas.height - paddingBottom - mUnreachedWidth / 2
        val centerX = (left + right) / 2
        val centerY = (top + bottom) / 2
        val wheelRadius = (canvas.width - paddingLeft - paddingRight) / 2 - mUnreachedWidth / 2

        canvas.drawCircle(centerX, centerY, wheelRadius, mWheelPaint)

        canvas.drawArc(
            RectF(left, top, right, bottom),
            -90f,
            mCurAngle.toFloat(),
            false,
            mReachedPaint
        )

    }

    fun getMaxProcess() = mMaxProcess

    fun setMaxProcess(maxProcess: Int) {
        mMaxProcess = maxProcess
        refreshCirclePositions()
        invalidate()
    }

    fun getCurProcess() = mCurProcess

    fun setCurProcess(curProcess: Int) {
        mCurProcess = min(curProcess, mMaxProcess)
        refreshCirclePositions()
        invalidate()
    }

    fun getUnreachedColor() = mUnreachedColor

    fun setUnreachedColor(unreachedColor: Int) {
        mUnreachedColor = unreachedColor
        mWheelPaint.color = unreachedColor
        invalidate()
    }

    fun getReachedColor() = mReachedColor

    fun setReachedColor(reachedColor: Int) {
        mReachedColor = reachedColor
        mReachedPaint.color = reachedColor
        mReachedEdgePaint.color = reachedColor
        invalidate()
    }

    fun getUnreachedWidth() = mUnreachedWidth

    fun setUnreachedWidth(unreachedWidth: Float) {
        mUnreachedWidth = unreachedWidth
        mWheelPaint.strokeWidth = unreachedWidth
        invalidate()
    }

    fun getReachedWidth() = mReachedWidth

    fun setReachedWidth(reachedWidth: Float) {
        mReachedWidth = reachedWidth
        mReachedPaint.strokeWidth = reachedWidth
        mReachedEdgePaint.strokeWidth = reachedWidth
        invalidate()
    }
}