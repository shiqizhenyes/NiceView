package me.nice.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class NiceMarkView extends View {


    public NiceMarkView(Context context) {
        super(context);
    }

    public NiceMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NiceMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public NiceMarkView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
