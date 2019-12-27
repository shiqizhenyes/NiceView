package me.nice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.nice.view.R;

public class NiceBubbleLayout extends FrameLayout {


    private final String tag = NiceBubbleLayout.class.getSimpleName();

    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM = 4;

    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Direction {
    }

    /**
     * 圆角大小
     */
    private int mRadius;

    /**
     * 三角形的方向
     */
    @Direction
    private int mDirection;

    /**
     * 三角形的底边中心点
     */
    private Point mDatumPoint;

    /**
     * 三角形位置偏移量(默认居中)
     */
    private int mOffset;

    private Paint mBorderPaint;

    private Path mPath;

    private RectF mRect;

    private int bubbleHOffsetStart;
    private int bubbleHOffsetEnd;

    private int bubbleVOffsetBottom;
    private int bubbleVOffsetTop;



    public NiceBubbleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.NiceBubbleLayout);

        //背景颜色
        int backGroundColor = ta.getColor(R.styleable.NiceBubbleLayout_backgroundColor, Color.WHITE);

        //阴影颜色
        int shadowColor = ta.getColor(R.styleable.NiceBubbleLayout_shadowColor,
                ContextCompat.getColor(context, R.color.md_black_alpha_50));

        int defShadowSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                4, getResources().getDisplayMetrics());
        //阴影尺寸
        int shadowSize = ta.getDimensionPixelSize(R.styleable.NiceBubbleLayout_shadowSize, defShadowSize);

        mRadius = ta.getDimensionPixelSize(R.styleable.NiceBubbleLayout_radius, 0);
        //三角形方向
        mDirection = ta.getInt(R.styleable.NiceBubbleLayout_direction, BOTTOM);

        mOffset = ta.getDimensionPixelOffset(R.styleable.NiceBubbleLayout_offset, 0);

        bubbleHOffsetStart = ta.getDimensionPixelSize(R.styleable.NiceBubbleLayout_bubbleHOffsetStart, 0);
        bubbleHOffsetEnd = ta.getDimensionPixelSize(R.styleable.NiceBubbleLayout_bubbleHOffsetEnd, 0);

        bubbleVOffsetTop = ta.getDimensionPixelSize(R.styleable.NiceBubbleLayout_bubbleVOffsetTop, 0);
        bubbleVOffsetBottom = ta.getDimensionPixelSize(R.styleable.NiceBubbleLayout_bubbleVOffsetBottom, 0);

        ta.recycle();
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(backGroundColor);
        mBorderPaint.setShadowLayer(shadowSize, 0, 0, shadowColor);

        mPath = new Path();
        mRect = new RectF();
        mDatumPoint = new Point();

        setWillNotDraw(false);
