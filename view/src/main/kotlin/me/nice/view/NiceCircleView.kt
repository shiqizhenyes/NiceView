package me.nice.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class NiceCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                               defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var circleRadius = 0
    private var circleColor = 0
    private var circlePaint: Paint? = null

    private fun initStyledAttr(styledAttributes: TypedArray) {
        circleRadius = styledAttributes
                .getDimensionPixelSize(R.styleable.NiceCircleView_circleRadius, 1)
        circleColor = styledAttributes.getColor(R.styleable.NiceCircleView_circleColor,
                ContextCompat.getColor(context, R.color.md_green_A500))
    }

    private fun initCirclePaint() {
        circlePaint = Paint()
        circlePaint!!.style = Paint.Style.FILL
        circlePaint!!.color = circleColor
        circlePaint!!.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle((width shr 1).toFloat(), (height shr 1).toFloat(), circleRadius.toFloat(), circlePaint!!)
    }

    fun setCircleColor(circleColor: Int) {
        this.circleColor = circleColor
        circlePaint!!.color = this.circleColor
        invalidate()
    }

    fun setCircleRadius(circleRadius: Int) {
        this.circleRadius = circleRadius
        invalidate()
    }

    init {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NiceCircleView)
        initStyledAttr(styledAttributes)
        initCirclePaint()
    }
}