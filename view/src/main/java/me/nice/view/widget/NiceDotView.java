package me.nice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import me.nice.view.R;

public class NiceDotView extends AppCompatTextView {

    private Paint mPaint;
    private int mDotPadding = DEFAULT_PADDING;

    private static final String DEFAULT_OVER_TEXT = "...";
    private static final int DEFAULT_PADDING = 3;
    private static final String DEFAULT_TEXT = "88";


    public NiceDotView(Context context) {
        super(context);
    }

    public NiceDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceDotView, 0, 0);
        int color = typedArray.getColor(R.styleable.NiceDotView_dotColor, Color.RED);
        float density = getResources().getDisplayMetrics().density;
//        mDotPadding = typedArray.getDimensionPixelSize(R.styleable.NiceDotView_dotPadding,
//                (int) (DEFAULT_PADDING * density));
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);

        setPadding(0, 0, 0, 0);
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int radius = getWidth()/2;
        canvas.drawCircle(radius, radius, radius, mPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        if (getText().length() > 0) {
            width = Math.max(getTextWidth(), getTextHeight());
        }
        width += mDotPadding * 2;
        int newMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        super.onMeasure(newMeasureSpec, newMeasureSpec);
    }

    private int getTextWidth() {
        return (int) getPaint().measureText(DEFAULT_TEXT);
    }

    private int getTextHeight() {
        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text.length() > 0) {
            final String testStr = text.toString();
            if (!isNumber(testStr) || !isLegalNumber(Integer.valueOf(testStr))) {
                text = DEFAULT_OVER_TEXT;
            }
        }
        super.setText(text, type);
    }

    private boolean isNumber(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.matches("\\d+");
    }

    private boolean isLegalNumber(int number) {
        return number >= 0 && number < 100;
    }

}
