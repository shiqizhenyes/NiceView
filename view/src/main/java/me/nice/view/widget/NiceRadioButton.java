package me.nice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import me.nice.view.R;
import me.nice.view.util.DensityUtil;

public class NiceRadioButton extends View {

    private int defaultRadius = 4;
    private int defaultBorderSize = 2;

    private int borderSpace = 5;
    private int textSpace = 16;

    private int radius;
    private int borderSize;
    private int borderColor;
    private int buttonColor;
    private String text;
    private int textColor;
    private int textSize;
    private boolean check;

    private int textLength;

    private int primaryColor;

    private Paint borderPaint;
    private Paint buttonPaint;
    private Paint textPaint;

    private RectF rect = new RectF();
    private int buttonSide;

    public NiceRadioButton(Context context) {
        this(context, null);
    }

    public NiceRadioButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NiceRadioButton, defStyleAttr, 0);
        initAttrs(typedArray);
        initPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NiceRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NiceRadioButton, defStyleAttr, defStyleRes);
        initAttrs(typedArray);
        initPaint();
    }


    private void initAttrs(TypedArray typedArray) {
        borderSize = typedArray.getDimensionPixelSize(R.styleable.NiceRadioButton_nBorderSize, 5);
        borderColor = typedArray.getColor(R.styleable.NiceRadioButton_nBorderColor, primaryColor);
        buttonColor = typedArray.getColor(R.styleable.NiceRadioButton_nButtonColor, primaryColor);
        text = typedArray.getString(R.styleable.NiceRadioButton_nText);
        textColor = typedArray.getColor(R.styleable.NiceRadioButton_nTextColor, Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.NiceRadioButton_nTextSize, 10);
        check = typedArray.getBoolean(R.styleable.NiceRadioButton_nCheck, false);
        textLength = text == null ? 0 : text.length();
        typedArray.recycle();
        primaryColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        DensityUtil.getInstance().setDensity(getContext().getResources().getDisplayMetrics().density)
                .setScaledDensity(getContext().getResources().getDisplayMetrics().scaledDensity);
        radius = DensityUtil.dip2px(defaultRadius);
//        borderSize = DensityUtil.dip2px(defaultBorderSize);
    }

    private void initPaint() {
        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderSize);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);

        buttonPaint = new Paint();
        buttonPaint.setColor(buttonColor);
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
    }

    private int width;
    private int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int maxSide = Math.max(radius * 2 + borderSize * 2
                    + DensityUtil.dip2px(borderSpace) * 2, textSize);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                width = radius * 2 + borderSize * 2 + DensityUtil.dip2px(borderSpace) * 2 +
                        textLength * textSize + getPaddingStart() + getPaddingEnd();
            } else {
                width = radius * 2 + borderSize * 2 + DensityUtil.dip2px(borderSpace) * 2 +
                        textLength * textSize + getPaddingLeft() + getPaddingRight();
            }
            height = maxSide + getPaddingTop() + getPaddingBottom();
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            radius = heightSpecSize / 2 - borderSize - DensityUtil.dip2px(borderSpace)
                    - ((getPaddingTop() + getPaddingBottom()) / 2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                width = radius * 2 + borderSize * 2 + DensityUtil.dip2px(borderSpace) * 2 +
                        textSpace + textSize * textLength + getPaddingStart() + getPaddingEnd();
            } else {
                width = radius * 2 + borderSize * 2 + DensityUtil.dip2px(borderSpace) * 2 +
                        textSpace + textSize * textLength + getPaddingLeft() + getPaddingRight();
            }
            height = heightSpecSize;
        } else if (heightMode == MeasureSpec.AT_MOST && widthMode == MeasureSpec.EXACTLY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                radius = (widthSpecSize - textSize * textLength - getPaddingStart() - getPaddingEnd()
                        - borderSize * 2 - borderSpace * 2) / 2;
            } else {
                radius = (widthSpecSize - textSize * textLength - getPaddingLeft() - getPaddingRight()
                        - borderSize * 2 - borderSpace * 2) / 2;
            }
            height = Math.max(textSize, radius * 2 + borderSize * 2 + borderSpace * 2) +
            getPaddingTop() + getPaddingBottom();
        } else {
            int shortSide = Math.max(heightSpecSize, widthSpecSize);
            radius = (shortSide >> 1) - borderSize - DensityUtil.dip2px(borderSpace) -
                    ((getPaddingTop() + getPaddingBottom()) / 2);
            width = widthSpecSize;
            height = heightSpecSize;
        }
        buttonSide = radius * 2 + borderSpace * 2 + borderSize * 2;
        initRectF(buttonSide);
        setMeasuredDimension(width, height);
    }

    private void initRectF(int buttonSide) {
        rect.set(0,
                buttonSide >> 1,
                buttonSide + borderSize - DensityUtil.dip2px(borderSpace),
                buttonSide - borderSize - DensityUtil.dip2px(borderSpace));
//        rect.set(width >> 1 - radius,
//                width >> 1 - radius - DensityUtil.dip2px(borderSpace),
//                width - DensityUtil.dip2px(borderSpace),
//                height - DensityUtil.dip2px(borderSpace));

//        rect.set(width - textSize * textLength - getPaddingEnd() - textSpace - radius * 2 -
//                        DensityUtil.dip2px(borderSpace * 2),
//                (height >> 1) - radius - DensityUtil.dip2px(borderSpace),
//                getPaddingStart() + radius * 2 +
//                        borderSize * 2 + DensityUtil.dip2px(borderSpace * 2) - borderSize,
//                (height >> 1) + radius + DensityUtil.dip2px(borderSpace));
    }

    private float startAngle = 0;
    private float sweepAngle = 360;

    private void drawBorder(Canvas canvas) {
        canvas.drawRect(rect, borderPaint);
        canvas.drawArc(rect, startAngle, sweepAngle,false, borderPaint);
    }

    private void drawButton(Canvas canvas) {
        canvas.drawCircle(width >> 1, height >> 1, radius, buttonPaint);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText(text, radius * 2 + borderSize * 2 + DensityUtil.dip2px(borderSpace) * 2,
                height, textPaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);
        drawBorder(canvas);
        drawText(canvas);
    }


}
