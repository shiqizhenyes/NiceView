package me.nice.view.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import me.nice.view.R;


/**
 * 地图定位点控件
 */
public class NicePointerView extends View {

    private int outColor;
    private int insideColor;
    private int lineColor;
    private int jumpStyle;
    private int zoomStyle;
    private int DEFAULT_DURING = 1000;
    private boolean waterWave = true;

    private Paint outPaint;
    private Paint insidePaint;
    private Paint linePaint;
    private Paint wavePaint;
    private int outRadius;
    private int inSideRadius;
    private int waveRadius;
    private int centerX;
    private int centerY;
    private int lineHeight;
    private int waterWaveMinRadius;
    private int waterWaveMaxRadius;

    private final double goldenSection = 0.618;


    public NicePointerView(Context context) {
        this(context, null);
    }


    public NicePointerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public NicePointerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NicePointerView, defStyleAttr, defStyleAttr);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.NicePointerView_outRadius) {
                outRadius = typedArray.getDimensionPixelSize(i, 0);
            } else if (attr == R.styleable.NicePointerView_outColor) {
                outColor = typedArray.getColor(i, 0);
            } else if (attr == R.styleable.NicePointerView_insideColor) {
                insideColor = typedArray.getColor(i, 0);
            } else if (attr == R.styleable.NicePointerView_lineHeight) {
                lineHeight = typedArray.getDimensionPixelSize(i, 0);
            } else if (attr == R.styleable.NicePointerView_lineColor) {
                lineColor = typedArray.getColor(i, 0);
            } else if (attr == R.styleable.NicePointerView_waterWave) {
                waterWave = typedArray.getBoolean(i, waterWave);
            } else if (attr == R.styleable.NicePointerView_jumpStyle) {
                jumpStyle = typedArray.getInt(i, context.getResources().getInteger(R.integer.DIDI));
            } else if (attr == R.styleable.NicePointerView_zoomStyle) {
                zoomStyle = typedArray.getInt(i, context.getResources().getInteger(R.integer.DIDI));
            } else if (attr == R.styleable.NicePointerView_waterWaveMinRadius) {
                waterWaveMinRadius = typedArray.getDimensionPixelSize(i, 0);
            } else if (attr == R.styleable.NicePointerView_waterWaveMaxRadius) {
                waterWaveMaxRadius = typedArray.getDimensionPixelSize(i, 0);
            }
        }
        initPaint();
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        outPaint = new Paint();
        insidePaint = new Paint();
        linePaint = new Paint();
        wavePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        outPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        insidePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        wavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(5);
        linePaint.setColor(lineColor);
        outPaint.setColor(outColor);
        insidePaint.setColor(insideColor);
        wavePaint.setColor(outColor);
        wavePaint.setAlpha(100);
    }

    private int lineStarY;
    private int lineEndY;



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (waterWaveMaxRadius == 0) {
            waterWaveMaxRadius = (int) (outRadius * 3 / (1 + goldenSection));
        }
        inSideRadius = outRadius / 4;
        centerX = w / 2;
        centerY = h / 2;
        if (lineHeight == 0) {
            lineHeight = (int) (outRadius * goldenSection);
        }
        lineStarY = centerY - getPaddingTop() - lineHeight;
        lineEndY = centerY;
    }


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
            width = 120;
            height = (int) (width / goldenSection);
        }else if (withMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY){
            height = heightSpecSize;
            width = (int) (height * goldenSection);
        }else if (heightMode == MeasureSpec.AT_MOST && withMode == MeasureSpec.EXACTLY) {
            width = withSpecSize;
            height = (int) (width / goldenSection);
        }else {
            width = withSpecSize;
            height = heightSpecSize;
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLine(canvas);

        canvas.drawCircle(centerX + getPaddingLeft(), centerY - getPaddingTop() - lineHeight,
                outRadius, outPaint);

        canvas.drawCircle(centerX + getPaddingLeft(), centerY - getPaddingTop() - lineHeight,
                inSideRadius, insidePaint);

        drawWave(canvas);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    /**
     * 是否开启水波纹动画
     * @param waterWave
     */
    public void waterWave(boolean waterWave) {
        this.waterWave = waterWave;
    }


    /**
     * 画线
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawLine(centerX + getPaddingLeft(), lineStarY,
                centerX + getPaddingLeft(), lineEndY, linePaint);
    }

    /**
     * 绘制水波纹
     * @param canvas
     */
    private void drawWave(Canvas canvas) {
        canvas.drawCircle(centerX + getPaddingLeft(), lineEndY, waveRadius, wavePaint);
    }


    ValueAnimator outValueAnimator;

    /**
     * 开始外圆动画
     */
    public void startOutAnimation() {
        if (outValueAnimator!=null&&outValueAnimator.isRunning()) {
            return;
        }
        outValueAnimator = ValueAnimator.ofInt(outRadius / 4, outRadius - 10);
        outValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                inSideRadius = (int) animation.getAnimatedValue();
                invalidate();
            }

        });

        outValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