//        //关闭硬件加速
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            switch (mDirection) {
                case LEFT:
                    drawLeftTriangle(canvas);
                    break;
                case TOP:
                    drawTopTriangle(canvas);
                    break;
                case RIGHT:
                    drawRightTriangle(canvas);
                    break;
                case BOTTOM:
                    Log.d(tag, " onDraw ");
                    drawBottomTriangle(canvas);
                    break;
            }
    }

    private void drawLeftTriangle(Canvas canvas) {
        int triangularLength = getPaddingLeft();
        if (triangularLength == 0) {
            return;
        }
        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CCW);
        mPath.moveTo(mDatumPoint.x, mDatumPoint.y - (triangularLength >> 1));
        mPath.lineTo(mDatumPoint.x - (triangularLength >> 1), mDatumPoint.y);
        mPath.lineTo(mDatumPoint.x, mDatumPoint.y + (triangularLength >> 1));
        mPath.close();
        canvas.drawPath(mPath, mBorderPaint);
    }

    private void drawTopTriangle(Canvas canvas) {
        int triangularLength = getPaddingTop();
        if (triangularLength == 0) {
            return;
        }

        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CCW);
        mPath.moveTo(mDatumPoint.x + (triangularLength >> 1), mDatumPoint.y);
        mPath.lineTo(mDatumPoint.x, mDatumPoint.y - (triangularLength >> 1));
        mPath.lineTo(mDatumPoint.x - (triangularLength >> 1), mDatumPoint.y);
        mPath.close();
        canvas.drawPath(mPath, mBorderPaint);
    }

    private void drawRightTriangle(Canvas canvas) {
        int triangularLength = getPaddingRight();
        if (triangularLength == 0) {
            return;
        }
        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CCW);
        mPath.moveTo(mDatumPoint.x, mDatumPoint.y - (triangularLength >> 1));
        mPath.lineTo(mDatumPoint.x + (triangularLength >> 1), mDatumPoint.y);
        mPath.lineTo(mDatumPoint.x, mDatumPoint.y + (triangularLength >> 1));
        mPath.close();
        canvas.drawPath(mPath, mBorderPaint);
    }

    private void drawBottomTriangle(Canvas canvas) {
        int triangularLength = getPaddingBottom();
        if (triangularLength == 0) {
            return;
        }
        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CCW);
        mPath.moveTo(mRect.left + halfBubbleWidth + (triangularLength >> 1), mRect.bottom);
        mPath.lineTo(mRect.left + halfBubbleWidth, mRect.bottom + (triangularLength >> 1));
        mPath.lineTo(mRect.left + halfBubbleWidth - (triangularLength >> 1), mRect.bottom);
        mPath.close();
        canvas.drawPath(mPath, mBorderPaint);
    }

    private int halfBubbleWidth;
    private int halfBubbleHeight;

    private void initRect() {
        mRect.left = (getWidth() >> 1) - halfBubbleWidth + bubbleHOffsetStart - bubbleHOffsetEnd;
        mRect.top = (getHeight() >> 1) - halfBubbleHeight + bubbleVOffsetTop - bubbleVOffsetBottom;
        mRect.right = (getWidth() >> 1) + halfBubbleWidth + bubbleHOffsetStart - bubbleHOffsetEnd;
        mRect.bottom = (getHeight() >> 1) + halfBubbleHeight + bubbleVOffsetTop - bubbleVOffsetBottom;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(tag, " onSizeChanged w " + w + " h " + h );
        int childCount = this.getChildCount();
        int mBubbleWidth = 0;
        int mBubbleHeight = 0;
        for (int i = 0; i < childCount; i++) {
            int childWidth = this.getChildAt(i).getMeasuredWidth();
            int childHeight = this.getChildAt(i).getMeasuredHeight();
            Log.d(tag , " childWidth " + childWidth);
            if (childWidth > mBubbleWidth) {
                mBubbleWidth = childWidth;
            }
            if (childHeight > mBubbleHeight) {
                mBubbleHeight = childHeight;
            }
        }
        halfBubbleWidth = mBubbleWidth >> 1;
        halfBubbleHeight = mBubbleHeight >> 1;
        setMeasuredDimension(mBubbleWidth, mBubbleHeight);
        initRect();
        switch (mDirection) {
            case LEFT:
                mDatumPoint.x = getPaddingLeft();
                mDatumPoint.y = h / 2;
                break;
            case TOP:
                mDatumPoint.x = w / 2;
                mDatumPoint.y = getPaddingTop();
                break;
            case RIGHT:
                mDatumPoint.x = w - getPaddingRight();
                mDatumPoint.y = h / 2;
                break;
            case BOTTOM:
                mDatumPoint.x = w / 2;
                mDatumPoint.y = h - getPaddingBottom();
                break;
        }
        if (mOffset != 0) {
            applyOffset();
        }
        Log.d(tag, " mBubbleWidth " + mBubbleWidth + " mBubbleHeight " + mBubbleHeight);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int withSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(tag, " onMeasure  widthMeasureSpec " + withSpecSize + " heightMeasureSpec " + heightSpecSize);
    }

    /**
     * 设置三角形偏移位置
     *
     * @param offset 偏移量
     */
    public void setTriangleOffset(int offset) {
        this.mOffset = offset;
        applyOffset();
        invalidate();
    }

    private void applyOffset() {
        switch (mDirection) {
            case LEFT:
            case RIGHT:
                mDatumPoint.y += mOffset;
                break;
            case TOP:
            case BOTTOM:
                mDatumPoint.x += mOffset;
                break;
        }
    }

}
