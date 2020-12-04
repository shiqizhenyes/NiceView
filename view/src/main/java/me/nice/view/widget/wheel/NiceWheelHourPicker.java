package me.nice.view.widget.wheel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.nice.view.R;
import me.nice.view.adapter.NiceWheelAdapter;
import me.nice.view.helper.LocaleHelper;
import me.nice.view.inter.OnItemSelectedListener;
import me.nice.view.inter.OnNiceWheelChangeListener;
import me.nice.view.inter.OnScrollFinishedListener;

import static me.nice.view.helper.DateHelper.getHour;
import static me.nice.view.helper.DateHelper.today;
import static me.nice.view.inter.SingleDateAndTimeConstants.*;
import static me.nice.view.widget.wheel.NiceWheelPicker.FORMAT;
import static me.nice.view.widget.wheel.NiceWheelPicker.SCROLL_STATE_IDLE;

public class NiceWheelHourPicker extends LinearLayout {

    private final String TAG = NiceWheelHourPicker.class.getSimpleName();


    private int minHour;
    private int maxHour;
    private int hoursStep;

    private Locale customLocale;

    protected boolean isAmPm;

    protected boolean displayDefaultText = false;

    private FinishedLoopListener finishedLoopListener;
    private OnHourChangedListener onHourChangedListener;
    private OnScrollFinishedListener onScrollFinishedListener;



    private String defaultText;


    private Date minDate;
    private Date maxDate;
    private Date defaultDate;


    private NiceWheelPicker<String> niceWheelPicker;
    private NiceWheelAdapter adapter;


    public NiceWheelHourPicker(Context context) {
        super(context);
    }

    public NiceWheelHourPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    protected void init(Context context) {
        Log.d(TAG, "NiceWheelHourPicker");
        isAmPm = false;
        minHour = MIN_HOUR_DEFAULT;
        maxHour = MAX_HOUR_DEFAULT;
        hoursStep = STEP_HOURS_DEFAULT;
        View rootView = inflate(context, R.layout.nice_hour_picker_layout,this);
        niceWheelPicker = rootView.findViewById(R.id.niceWheelHourPicker);
        adapter = new NiceWheelAdapter();
        niceWheelPicker.setAdapter(adapter);
        defaultText = initDefault();
        initAdapterValues();
    }


    protected String initDefault() {
        if (displayDefaultText) {
            return LocaleHelper.getLocalizedString(getContext(), R.string.default_text);
        }else {

            if (defaultDate == null) {


            }else {

            }

        }
        return String.valueOf(getHour(today(), isAmPm));
    }