//                startWaveAnimation();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startInsideAnimation();
            }
        });
        ValueAnimator.setFrameDelay(1000);
        outValueAnimator.start();
    }

//    private void startJump() {
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(circleHeigh,circleHeigh - 25, circleHeigh);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                circleHeigh = (int) animation.getAnimatedValue();
//                Log.d("deling", "circleHeigh动画的值 " + String.valueOf(animation.getAnimatedValue()));
//                invalidate();
//            }
//        });
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//            }
//        });
//
//        ValueAnimator.setFrameDelay(1000);
//        valueAnimator.start();
//    }

    /**
     * 开始内圆动画
     */
    public void startInsideAnimation() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(outRadius - 10, outRadius / 4);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                inSideRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        ValueAnimator.setFrameDelay(800);
        valueAnimator.start();

    }


    private ValueAnimator waveValueAnimator;

    /**
     * 开始水波纹动画
     */
    public void startWaveAnimation() {

        waveValueAnimator = ValueAnimator.ofInt(waterWaveMinRadius, waterWaveMaxRadius);

        waveValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveRadius = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        waveValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                waveRadius = waterWaveMinRadius;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                startWaveColorAnimation();
            }
        });
        waveValueAnimator.setDuration(1000);
        waveValueAnimator.start();
    }


    public void cancelWaveAnimation() {
        if (waveValueAnimator != null) {
            waveValueAnimator.cancel();
        }
    }


    private ValueAnimator waveColorAnimation;

    /**
     * 开始水波纹颜色动画
     */
    public void startWaveColorAnimation() {

        waveColorAnimation = ValueAnimator.ofInt(100, 0);

        waveColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                wavePaint.setAlpha((int) animation.getAnimatedValue());
                invalidate();
            }
        });

        waveColorAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                waveRadius = (int) (outRadius * goldenSection);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        waveColorAnimation.setDuration(800);
        waveColorAnimation.start();
    }


    public void cancelWaveColorAnimation() {
        if (waveColorAnimation != null) {
            waveColorAnimation.cancel();
        }
    }


    /**
     * 滴滴风格缩放
     */
    public void startZoomDiDiStyleAnimation() {
        startOutAnimation();
    }


    /**
     * 摩拜风格缩放
     */
    public void startZoomMoBaiStyleAnimation() {
        startInsideAnimation();
    }


    /**
     * 默认使用滴滴风格的动画
     */
    public void startZoomAnimation() {
        if (zoomStyle == getContext().getResources().getInteger(R.integer.DIDI)) {
            startZoomDiDiStyleAnimation();
        } else {
            // TODO: 2019/12/18  
        }
    }


    private ValueAnimator didiJumpAnimator;
    private int jumpHeight = 30;
    private int centerYOld;

    /**
     * 滴滴风格跳动
     */
    public void startJumpDiDiStyleAnimation() {
        cancelJumpDiDiStyleAnimation();
        didiJumpAnimator = ValueAnimator.ofInt(centerY, centerY - jumpHeight , centerYOld);
        didiJumpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerY = (int) animation.getAnimatedValue();
                if (centerY == (centerYOld - jumpHeight)) {
                    startLineJumpAnimation();
                }
                postInvalidate();
            }
        });
        didiJumpAnimator.setDuration(400);
        didiJumpAnimator.start();
    }


    public void cancelJumpDiDiStyleAnimation() {
        if (didiJumpAnimator != null){
            didiJumpAnimator.removeAllUpdateListeners();
            didiJumpAnimator.cancel();
        }
        centerYOld = getHeight() / 2;
        centerY = getHeight() / 2;
    }

    private ValueAnimator lineJumpAnimator;

    private void startLineJumpAnimation() {

        lineJumpAnimator = ValueAnimator.ofInt(lineEndY, lineEndY - jumpHeight, getHeight() / 2);
        lineJumpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lineEndY = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        lineJumpAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (waterWave) {
                    startWaveAnimation();
                }
            }
        });
        lineJumpAnimator.setDuration(200);
        lineJumpAnimator.start();

    }

    /**
     * 通用跳动
     */
    public void startJumpCommonAnimation() {

    }

    public void startJumpAnimation() {
        if (jumpStyle == getResources().getInteger(R.integer.DIDI)) {
            startJumpDiDiStyleAnimation();
        }
    }


}
