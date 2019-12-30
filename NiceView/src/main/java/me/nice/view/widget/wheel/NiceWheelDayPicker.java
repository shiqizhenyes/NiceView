package me.nice.view.widget.wheel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import static me.nice.view.inter.SingleDateAndTimeConstants.DAYS_PADDING;
import static me.nice.view.inter.SingleDateAndTimeConstants.DAY_FORMAT_M_D;
import static me.nice.view.widget.wheel.NiceWheelPicker.SCROLL_STATE_IDLE;


public class NiceWheelDayPicker extends LinearLayout {

    private final String TAG = NiceWheelDayPicker.class.getSimpleName();

    private NiceWheelPicker<String> niceWheelPicker;

    private NiceWheelAdapter adapter;

    private SimpleDateFormat simpleDateFormat;

    private SimpleDateFormat customDateFormat;

    private OnDaySelectedListener onDaySelectedListener;

    private OnScrollFinishedListener onScrollFinishedListener;

    private boolean displayTomorrow = false;

    private boolean displayTheDayAfterTomorrow = false;

    private boolean displayNow = false;

    private boolean displayPrevious = false;

    private boolean displayFuture = false;


    private Date minDate;
    private Date maxDate;
    private Date defaultDate;

    private String defaultText;


    public NiceWheelDayPicker(Context context) {
        super(context);
    }


