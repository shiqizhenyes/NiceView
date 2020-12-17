package me.nice.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import me.nice.view.util.DensityUtil
import java.time.Duration

class NiceCameraButton @JvmOverloads constructor(context: Context,
                                                     attrs: AttributeSet? = null,
                                                     defStyleAttr: Int = 0,
                                                     defStyleRes: Int = 0):
        View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val CAMERA = 1
        private const val VIDEO = 2
    }

    @IntDef(CAMERA, VIDEO)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Direction

    private var nRadius: Int = 0
    private var nBorderSize: Int = 0
    private var nBorderColor: Int = 0
    private var nButtonColor: Int = 0
    private var nButtonStyle: Int = 0
    private var nBorderPadding: Int = 16

    private fun initStyledAttr(styledAttributes: TypedArray) {
        nRadius = styledAttributes.getDimensionPixelSize(R.styleable.NiceCameraButton_nRadius,
                DensityUtil.dip2px(16F))
        nBorderSize = styledAttributes.getDimensionPixelSize(R.styleable.NiceCameraButton_nBorderSize,
                DensityUtil.dip2px(4F))
        nBorderColor = styledAttributes.getColor(R.styleable.NiceCameraButton_nBorderColor,
                ContextCompat.getColor(context, R.color.md_white))
        nButtonColor = styledAttributes.getColor(R.styleable.NiceCameraButton_nButtonColor,
                ContextCompat.getColor(context, R.color.md_white))
        nButtonStyle = styledAttributes.getInt(R.styleable.NiceCameraButton_nButtonStyle, CAMERA)
    }

    private lateinit var borderPaint: Paint

    private fun initBorderPaint() {
        borderPaint = Paint()
        borderPaint.color = nBorderColor
        borderPaint.isAntiAlias = true
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = nBorderSize.toFloat()
    }

    private lateinit var buttonPaint: Paint

    private fun initButtonPaint() {
        buttonPaint = Paint()
        buttonPaint.color = nButtonColor
        buttonPaint.isAntiAlias = true
    }

    private var inSideCircleRadius: Int = 0
    private var borderRadius: Int = 0

    private fun initRadius() {
        inSideCircleRadius = nRadius
        borderRadius = inSideCircleRadius + nBorderPadding
    }

    init {
//        DensityUtil.getInstance().setDensity(context..)
//                .setScaledDensity();
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceCameraButton,
                defStyleAttr, defStyleRes)
        initStyledAttr(typedArray)
        initBorderPaint()
        initButtonPaint()
        initRadius()
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val withMode = MeasureSpec.getMode(widthMeasureSpec)
        val withSpecSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            width = nRadius * 2 + nBorderSize * 2 + nBorderPadding * 2
            height = width
        } else if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            height = heightSpecSize
            width = nRadius * 2 + nBorderSize * 2 + nBorderPadding * 2
        } else if (heightMode == MeasureSpec.AT_MOST && withMode == MeasureSpec.EXACTLY) {
            width = withSpecSize
            height = nRadius * 2 + nBorderSize * 2 + nBorderPadding * 2
        } else {
            width = withSpecSize
            height = heightSpecSize
        }
        setMeasuredDimension(width, height)
    }

    private fun drawButtonAndBorder(canvas: Canvas?) {
        canvas?.save()
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
                inSideCircleRadius.toFloat(), buttonPaint)
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(),
                (borderRadius).toFloat(), borderPaint)
        canvas?.restore()
    }

    private val zoomInsideCircleAnimation = ValueAnimator.ofInt(inSideCircleRadius, (inSideCircleRadius * 0.8).toInt())

    private fun startZoomInsideCircleAnimation() {
        zoomInsideCircleAnimation.addUpdateListener {
            inSideCircleRadius = it.animatedValue as Int
            postInvalidate()
        }
        zoomInsideCircleAnimation.duration = 200
        zoomInsideCircleAnimation.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startZoomInsideCircleAnimation()
            }
            MotionEvent.ACTION_UP -> {
                zoomInsideCircleAnimation.reverse()
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawButtonAndBorder(canvas)
    }

}