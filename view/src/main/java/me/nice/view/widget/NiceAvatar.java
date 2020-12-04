package me.nice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.nice.view.R;
import me.nice.view.util.DensityUtil;

public class NiceAvatar extends ViewGroup {

    private int nCount;
    private int nRadius;
    private int nBorderSize;
    private int nBorderColor;

    private int defaultBorderWidth;
    private int defaultRadius;
    private int defaultColor;

    public NiceAvatar(Context context) {
        this(context, null);
    }

    public NiceAvatar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceAvatar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NiceAvatar, defStyleAttr, 0);
        initAttrs(typedArray);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NiceAvatar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NiceAvatar, defStyleAttr, defStyleRes);
        initAttrs(typedArray);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void initAttrs(TypedArray typedArray) {
        defaultColor = ContextCompat.getColor(getContext(), R.color.md_red_800);
        defaultRadius = DensityUtil.dip2px(10);
        defaultBorderWidth = DensityUtil.dip2px(4);
        nCount = typedArray.getInteger(R.styleable.NiceAvatar_nCount, 1);
        nRadius = typedArray.getDimensionPixelSize(R.styleable.NiceAvatar_nRadius, defaultRadius);
        nBorderSize = typedArray.getDimensionPixelSize(R.styleable.NiceAvatar_nBorderSize, defaultBorderWidth);
        nBorderColor = typedArray.getColor(R.styleable.NiceAvatar_nBorderColor, defaultColor);
        addImage();
    }


    private void addImage() {
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getResources().getDrawable(R.drawable.a));
        drawables.add(getResources().getDrawable(R.drawable.b));
        drawables.add(getResources().getDrawable(R.drawable.c));
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawables.get(0));
        addView(imageView);
//            imageView.setX(imageView.getWidth() >> 1);
//            addView(imageView);
//        for (int i = 0; i < 2; i++) {
//            ImageView imageView = new ImageView(getContext());
//            imageView.setImageDrawable(drawables.get(i));
////            imageView.setX(imageView.getWidth() >> 1);
//            addView(imageView);
//        }
    }

}