    public NiceWheelDayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAdapterValues();
    }


    protected void init(Context context) {
        View rootView = inflate(context, R.layout.nice_day_picker_layout, this);
        niceWheelPicker = rootView.findViewById(R.id.niceWheelDayPicker);
        simpleDateFormat = new SimpleDateFormat(DAY_FORMAT_M_D, LocaleHelper.getCurrentLocale(getContext(), null));
        adapter = new NiceWheelAdapter();
        niceWheelPicker.setAdapter(adapter);
        defaultText = initDefault();
        initAdapterValues();
    }


    public void setCustomLocale(Locale customLocale) {
        simpleDateFormat = new SimpleDateFormat(DAY_FORMAT_M_D, LocaleHelper.getCurrentLocale(getContext(), customLocale));
    }

    public void setDisplayNow(boolean displayNow) {
        this.displayNow = displayNow;
    }

    public void setDisplayTomorrow(boolean displayTomorrow) {
        this.displayTomorrow = displayTomorrow;
        initAdapterValues();
    }

    public void setDisplayTheDayAfterTomorrow(boolean displayTheDayAfterTomorrow) {
        this.displayTheDayAfterTomorrow = displayTheDayAfterTomorrow;
        initAdapterValues();
    }


    public void setDisplayPrevious(boolean displayPrevious) {
        this.displayPrevious = displayPrevious;
        initAdapterValues();
    }

    public void setDisplayFuture(boolean displayFuture) {
        this.displayFuture = displayFuture;
        initAdapterValues();
    }


    public void setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
        niceWheelPicker.setDefault(getFormattedValue(defaultDate));
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }


    protected String initDefault() {
        return getTodayText();
    }


    @NonNull
    public String getNowText() {
        return LocaleHelper.getLocalizedString(getContext(), R.string.now);
    }

    @NonNull
    public String getTodayText() {
        return LocaleHelper.getLocalizedString(getContext(), R.string.picker_today);
    }

    @NonNull
    public String getTomorrowText() {
        return LocaleHelper.getLocalizedString(getContext(), R.string.tomorrow);
    }

    @NonNull
    public String getTheDayAfterTomorrowText() {
        return LocaleHelper.getLocalizedString(getContext(), R.string.the_day_after_tomorrow);
    }


    public int getCurrentItemPosition() {
        return niceWheelPicker.getCurrentItemPosition();
    }


    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getDefaultTextPosition() {
        return adapter.getData().indexOf(LocaleHelper
                .getLocalizedString(getContext(), R.string.default_text));
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getNowItemPosition() {
        return adapter.getData().indexOf(LocaleHelper.getLocalizedString(getContext(),
                R.string.now));
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getTomorrowItemPosition() {
        return adapter.getData().indexOf(LocaleHelper.getLocalizedString(getContext(),
                R.string.tomorrow));
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getTheDayAfterTomorrowItemPosition() {
        return adapter.getData().indexOf(LocaleHelper.getLocalizedString(getContext(),
                R.string.the_day_after_tomorrow));
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public int getTodayItemPosition() {
        return adapter.getData().indexOf(LocaleHelper.getLocalizedString(getContext(),
                R.string.picker_today));
    }


    public void setCurved(boolean curved) {
        niceWheelPicker.setCurved(curved);
    }


    private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(Object data, int position) {
            if (onDaySelectedListener != null) {
                final  Date date = convertItemToDate(position);
                onDaySelectedListener.onDaySelected(NiceWheelDayPicker.this , position, String.valueOf(data), date);
            }
        }

        @Override
        public void onCurrentItemOfScroll(int position) {

        }

    };


    private OnNiceWheelChangeListener onScrollChangeListener = new OnNiceWheelChangeListener() {

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

    public void scrollTo(int poison) {
        niceWheelPicker.scrollTo(poison);
    }


    public int findIndexOfItem(String item) {

        int index = -1000;

        for (int i = 0; i < adapter.getData().size(); i++) {

            if (adapter.getItemText(i).equals(getNowText())) {
                continue;
            }

            if (item.equals(getFormattedValue(convertItemToDate(i)))) {
                index = i;
                break;
            }

        }
       return index;
    }


    public int findIndexOfDate(Date date) {
        return findIndexOfItem(getFormattedValue(date));
    }


    /**
     * 生成日期数据
     * @return
     */
    protected void initAdapterValues() {

        final List<String> days = new ArrayList<>();

        Calendar instance = Calendar.getInstance();

        if (displayPrevious) {
            instance.add(Calendar.DATE, -1 * DAYS_PADDING - 1);
            for (int i = (-1) * DAYS_PADDING; i < 0; ++i) {
                instance.add(Calendar.DAY_OF_MONTH, 1);
                days.add(getFormattedValue(instance.getTime()));
            }
        }


        if (displayNow) {
            days.add(getNowText());
        }

        days.add(getTodayText());

        instance = Calendar.getInstance();

        int dayPadding = 2;
        if (displayFuture) {
            dayPadding = DAYS_PADDING;
        }

        for (int i = 0; i < dayPadding; ++i) {
            instance.add(Calendar.DATE, 1);
            if (i == 0 && displayTomorrow) {
                days.add(getTomorrowText());
                continue;
            }
            if (i == 1 && displayTheDayAfterTomorrow) {
                days.add(getTheDayAfterTomorrowText());
                continue;
            }
            days.add(getFormattedValue(instance.getTime()));
        }

        adapter.setData(days);
        niceWheelPicker.setOnNiceWheelChangeListener(onScrollChangeListener);
        niceWheelPicker.setOnItemSelectedListener(onItemSelectedListener);
        niceWheelPicker.notifyDataSetChanged();
    }

    protected String getFormattedValue(Object value) {
        return getDateFormat().format(value);
    }

    public NiceWheelDayPicker setDayFormatter(SimpleDateFormat simpleDateFormat){
        this.customDateFormat = simpleDateFormat;
        niceWheelPicker.notifyDataSetChanged();
        return this;
    }


    public void setOnScrollFinishedListener(OnScrollFinishedListener onScrollFinishedListener) {
        this.onScrollFinishedListener = onScrollFinishedListener;
    }

    public NiceWheelDayPicker setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
        return this;
    }

    public Date getCurrentDate() {
        return convertItemToDate(niceWheelPicker.getCurrentItemPosition());
    }

    private SimpleDateFormat getDateFormat() {
        if (customDateFormat != null) {
            return customDateFormat;
        }
        return simpleDateFormat;
    }


    public String getCurrentItemText() {
        return adapter.getItemText(niceWheelPicker.getCurrentItemPosition());
    }




    private Date convertItemToDate(int itemPosition) {
        Date date = null;

        final String itemText = adapter.getItemText(itemPosition);

        final Calendar todayCalendar = DateHelper.getCalendarOfDateWithOutHourMinutesSecond(new Date());

        final int todayPosition = adapter.getData().indexOf(getTodayText());

        Log.d(TAG, "getTodayText().equals(itemText) " +  getTodayText().equals(itemText) + " itemText " + itemText + " itemPosition " + itemPosition );

        if (getTodayText().equals(itemText)) {
            date = todayCalendar.getTime();
        }else if (getNowText().equals(itemText)) {
            date = todayCalendar.getTime();
        } else if (getTomorrowText().equals(itemText)) {
            todayCalendar.add(Calendar.DATE, 1);
            date = todayCalendar.getTime();
        } else if (getTheDayAfterTomorrowText().equals(itemText)) {
            todayCalendar.add(Calendar.DATE, 2);
            date = todayCalendar.getTime();
        } else {
            try {
                date = getDateFormat().parse(itemText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (date != null) {
            final Calendar dateCalendar = DateHelper.getCalendarOfDateWithOutHourMinutesSecond(date);
            if (!(getNowText().equals(itemText)) && !(getTomorrowText().equals(itemText)) &&
                    !(getTheDayAfterTomorrowText().equals(itemText))) {
                todayCalendar.add(Calendar.DATE, (itemPosition - todayPosition));
            }
            dateCalendar.set(Calendar.YEAR, todayCalendar.get(Calendar.YEAR));
            date = dateCalendar.getTime();
        }
        return date;
    }

    public void setTodayText(String todayText) {
        int index = adapter.getData().indexOf(getTodayText());
        if (index != -1) {
            adapter.getData().set(index, todayText);
            niceWheelPicker.notifyDataSetChanged();
        }
    }

    public interface OnDaySelectedListener {
        void onDaySelected(NiceWheelDayPicker picker, int position, String name, Date date);
    }
}