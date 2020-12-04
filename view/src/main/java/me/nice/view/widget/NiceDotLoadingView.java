package me.nice.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.nice.view.R;

public class NiceDotLoadingView extends View {

    private static final int DEFAULT_RADIUS = 5; //px
    private static final int DEFAULT_COUNT = 3;
    private static final int DEFAULT_SPACE = 2;

    private TypedArray styledAttrArray;
    private int dotRadius;
    private int dotColor;
    private int dotAnimationColor;
    private int dotCount;
    private int dotSpace;
    private Paint dotPaint;

    private float circleX;
    private float circleY;
    private float scaleFloat = 1;

    private float[] scaleFloats;


    private boolean mIsStarted = false;

    private ArrayList<ValueAnimator> mAnimators;

    private Map<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();

    public NiceDotLoadingView(Context context) {
        this(context, null);
    }

    public NiceDotLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceDotLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        styledAttrArray = context.obtainStyledAttributes(attrs, R.styleable.NiceDotLoadingView);
        initStyledAttr();
        initDotPaint();
        initCirclePosition();
        initScale();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NiceDotLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        styledAttrArray = context.obtainStyledAttributes(attrs, R.styleable.NiceDotLoadingView,
                defStyleAttr, defStyleRes);
        initStyledAttr();
        initDotPaint();
        initCirclePosition();
        initScale();
    }

    /**
     * 初始化属性
     */
    private void initStyledAttr() {
        dotRadius = styledAttrArray
                .getDimensionPixelSize(R.styleable.NiceDotLoadingView_dotRadius, DEFAULT_RADIUS);
        dotColor = styledAttrArray.getColor(R.styleable.NiceDotLoadingView_dotColor,
                ContextCompat.getColor(getContext(), R.color.md_grey_200));
        dotAnimationColor = styledAttrArray.getColor(R.styleable.NiceDotLoadingView_dotAnimationColor,
                ContextCompat.getColor(getContext(), R.color.md_grey_200));
        dotCount = styledAttrArray.getInteger(R.styleable.NiceDotLoadingView_dotCount, DEFAULT_COUNT);
        dotSpace = styledAttrArray.getDimensionPixelSize(R.styleable.NiceDotLoadingView_dotSpace, DEFAULT_SPACE);
    }

    private void initDotPaint() {
        dotPaint = new Paint();
        dotPaint.setColor(dotColor);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setAntiAlias(true);
    }

    private void initCirclePosition() {
        circleX = dotRadius + ((getPaddingLeft() + getPaddingRight()) >> 1);
        circleY = dotRadius + ((getPaddingTop() + getPaddingBottom()) >> 1);
    }


    private void initScale() {
        scaleFloats = new float[dotCount];
        for (int i = 0; i < dotCount ; i++) {
            scaleFloats[i] = 1.0f;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                width = dotRadius * 2 * dotCount + dotSpace * (dotCount - 1) + getPaddingStart()
                        + getPaddingEnd();
            } else {
                width = dotRadius * 2 * dotCount + dotSpace * (dotCount - 1) + getPaddingLeft()
                        + getPaddingRight();
            }
            height = dotRadius * 2 + getPaddingTop() + getPaddingBottom();
        }else if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY){
            height = heightSpecSize;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                width = dotRadius * 2 * dotCount + dotSpace * (dotCount - 1) + getPaddingStart()
                        + getPaddingEnd();
            } else {
                width = dotRadius * 2 * dotCount + dotSpace * (dotCount - 1) + getPaddingLeft()
                        + getPaddingRight();
            }
        }else if (heightMode == MeasureSpec.AT_MOST && withMode == MeasureSpec.EXACTLY) {
            width = withSpecSize;
            height = dotRadius * 2 + getPaddingTop() + getPaddingBottom();
        }else {
            width = withSpecSize;
            height = heightSpecSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimators != null) for (int i = 0; i < mAnimators.size(); i++) {
            mAnimators.get(i).cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < dotCount; i++) {
            canvas.save();
            float translateX = circleX + dotSpace * i +
                    dotRadius * 2 * i;
            canvas.translate(translateX, circleY);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, dotRadius, dotPaint);
            canvas.restore();
        }
    }

    private boolean isStarted() {
        return mIsStarted;
    }

    private void createAnimators() {
        mAnimators = new ArrayList<>();
        int[] delays = new int[dotCount];
        for (int i = 0; i < dotCount; i++) {
            delays[i] = 120 * (i + 1);
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnim.setStartDelay(delays[i]);
            mUpdateListeners.put(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
//                    scaleFloat = (float) animation.getAnimatedValue();
                    postInvalidate();
                }

            });
            mAnimators.add(scaleAnim);
        }
    }

    public void startAnim() {
        if (mAnimators == null) createAnimators();
        if (mAnimators == null) return;
        if (isStarted()) return;
        for (int i = 0; i < mAnimators.size(); i++) {
            ValueAnimator animator = mAnimators.get(i);
            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
            if (updateListener != null) {
                animator.addUpdateListener(updateListener);
            }
            animator.start();
        }
        mIsStarted = true;
    }

    public void stopAnim() {
        if (mAnimators != null && mIsStarted) {
            mIsStarted = false;
            for (ValueAnimator animator : mAnimators) {
                if (animator != null) {
                    animator.removeAllUpdateListeners();
                    animator.end();
                }
            }
            initScale();
            postInvalidate();
        }
    }


}
