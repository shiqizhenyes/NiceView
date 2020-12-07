package me.nice.view.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import me.nice.view.R;

import static android.animation.ValueAnimator.INFINITE;

public class NiceCircleRippleView extends View {

    private TypedArray styledAttrArray;

    private int circleRadius;
    private int circleColor;
    private int circleRippleDuring;
    private int circleRippleRadius;
    private int circleRippleAnimatorRadius;

    private Paint circlePaint;
    private Paint circleRipplePaint;

    public NiceCircleRippleView(Context context) {
        this(context, null);
    }

    public NiceCircleRippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceCircleRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        styledAttrArray = context.obtainStyledAttributes(attrs, R.styleable.NiceCircleRippleView);
        initStyledAttr();
        initCirclePaint();
        initCircleRipplePaint();
        initRippleAnimator();
//        startRippleAnimator();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NiceCircleRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        styledAttrArray = context.obtainStyledAttributes(attrs, R.styleable.NiceCircleRippleView,
                defStyleAttr, defStyleRes);
        initStyledAttr();
        initCirclePaint();
        initCircleRipplePaint();
        initRippleAnimator();
    }

    private void initStyledAttr() {
        circleRadius = styledAttrArray.getDimensionPixelSize(R.styleable.NiceCircleRippleView_circleRadius, 0);
        circleColor = styledAttrArray.getColor(R.styleable.NiceCircleRippleView_circleColor, 0);
        circleRippleDuring = styledAttrArray.getInteger(R.styleable.NiceCircleRippleView_circleRippleDuring, 0);
        circleRippleRadius = styledAttrArray.getDimensionPixelSize(R.styleable.NiceCircleRippleView_circleRippleRadius, 0);
        circleRippleAnimatorRadius = circleRadius;
    }


    private void initCirclePaint() {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
    }


    private void initCircleRipplePaint() {
        circleRipplePaint = new Paint();
        circleRipplePaint.setColor(circleColor);
        circleRipplePaint.setStyle(Paint.Style.FILL);
        circleRipplePaint.setAntiAlias(true);
    }


    private int halfWidth;
    private int halfHeight;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                width = circleRippleRadius * 2 + getPaddingStart()
                        + getPaddingEnd();
            } else {
                width = circleRippleRadius * 2 + getPaddingLeft()
                        + getPaddingRight();
            }
            height = circleRippleRadius * 2 + getPaddingTop() + getPaddingBottom();
        }else if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY){
            height = heightSpecSize;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                width = circleRippleRadius * 2 + getPaddingStart()
                        + getPaddingEnd();
            } else {
                width = circleRippleRadius * 2 + getPaddingLeft()
                        + getPaddingRight();
            }
        }else if (heightMode == MeasureSpec.AT_MOST && withMode == MeasureSpec.EXACTLY) {
            width = withSpecSize;
            height = circleRippleRadius * 2 + getPaddingTop() + getPaddingBottom();
        }else {
            width = withSpecSize;
            height = heightSpecSize;
        }
        setMeasuredDimension(width, height);
        halfWidth = width >> 1;
        halfHeight = height >> 1;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(halfWidth, halfHeight, circleRadius, circlePaint);
        canvas.drawCircle(halfWidth, halfHeight, circleRippleAnimatorRadius, circleRipplePaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelRippleAnimator();
    }

    private ValueAnimator zoomAnimator;
    private ValueAnimator alphaAnimator;

    private void initRippleAnimator() {
        zoomAnimator = ValueAnimator
                .ofInt(circleRadius, circleRippleRadius);
        zoomAnimator.setRepeatCount(INFINITE);
        zoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleRippleAnimatorRadius = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        alphaAnimator = ValueAnimator
                .ofInt(100, 0);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleRipplePaint.setAlpha((Integer) animation.getAnimatedValue());
            }
        });
        alphaAnimator.setRepeatCount(INFINITE);
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        invalidate();
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        circlePaint.setColor(this.circleColor);
        invalidate();
    }

    public void setCircleRippleRadius(int circleRippleRadius) {
        this.circleRippleRadius = circleRippleRadius;
    }

    public void setCircleRippleDuring(int circleRippleDuring) {
        this.circleRippleDuring = circleRippleDuring;
    }

    private AnimatorSet animatorSet;

    public void startRippleAnimator() {
        initRippleAnimator();
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(circleRippleDuring);
        animatorSet.playTogether(zoomAnimator, alphaAnimator);
        animatorSet.setInterpolator(new LinearOutSlowInInterpolator());
        animatorSet.start();
    }


    public void cancelRippleAnimator() {
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }

    }


}
