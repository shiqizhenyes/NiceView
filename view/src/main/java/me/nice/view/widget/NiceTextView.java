package me.nice.view.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import me.nice.view.R;

public class NiceTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint strokePaint;

    private int strokeSize;
    private int strokeColor;

    private TextView outlineTextView = null;

    public NiceTextView(Context context) {
        this(context, null);
    }

    public NiceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        outlineTextView = new TextView(context, attrs, defStyle);
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NiceTextView);
        initStyledAttr(styledAttributes);
    }

    private void initStyledAttr(TypedArray styledAttributes) {
        strokeColor = styledAttributes.getColor(R.styleable.NiceTextView_niceStrokeColor, 0);
        strokeSize = styledAttributes.getDimensionPixelSize(R.styleable.NiceTextView_niceStrokeSize, 0);
    }

    @Override
    public void setLayoutParams (ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        outlineTextView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        CharSequence outlineText = outlineTextView.getText();
        if (outlineText == null || !outlineText.equals(this.getText())) {
            outlineTextView.setText(getText());
            outlineTextView.setTypeface(getTypeface());
            postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            outlineTextView.layout(left, top, right, bottom);
        }
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (strokePaint == null) {
            strokePaint = new TextPaint();
        }
        TextPaint paint = getPaint();
        strokePaint.setTextSize(paint.getTextSize());
        strokePaint.setTypeface(getTypeface());
        strokePaint.setFlags(paint.getFlags());
        strokePaint.setAlpha(paint.getAlpha());
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(strokeSize);
        String text = getText().toString();
        canvas.drawText(text, (getPaddingLeft() >> 1) +
                (getWidth() - strokePaint.measureText(text)) / 2 - (getPaddingRight() >> 1),
                getBaseline(), strokePaint);
        super.onDraw(canvas);
    }
}
