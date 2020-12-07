package me.nice.view.widget.wheel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.nice.view.R;
import me.nice.view.adapter.NiceWheelAdapter;
import me.nice.view.helper.DateHelper;
import me.nice.view.helper.LocaleHelper;
import me.nice.view.inter.OnItemSelectedListener;
import me.nice.view.inter.OnNiceWheelChangeListener;
import me.nice.view.inter.OnScrollFinishedListener;

import static me.nice.view.inter.SingleDateAndTimeConstants.*;
import static me.nice.view.widget.wheel.NiceWheelPicker.FORMAT;
import static me.nice.view.widget.wheel.NiceWheelPicker.SCROLL_STATE_IDLE;


public class NiceWheelMinutePicker extends LinearLayout {

    private final String TAG = NiceWheelMinutePicker.class.getSimpleName();

    protected boolean displayDefaultText = false;

    private String defaultText;


    private Date minDate;
    private Date maxDate;
    private Date defaultDate;
    private int stepMinutes;
    private int minMinute;
    private int maxMinute;


    private Locale customLocale;

    private OnMinuteChangedListener onMinuteChangedListener;

    private OnFinishedLoopListener onFinishedLoopListener;

    private OnScrollFinishedListener onScrollFinishedListener;


    private NiceWheelPicker<String> niceWheelPicker;

    private NiceWheelAdapter adapter;


    public NiceWheelMinutePicker(Context context) {
        super(context);
    }

    public NiceWheelMinutePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAdapterValues();
    }


    protected void init(Context context) {
        stepMinutes = STEP_MINUTES_DEFAULT;
        defaultText = initDefault();
        View rootView = inflate(context, R.layout.nice_minute_picker_layout, this);
        niceWheelPicker = rootView.findViewById(R.id.niceWheelMinutePicker);
        niceWheelPicker.setOnItemSelectedListener(onItemSelectedListener);
        adapter = new NiceWheelAdapter();
    }


    protected String initDefault() {
        if (displayDefaultText) {
            return LocaleHelper.getLocalizedString(getContext(), R.string.default_text);
        }else {

            if (defaultDate == null) {

                return getFormattedValue(DateHelper.getMinuteOf(new Date(), stepMinutes));

            }else {

            }

        }
        return "";
    }


    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getDefaultTextPosition() {
        return adapter.getData().indexOf(LocaleHelper
                .getLocalizedString(getContext(), R.string.default_text));
    }


    public String getDefaultText() {
        return defaultText;
    }


    public void setCustomLocale(Locale customLocale) {
        this.customLocale = customLocale;
    }


    public void setStepMinutes(int stepMinutes) {
        this.stepMinutes = stepMinutes;
        initAdapterValues();
    }

    public void setCurved(boolean curved) {
        niceWheelPicker.setCurved(curved);
    }


    public void setDefault(String item) {
        niceWheelPicker.setDefault(item);
    }


    public void setDisplayDefaultText(boolean displayDefaultText) {
        this.displayDefaultText = displayDefaultText;
        defaultText = initDefault();
        initAdapterValues();
    }


    public void setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
        niceWheelPicker.setDefault(getFormattedValue(this.defaultDate));
//        niceWheelPicker.setSelectedItemPosition(findIndexOfDate(defaultDate));
    }


    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }


    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }



    private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(Object data, int position) {

            if (onMinuteChangedListener != null) {

                onMinuteChangedListener.onMinuteChanged(NiceWheelMinutePicker.this,
                        convertItemToMinute(data));

            }
        }

        @Override
        public void onCurrentItemOfScroll(int position) {

        }
    };



    private OnNiceWheelChangeListener onNiceWheelChangeListener = new OnNiceWheelChangeListener() {
        @Override
        public void onWheelScrolled(int offset) {

        }

        @Override
        public void onWheelSelected(int position) {

        }

        @Override
        public void onNiceWheelScrollStateChanged(int state) {

            if (state == SCROLL_STATE_IDLE) {
                if (onScrollFinishedListener != null) {
                    onScrollFinishedListener.onScrollFinished();
                }
            }

        }
    };


    protected List<String> initAdapterValues() {
        final List<String> minutes = new ArrayList<>();

        if (displayDefaultText) {
            minutes.add(defaultText);
        }

        for (int min = MIN_MINUTES; min <= MAX_MINUTES; min += stepMinutes) {
            minutes.add(getFormattedValue(min));
        }

        adapter.setData(minutes);
        niceWheelPicker.setAdapter(adapter);
        niceWheelPicker.setOnNiceWheelChangeListener(onNiceWheelChangeListener);
        niceWheelPicker.setOnItemSelectedListener(onItemSelectedListener);
        niceWheelPicker.notifyDataSetChanged();
        return minutes;
    }


    public void scrollTo(int poison) {

        Log.d(TAG, "滚动到 "  + poison);

        niceWheelPicker.scrollTo(poison);
    }


    public int findIndexOfItem(String item) {

        int index = -1000;

        for (int i = 0; i < adapter.getData().size(); i++) {
            if (item.equals(adapter.getData().get(i))) {
                index = i;
                Log.d(TAG, "查找分位置 " + index);
                break;
            }
        }

        return index;
    }


    public int findIndexOfDate(@NonNull Date date) {
        return findIndexOfItem(getFormattedValue(DateHelper.getMinuteOf(date, stepMinutes)));
    }


    protected String getFormattedValue(Object value) {
        Object valueItem = value;
        if (value instanceof Date) {
            final Calendar instance = Calendar.getInstance();
            instance.setTime((Date) value);
            valueItem = instance.get(Calendar.MINUTE);
        }
        return String.format(LocaleHelper.getCurrentLocale(getContext(), customLocale), FORMAT, valueItem);
    }


//
//    public void setDisplayDefaultText(boolean displayDefaultText) {
//        this.displayDefaultText = displayDefaultText;
//        initDefault();
//        updateAdapter();
//    }
//
//    public String getDefaultText() {
//        return getLocalizedString(R.string.default_text);
//    }
//
//    public void setStepMinutes(int stepMinutes) {
//        if (stepMinutes < 60 && stepMinutes > 0) {
//            this.stepMinutes = stepMinutes;
//            updateAdapter();
//        }
//    }

    private int convertItemToMinute(Object item) {
        if (item.equals(defaultText)) {
            if (displayDefaultText) {
                return DateHelper.getMinuteOf(new Date(), stepMinutes);
            }else {
                return Integer.valueOf(String.valueOf(item));
            }
        }
        return Integer.valueOf(String.valueOf(item));
    }



    public int getCurrentMinute() {
        return convertItemToMinute(adapter.getItem(niceWheelPicker.getCurrentItemPosition()));
    }

    public int getCurrentItemPosition() {
        return niceWheelPicker.getCurrentItemPosition();
    }

    public NiceWheelMinutePicker setOnMinuteChangedListener(OnMinuteChangedListener onMinuteChangedListener) {
        this.onMinuteChangedListener = onMinuteChangedListener;
        return this;
    }

    public NiceWheelMinutePicker setOnFinishedLoopListener(OnFinishedLoopListener onFinishedLoopListener) {
        this.onFinishedLoopListener = onFinishedLoopListener;
        return this;
    }


    public void setOnScrollFinishedListener(OnScrollFinishedListener onScrollFinishedListener) {
        this.onScrollFinishedListener = onScrollFinishedListener;
    }

    public interface OnMinuteChangedListener {
        void onMinuteChanged(NiceWheelMinutePicker picker, int minutes);
    }


    public interface OnFinishedLoopListener {
        void onFinishedLoop(NiceWheelMinutePicker picker);
    }


}