    public void setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
        niceWheelPicker.setDefault(getFormattedValue(this.defaultDate));
//        niceWheelPicker.setSelectedItemPosition(findIndexOfItem(getFormattedValue(defaultDate)));
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }


    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setMaxHour(int maxHour) {
        if (maxHour >= MIN_HOUR_DEFAULT && maxHour <= MAX_HOUR_DEFAULT) {
            this.maxHour = maxHour;
        }

    }

    public void setMinHour(int minHour) {
        if (minHour >= MIN_HOUR_DEFAULT && minHour <= MAX_HOUR_DEFAULT) {
            this.minHour = minHour;
        }
    }

    public void setHoursStep(int hoursStep) {
        if (hoursStep >= MIN_HOUR_DEFAULT && hoursStep <= MAX_HOUR_DEFAULT) {
            this.hoursStep = hoursStep;
        }
    }

    public void setCurved(boolean curved) {
        niceWheelPicker.setCurved(curved);
    }


    public void setCustomLocale(Locale customLocale) {
        this.customLocale = customLocale;
    }


    public void setDefault(String item) {
        niceWheelPicker.setSelectedItemPosition(findIndexOfItem(item));
    }

    public void setDisplayDefaultText(boolean displayDefaultText) {
        this.displayDefaultText = displayDefaultText;
        defaultText = initDefault();
        initAdapterValues();
    }


    private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(Object data, int position) {

            if (onHourChangedListener != null) {

                onHourChangedListener.onHourChanged(NiceWheelHourPicker.this, convertItemToHour(data));

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

        final List<String> hours = new ArrayList<>();

        if (displayDefaultText) {
            hours.add(defaultText);
        }

        if (isAmPm) {
            hours.add(getFormattedValue(12));
            for (int hour = hoursStep; hour < maxHour; hour += hoursStep) {
                hours.add(getFormattedValue(hour));
            }
        } else {
            for (int hour = minHour; hour <= maxHour; hour += hoursStep) {
                hours.add(getFormattedValue(hour));
            }
        }
        adapter.setData(hours);
        niceWheelPicker.setAdapter(adapter);
        niceWheelPicker.setOnNiceWheelChangeListener(onNiceWheelChangeListener);
        niceWheelPicker.setOnItemSelectedListener(onItemSelectedListener);
        niceWheelPicker.notifyDataSetChanged();
        return hours;
    }


    public void scrollTo(int poison) {
        niceWheelPicker.scrollTo(poison);
    }


    public int findIndexOfItem(String item) {

        int index = -1000;

        for (int i = 0; i < adapter.getData().size(); i++) {
            if (item.equals(adapter.getData().get(i))) {
                index = i;
                break;
            }
        }

        return index;
    }


    public int findIndexOfDate(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (isAmPm) {
            return findIndexOfItem(getFormattedValue(calendar.get(Calendar.HOUR)));
        }

        return findIndexOfItem(getFormattedValue(calendar.get(Calendar.HOUR_OF_DAY)));
    }


    protected String getFormattedValue(Object value) {
        Object valueItem = value;
        if (value instanceof Date) {
            Calendar instance = Calendar.getInstance();
            instance.setTime((Date) value);
            valueItem = instance.get(Calendar.HOUR_OF_DAY);
        }
        return String.format(LocaleHelper.getCurrentLocale(getContext(), customLocale),
                FORMAT, valueItem);
    }


    public String getDefaultText() {
        return defaultText;
    }


    public int getCurrentItemPosition() {

        return niceWheelPicker.getCurrentItemPosition();
    }


//    public void setDisplayDefaultText(boolean displayDefaultText) {
//        this.displayDefaultText = displayDefaultText;
//        initDefault();
//        updateAdapter();
//    }
//
//    @Override
//    public void setDefault(String defaultValue) {
//        try {
//            if (!defaultValue.equals(getDefaultText())) {
//                int hour = Integer.parseInt(defaultValue);
//                if (isAmPm && hour >= MAX_HOUR_AM_PM) {
//                    hour -= MAX_HOUR_AM_PM;
//                }
//                super.setDefault(getFormattedValue(hour));
//            }else {
//                super.setDefault(defaultValue);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void setIsAmPm(boolean isAmPm) {
//        this.isAmPm = isAmPm;
//        if (isAmPm) {
//            setMaxHour(MAX_HOUR_AM_PM);
//        } else {
//            setMaxHour(MAX_HOUR_DEFAULT);
//        }
//        updateAdapter();
//    }
//


    private int convertItemToHour(Object item) {

        if (item.equals(defaultText)) {

            if (displayDefaultText) {
                return getHour(today());
            }else {
                return Integer.parseInt(String.valueOf(item));
            }
        }

        Integer hour = Integer.valueOf(String.valueOf(item));
        if (!isAmPm) {
            return hour;
        }

        if (hour == 12) {
            hour = 0;
        }

        return hour;
    }

    public int getCurrentHour() {
        return convertItemToHour(adapter.getItem(niceWheelPicker.getCurrentItemPosition()));
    }


    public NiceWheelHourPicker setOnFinishedLoopListener(FinishedLoopListener finishedLoopListener) {
        this.finishedLoopListener = finishedLoopListener;
        return this;
    }

    public NiceWheelHourPicker setHourChangedListener(OnHourChangedListener onHourChangedListener) {
        this.onHourChangedListener = onHourChangedListener;
        return this;
    }

    public void setOnScrollFinishedListener(OnScrollFinishedListener onScrollFinishedListener) {
        this.onScrollFinishedListener = onScrollFinishedListener;
    }

    public interface FinishedLoopListener {
        void onFinishedLoop(NiceWheelHourPicker picker);
    }

    public interface OnHourChangedListener {
        void onHourChanged(NiceWheelHourPicker picker, int hour);
    }


    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getDefaultTextPosition() {
        return adapter.getData().indexOf(LocaleHelper
                .getLocalizedString(getContext(), R.string.default_text));
    }
}