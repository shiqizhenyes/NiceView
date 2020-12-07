package me.nice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import me.nice.view.R;

public class NiceCircleView extends View {


    private int circleRadius;
    private int circleColor;
    private Paint circlePaint;

    public NiceCircleView(Context context) {
        this(context, null);
    }

    public NiceCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NiceCircleView);
        initStyledAttr(styledAttributes);
        initCirclePaint();
    }

    private void initStyledAttr(TypedArray styledAttributes) {
        circleRadius = styledAttributes
                .getDimensionPixelSize(R.styleable.NiceCircleView_circleRadius, 1);
        circleColor = styledAttributes.getColor(R.styleable.NiceCircleView_circleColor,
                ContextCompat.getColor(getContext(), R.color.md_green_A500));
    }

    private void initCirclePaint() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, circleRadius, circlePaint);
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        circlePaint.setColor(this.circleColor);
        invalidate();
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        invalidate();
    }
}
