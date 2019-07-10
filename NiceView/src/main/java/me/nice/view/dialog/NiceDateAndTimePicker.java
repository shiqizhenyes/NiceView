package me.nice.view.dialog;


import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.LoginFilter;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.nice.view.R;
import me.nice.view.helper.DateHelper;
import me.nice.view.helper.LocaleHelper;
import me.nice.view.inter.OnDateSelectedListener;
import me.nice.view.inter.OnLeftTitleClickListener;
import me.nice.view.inter.OnRightTitleClickListener;
import me.nice.view.inter.OnScrollFinishedListener;
import me.nice.view.widget.NiceWheelAmPmPicker;
import me.nice.view.widget.NiceWheelDayOfMonthPicker;
import me.nice.view.widget.NiceWheelDayPicker;
import me.nice.view.widget.NiceWheelHourPicker;
import me.nice.view.widget.NiceWheelMinutePicker;
import me.nice.view.widget.NiceWheelMonthPicker;
import me.nice.view.widget.NiceWheelPicker;
import me.nice.view.widget.NiceWheelYearPicker;

import static me.nice.view.helper.DateHelper.getCalendarOfDate;
import static me.nice.view.helper.DateHelper.getMinuteOf;
import static me.nice.view.helper.DateHelper.today;
import static me.nice.view.inter.SingleDateAndTimeConstants.*;
import static me.nice.view.widget.NiceWheelPicker.SCROLL_STATE_SCROLLING;

public class NiceDateAndTimePicker extends LinearLayout {


    private final String TAG = NiceDateAndTimePicker.class.getSimpleName();


    @NonNull
    private RelativeLayout niceDateAndTimePickerTopLayout;


    @NonNull
    private Button niceDateAndTimePickerTopLeftButton;

    @NonNull
    private TextView niceDateAndTimePickerTopCenterTitle;


    private Button niceDateAndTimePickerTopRightButton;


    @NonNull
    private NiceWheelYearPicker niceWheelYearPicker;

    @NonNull
    private NiceWheelMonthPicker niceWheelMonthPicker;

    @NonNull
    private NiceWheelDayOfMonthPicker niceWheelDayOfMonthPicker;

    @NonNull
    private NiceWheelDayPicker niceWheelDayPicker;

    @NonNull
    private NiceWheelMinutePicker niceWheelMinutePicker;

    @NonNull
    private NiceWheelHourPicker niceWheelHourPicker;

    @NonNull
    private NiceWheelAmPmPicker niceWheelAmPmPicker;

//    private View dtSelector;

    private boolean mustBeOnFuture = false;

    private boolean displayPrevious = false;

    private boolean displayFuture = false;

    private int minutesStep = STEP_MINUTES_DEFAULT;


    @Nullable
    private Date minDate;
    @Nullable
    private Date maxDate;
    @NonNull
    private Date defaultDate;

    private String day;


    private boolean displayTopLayout = true;
    private boolean displayYears = false;
    private boolean displayMonth = false;
    private boolean displayDaysOfMonth = false;
    private boolean displayDays = true;
    private boolean displayMinutes = true;
    private boolean displayHours = true;


    private boolean displayTomorrow = false;
    private boolean displayTheDayAfterTomorrow = false;
    private boolean displayNow = false;


    private boolean amPm = false;


    private OnDateSelectedListener onDateSelectedListener;


    public NiceDateAndTimePicker(Context context) {
        this(context,null);
    }

    public NiceDateAndTimePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceDateAndTimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        amPm = !DateFormat.is24HourFormat(context);
        initPickerView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NiceDateAndTimePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        amPm = !DateFormat.is24HourFormat(context);
        initPickerView(context);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

//        niceWheelYearPicker.setOnYearSelectedListener(new NiceWheelYearPicker.OnYearSelectedListener() {
//            @Override
//            public void onYearSelected(NiceWheelYearPicker picker, int position, int year) {
////                updateListener();
////                checkMinMaxDate(picker);
////                if (displayDaysOfMonth) {
////                    updateDaysOfMonth();
////                }
//            }
//        });

//        niceWheelMonthPicker.setOnMonthSelectedListener(new NiceWheelMonthPicker.MonthSelectedListener() {
//            @Override
//            public void onMonthSelected(NiceWheelMonthPicker picker, int monthIndex, String monthName) {
////                updateListener();
////                checkMinMaxDate(picker);
////                if (displayDaysOfMonth) {
////                    updateDaysOfMonth();
////                }
//            }
//        });

//        niceWheelDayOfMonthPicker
//                .setDayOfMonthSelectedListener(new NiceWheelDayOfMonthPicker.DayOfMonthSelectedListener() {
//                    @Override
//                    public void onDayOfMonthSelected(NiceWheelDayOfMonthPicker picker, int dayIndex) {
////                        updateListener();
////                        checkMinMaxDate(picker);
//                    }
//                });

//        niceWheelDayOfMonthPicker
//                .setOnFinishedLoopListener(new NiceWheelDayOfMonthPicker.FinishedLoopListener() {
//                    @Override
//                    public void onFinishedLoop(NiceWheelDayOfMonthPicker picker) {
////                        if (displayMonth) {
////                            niceWheelMonthPicker.scrollTo(niceWheelMonthPicker.getCurrentItemPosition() + 1);
////                            updateDaysOfMonth();
////                        }
//                    }
//                });


        niceWheelDayPicker
                .setOnDaySelectedListener(new NiceWheelDayPicker.OnDaySelectedListener() {
                    @Override
                    public void onDaySelected(NiceWheelDayPicker picker, int position, String name, Date date) {

                        Log.d(TAG, "name  " + name + " date  " + date.toString() + " position " + position);

                        day = name;
                        if (displayNow) {
                            // TODO: 2019/7/8 显示现在

                            if (niceWheelDayPicker.getCurrentItemPosition() ==
                                    niceWheelDayPicker.getNowItemPosition()) {

                                niceWheelHourPicker.scrollTo(niceWheelHourPicker.getDefaultTextPosition());
                                niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.getDefaultTextPosition());

                            }else if (niceWheelDayPicker.getCurrentItemPosition() ==
                                    niceWheelDayPicker.getTodayItemPosition()){
                                niceWheelHourPicker.scrollTo(niceWheelHourPicker.findIndexOfDate(minDate));
                                niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.findIndexOfDate(minDate));
                            }else {
                                niceWheelHourPicker.scrollTo(niceWheelHourPicker.getDefaultTextPosition() +1);
                                niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.getDefaultTextPosition() +1);
                                checkMinMaxDate();
                            }
                        }else {
                            checkMinMaxDate();
                        }

                    }
                }).setOnScrollFinishedListener(new OnScrollFinishedListener() {
            @Override
            public void onScrollFinished() {
//                checkMinMaxDate();
                Log.d(TAG, "滚动完成 ");

                if (onDateSelectedListener != null) {
                    onDateSelectedListener.onDateSelected(day ,getDate());
                }

            }
        });


        niceWheelHourPicker
                .setHourChangedListener(new NiceWheelHourPicker.OnHourChangedListener() {
                    @Override
                    public void onHourChanged(NiceWheelHourPicker picker, int hour) {
                        Log.d(TAG, "hour  " + hour);

                        if (displayNow) {
                            // TODO: 2019/7/8
                            if (niceWheelDayPicker.getCurrentItemPosition() ==
                                    niceWheelDayPicker.getNowItemPosition()) {
                                niceWheelHourPicker.scrollTo(niceWheelHourPicker.getDefaultTextPosition());
                            } else if (niceWheelDayPicker.getCurrentItemPosition() ==
                                    niceWheelDayPicker.getTodayItemPosition()){
                                if (niceWheelHourPicker.getCurrentItemPosition() == niceWheelHourPicker.getDefaultTextPosition()) {
                                    niceWheelHourPicker.scrollTo(niceWheelHourPicker.getDefaultTextPosition() + 1);
                                }
                                checkMinMaxDate();
                            }else {
                                if (niceWheelHourPicker.getCurrentItemPosition() == niceWheelHourPicker.getDefaultTextPosition()) {
                                    niceWheelHourPicker.scrollTo(niceWheelHourPicker.getDefaultTextPosition() + 1);
                                }
                                checkMinMaxDate();
                            }
                        }else {
                            checkMinMaxDate();
                        }
                    }
                }).setOnScrollFinishedListener(new OnScrollFinishedListener() {
            @Override
            public void onScrollFinished() {
                Log.d(TAG, "niceWheelHourPicker滚动完成 ");
                if (onDateSelectedListener != null) {
                    onDateSelectedListener.onDateSelected(day ,getDate());
                }
            }
        });
//
        niceWheelMinutePicker
                .setOnMinuteChangedListener(new NiceWheelMinutePicker.OnMinuteChangedListener() {
                    @Override
                    public void onMinuteChanged(NiceWheelMinutePicker picker, int minutes) {
                        Log.d(TAG, "minutes  " + minutes);
                        if (displayNow) {
                            // TODO: 2019/7/8 显示现在
                            if (niceWheelDayPicker.getCurrentItemPosition() ==
                                    niceWheelDayPicker.getNowItemPosition()) {
                                niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.getDefaultTextPosition());
                            }else if (niceWheelDayPicker.getCurrentItemPosition() ==
                                    niceWheelDayPicker.getTodayItemPosition()){
                                if (niceWheelMinutePicker.getCurrentItemPosition() == niceWheelMinutePicker.getDefaultTextPosition()) {
                                    niceWheelHourPicker.scrollTo(niceWheelHourPicker.getDefaultTextPosition() + 1);
                                }
                                checkMinMaxDate();
                            }else {
                                if (niceWheelMinutePicker.getCurrentItemPosition() == niceWheelMinutePicker.getDefaultTextPosition()) {
                                    niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.getDefaultTextPosition() + 1);
                                }
                                checkMinMaxDate();
                            }
                        }else {
                            checkMinMaxDate();
                        }
                    }
                }).setOnScrollFinishedListener(new OnScrollFinishedListener() {
            @Override
            public void onScrollFinished() {

                Log.d(TAG, "niceWheelMinutePicker滚动完成 ");
                if (onDateSelectedListener != null) {
                    onDateSelectedListener.onDateSelected(day, getDate());
                }

            }
        });
//
//
//
//        niceWheelAmPmPicker
//                .setAmPmListener(new NiceWheelAmPmPicker.AmPmListener() {
//                    @Override
//                    public void onAmPmChanged(NiceWheelAmPmPicker picker, boolean isAm) {
//                        updateListener();
//                        checkMinMaxDate(picker);
//                    }
//                });

//        setDefaultDate(this.defaultDate); //update displayed date
    }


//    private void checkSettings() {
//        if (displayDays && (displayDaysOfMonth || displayMonth)) {
//            throw new IllegalArgumentException("You can either display days with months or days and months separately");
//        }
//    }
//

    private <T> void checkMinMaxDate() {
        checkBeforeMinDate();
        checkAfterMaxDate();
    }

//    private void checkBeforeDefaultDate(final NiceWheelPicker picker) {
//        picker.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                if (isBeforeDefaultDate(getDate())) {
//                    niceWheelYearPicker.scrollTo(niceWheelYearPicker.findIndexOfDate(defaultDate));
//                    niceWheelMonthPicker.scrollTo(niceWheelMonthPicker.findIndexOfDate(defaultDate));
//                    niceWheelDayOfMonthPicker.scrollTo(niceWheelDayOfMonthPicker.findIndexOfDate(defaultDate));
//                    niceWheelDayPicker.scrollTo(niceWheelDayPicker.findIndexOfDate(defaultDate));
//                    niceWheelHourPicker.scrollTo(niceWheelHourPicker.findIndexOfDate(defaultDate));
//                    niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.findIndexOfDate(defaultDate));
//                    niceWheelAmPmPicker.scrollTo(niceWheelAmPmPicker.findIndexOfDate(defaultDate));
//                }
//
//            }
//        }, DELAY_BEFORE_CHECK_PAST);
//    }
//
//
    private void checkBeforeMinDate() {

        Log.d(TAG, "获取时间" + getDate().toString() + " 是否在最小日期之前 " + isBeforeMinDate(getDate()));

        if (isBeforeMinDate(getDate())) {

            niceWheelDayPicker.scrollTo(niceWheelDayPicker.findIndexOfDate(minDate));

            Log.d(TAG, " 日期查找位置 " + niceWheelDayPicker.findIndexOfDate(minDate));

            niceWheelHourPicker.scrollTo(niceWheelHourPicker.findIndexOfDate(minDate));

            Log.d(TAG, " 日期查找位置 " + niceWheelHourPicker.findIndexOfDate(minDate));


            niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.findIndexOfDate(minDate));

            Log.d(TAG, " 日期查找位置 " + niceWheelMinutePicker.findIndexOfDate(minDate));

        }

    }


//    private void checkAfterDefaultDate(final NiceWheelPicker picker) {
//        picker.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                if (isAfterMaxDate(getDate())) {
//                    niceWheelYearPicker.scrollTo(niceWheelYearPicker.findIndexOfDate(defaultDate));
//                    niceWheelMonthPicker.scrollTo(niceWheelMonthPicker.findIndexOfDate(defaultDate));
//                    niceWheelDayOfMonthPicker.scrollTo(niceWheelDayOfMonthPicker.findIndexOfDate(defaultDate));
//                    niceWheelDayPicker.scrollTo(niceWheelDayPicker.findIndexOfDate(defaultDate));
//                    niceWheelHourPicker.scrollTo(niceWheelHourPicker.findIndexOfDate(defaultDate));
//                    niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.findIndexOfDate(defaultDate));
//                    niceWheelAmPmPicker.scrollTo(niceWheelAmPmPicker.findIndexOfDate(defaultDate));
//                }
//
//            }
//        }, DELAY_BEFORE_CHECK_PAST);
//    }

    private void checkAfterMaxDate() {

        Log.d(TAG, "获取时间" + getDate().toString() + " 是否在最大日期之后 " + isAfterMaxDate(getDate()) + " maxDate  " + maxDate.toString());

        if (isAfterMaxDate(getDate())) {
            niceWheelDayPicker.scrollTo(niceWheelDayPicker.findIndexOfDate(maxDate));
            niceWheelHourPicker.scrollTo(niceWheelHourPicker.findIndexOfDate(maxDate));
            niceWheelMinutePicker.scrollTo(niceWheelMinutePicker.findIndexOfDate(maxDate));
        }

    }
//
//
//    private boolean isBeforeDefaultDate(Date date) {
//        return getCalendarOfDate(date).before(getCalendarOfDate(defaultDate));
//    }
//
//
    private boolean isBeforeMinDate(Date date) {
        return getCalendarOfDate(date).before(getCalendarOfDate(minDate));
    }

//
//
//    private boolean isAfterDefaultDate(Date date) {
//        return getCalendarOfDate(date).after(getCalendarOfDate(defaultDate));
//    }
//
    private boolean isAfterMaxDate(Date date) {
        return getCalendarOfDate(date).after(getCalendarOfDate(maxDate));
    }


//    public void addOnDateChangedListener(OnDateChangedListener listener) {
//        this.listeners.add(listener);
//    }
//
//    public void removeOnDateChangedListener(OnDateChangedListener listener) {
//        this.listeners.remove(listener);
//    }
//
//
//    public void checkPickersMinMax() {
//        checkMinMaxDate(niceWheelYearPicker);
//        checkMinMaxDate(niceWheelMonthPicker);
//        checkMinMaxDate(niceWheelDayOfMonthPicker);
//        checkMinMaxDate(niceWheelDayPicker);
//        checkMinMaxDate(niceWheelHourPicker);
//        checkMinMaxDate(niceWheelMinutePicker);
//        checkMinMaxDate(niceWheelAmPmPicker);
//    }
//
//    public void setMustBeOnFuture(boolean mustBeOnFuture) {
//        this.mustBeOnFuture = mustBeOnFuture;
//        if (mustBeOnFuture) {
//            minDate = Calendar.getInstance().getTime(); //minDate is Today
//        }
//    }
//
//
//    public boolean mustBeOnFuture() {
//        return mustBeOnFuture;
//    }
//

    public void setDisplayNow(boolean displayNow) {
        this.displayNow = displayNow;
        niceWheelDayPicker.setDisplayNow(this.displayNow);
        if (displayNow) {
            niceWheelHourPicker.setDisplayDefaultText(true);
            niceWheelHourPicker.setDefault(niceWheelHourPicker.getDefaultText());
            niceWheelMinutePicker.setDisplayDefaultText(true);
            niceWheelMinutePicker.setDefault(niceWheelMinutePicker.getDefaultText());
        }
    }


    public void setDisplayTomorrow(boolean displayTomorrow) {
        this.displayTomorrow = displayTomorrow;
        niceWheelDayPicker.setDisplayTomorrow(this.displayTomorrow);
    }


    public void setDisplayTheDayAfterTomorrow(boolean displayTheDayAfterTomorrow) {
        this.displayTheDayAfterTomorrow = displayTheDayAfterTomorrow;
        niceWheelDayPicker.setDisplayTheDayAfterTomorrow(this.displayTheDayAfterTomorrow);
    }



    public void setDisplayPrevious(boolean displayPrevious) {
        this.displayPrevious = displayPrevious;
        niceWheelDayPicker.setDisplayPrevious(displayPrevious);
    }


    public void setDisplayFuture(boolean displayFuture) {
        this.displayFuture = displayFuture;
        niceWheelDayPicker.setDisplayFuture(displayFuture);
    }

//
//    private void setMinAndMaxYear() {
//        if (displayYears && this.minDate != null && this.maxDate != null) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(this.minDate);
//            niceWheelYearPicker.setMinYear(calendar.get(Calendar.YEAR));
//            calendar.setTime(this.maxDate);
//            niceWheelYearPicker.setMaxYear(calendar.get(Calendar.YEAR));
//        }
//    }


    public void setMinDate(Date minDate) {
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.setTime(minDate);
        minCalendar.set(Calendar.MINUTE, getMinuteOf(minDate, minutesStep));
        this.minDate = minCalendar.getTime();
        niceWheelDayPicker.setMinDate(this.minDate);
        niceWheelHourPicker.setMinDate(this.minDate);
        niceWheelMinutePicker.setMinDate(this.minDate);
//        setMinAndMaxYear();
    }


    public void setMaxDate(Date maxDate) {
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.setTime(maxDate);
        maxCalendar.set(Calendar.MINUTE, getMinuteOf(maxDate, minutesStep));
        this.maxDate = maxCalendar.getTime();

        niceWheelDayPicker.setMaxDate(this.maxDate);
        niceWheelHourPicker.setMaxDate(this.maxDate);
        niceWheelMinutePicker.setMaxDate(this.maxDate);

//        niceWheelDayPicker
//        setMinAndMaxYear();
    }


//
//    public void selectDate(Calendar calendar) {
//        if (calendar == null) {
//            return;
//        }
//        final Date date = calendar.getTime();
//        niceWheelYearPicker.selectDate(date);
//        niceWheelMonthPicker.selectDate(date);
//        niceWheelDayOfMonthPicker.selectDate(date);
//        niceWheelDayPicker.selectDate(date);
//        niceWheelHourPicker.selectDate(date);
//        niceWheelMinutePicker.selectDate(date);
//        niceWheelAmPmPicker.selectDate(date);
//        if (displayDaysOfMonth) {
//            updateDaysOfMonth();
//        }
//    }
//
//    public void setAmPm(boolean amPm) {
//        this.amPm = amPm;
//        niceWheelAmPmPicker.setVisibility((amPm && displayHours) ? VISIBLE : GONE);
//        niceWheelHourPicker.setIsAmPm(amPm);
//    }
//
    /**
     * 是否显示上方布局
     * @param displayTopLayout
     */
    public void setDisplayTopLayout(boolean displayTopLayout) {
        this.displayTopLayout = displayTopLayout;
        niceDateAndTimePickerTopLayout.setVisibility( displayTopLayout ? VISIBLE : GONE);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }


    //
//
//    public void setDisplayYears(boolean displayYears) {
//        this.displayYears = displayYears;
//        niceWheelYearPicker.setVisibility(displayYears ? VISIBLE : GONE);
//    }
//
//    public void setDisplayMonths(boolean displayMonths) {
//        this.displayMonth = displayMonths;
//        niceWheelMonthPicker.setVisibility(displayMonths ? VISIBLE : GONE);
//        checkSettings();
//    }
//
//    public void setDisplayDaysOfMonth(boolean displayDaysOfMonth) {
//        this.displayDaysOfMonth = displayDaysOfMonth;
//        niceWheelDayOfMonthPicker.setVisibility(displayDaysOfMonth ? VISIBLE : GONE);
//        if (displayDaysOfMonth) {
//            updateDaysOfMonth();
//        }
//        checkSettings();
//    }
//
//    public void setDisplayDays(boolean displayDays) {
//        this.displayDays = displayDays;
//        niceWheelDayPicker.setVisibility(displayDays ? VISIBLE : GONE);
//        checkSettings();
//    }
//
//    public void setDisplayMinutes(boolean displayMinutes) {
//        this.displayMinutes = displayMinutes;
//        niceWheelMinutePicker.setVisibility(displayMinutes ? VISIBLE : GONE);
//    }
//
//
//    public void setDisplayHours(boolean displayHours) {
//        this.displayHours = displayHours;
//        niceWheelHourPicker.setVisibility(displayHours ? VISIBLE : GONE);
//        setAmPm(this.amPm);
//        niceWheelHourPicker.setIsAmPm(amPm);
//    }
//
//
//    public void setDisplayMonthNumbers(boolean displayMonthNumbers) {
//        this.niceWheelMonthPicker.setDisplayMonthNumbers(displayMonthNumbers);
//        this.niceWheelMonthPicker.updateAdapter();
//    }
//
//
    /**
     * 是否卷曲
     * @param curved
     */
    public void setCurved(boolean curved) {
//        niceWheelYearPicker.setCurved(curved);
//        niceWheelMonthPicker.setCurved(curved);
//        niceWheelDayOfMonthPicker.setCurved(curved);
        niceWheelDayPicker.setCurved(curved);
        niceWheelHourPicker.setCurved(curved);
        niceWheelMinutePicker.setCurved(curved);
//        niceWheelAmPmPicker.setCurved(curved);
    }
//
//
//    public void setVisibleItemCount(int visibleItemCount) {
//        niceWheelYearPicker.setVisibleItemCount(visibleItemCount);
//        niceWheelMonthPicker.setVisibleItemCount(visibleItemCount);
//        niceWheelDayOfMonthPicker.setVisibleItemCount(visibleItemCount);
//        niceWheelDayPicker.setVisibleItemCount(visibleItemCount);
//        niceWheelHourPicker.setVisibleItemCount(visibleItemCount);
//        niceWheelMinutePicker.setVisibleItemCount(visibleItemCount);
//        niceWheelAmPmPicker.setVisibleItemCount(visibleItemCount);
//    }
//
//
    public Date getDate() {

        int hour = niceWheelHourPicker.getCurrentHour();
//
//        if (hour == -1) {
//            hour = DateHelper.getHour(new Date());
//        }
//
//        if (amPm && niceWheelAmPmPicker.isPm()) {
//            hour += PM_HOUR_ADDITION;
//        }

        final int minute;

        if (niceWheelMinutePicker.getCurrentMinute() == -1) {
            minute = DateHelper.getMinuteOf(new Date(), minutesStep);
        }else {
            minute = niceWheelMinutePicker.getCurrentMinute();
        }

        final Calendar calendar = Calendar.getInstance();

        if (displayDays) {
            final Date dayDate = niceWheelDayPicker.getCurrentDate();
            calendar.setTime(dayDate);
        } else {

//            if (displayMonth) {
//                calendar.set(Calendar.MONTH, niceWheelMonthPicker.getCurrentMonth());
//            }

//            if (displayYears) {
//                calendar.set(Calendar.YEAR, niceWheelYearPicker.getCurrentYear());
//            }

//            if (displayDaysOfMonth) {
//                int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//                if (niceWheelDayOfMonthPicker.getCurrentDay() >= daysInMonth) {
//                    calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
//                } else {
//                    calendar.set(Calendar.DAY_OF_MONTH, niceWheelDayOfMonthPicker.getCurrentDay() + 1);
//                }
//            }

        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }


    public void setStepMinutes(int minutesStep) {
        this.minutesStep = minutesStep;
        niceWheelMinutePicker.setStepMinutes(minutesStep);
    }


    public void setHoursStep(int hoursStep) {
        niceWheelHourPicker.setHoursStep(hoursStep);
    }


    public void setDefaultDate(Date date) {
        if (date != null && !displayNow) {
            Calendar defaultCalendar = Calendar.getInstance();
            defaultCalendar.setTime(date);
            defaultCalendar.set(Calendar.MINUTE, getMinuteOf(date, minutesStep));
            this.defaultDate = defaultCalendar.getTime();
//            niceWheelYearPicker.setDefaultDate(defaultDate);
//            niceWheelMonthPicker.setDefaultDate(defaultDate);
//            niceWheelDayOfMonthPicker.setDefaultDate(defaultDate);
            niceWheelDayPicker.setDefaultDate(defaultDate);
            niceWheelHourPicker.setDefaultDate(defaultDate);
            niceWheelMinutePicker.setDefaultDate(defaultDate);
//            niceWheelAmPmPicker.setDefaultDate(defaultDate);
        }
    }



    public void hideTitle() {
        niceDateAndTimePickerTopCenterTitle.setVisibility(GONE);

    }

    public void setCenterTitle(String title) {
        niceDateAndTimePickerTopCenterTitle.setText(title);
    }



//
//
//    private void updateListener() {
//        final Date date = getDate();
//        final CharSequence format = amPm ? FORMAT_12_HOUR : FORMAT_24_HOUR;
//        final String displayed = DateFormat.format(format, date).toString();
//        for (OnDateChangedListener listener : listeners) {
//            listener.onDateChanged(displayed, date);
//        }
//    }
//
//
//    private void updateDaysOfMonth() {
//        final Date date = getDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        updateDaysOfMonth(calendar);
//    }
//
//    private void updateDaysOfMonth(@NonNull Calendar calendar) {
//        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        niceWheelDayOfMonthPicker.setDaysInMonth(daysInMonth);
//        niceWheelDayOfMonthPicker.updateAdapter();
//    }
//
//
    private void initPickerView(Context context) {

        Log.d(TAG, "NiceDateAndTimePicker");

        View rootView = inflate(context, R.layout.nice_date_and_time_picker_layout, this);

        niceDateAndTimePickerTopLayout = rootView.findViewById(R.id.niceDateAndTimePickerTopLayout);
        niceDateAndTimePickerTopLeftButton = rootView.findViewById(R.id.niceDateAndTimePickerTopLeftButton);
        niceDateAndTimePickerTopCenterTitle = rootView.findViewById(R.id.niceDateAndTimePickerTopCenterTitle);
        niceDateAndTimePickerTopRightButton = rootView.findViewById(R.id.niceDateAndTimePickerTopRightButton);
//        niceWheelYearPicker = rootView.findViewById(R.id.niceDateAndTimePickerYearPicker);
//        niceWheelMonthPicker = rootView.findViewById(R.id.niceDateAndTimePickerMonthPicker);
//        niceWheelDayOfMonthPicker = rootView.findViewById(R.id.niceDateAndTimePickerDayOfMonthPicker);

        niceWheelDayPicker = rootView.findViewById(R.id.niceDateAndTimePickerDayPicker);
        niceWheelHourPicker = rootView.findViewById(R.id.niceDateAndTimePickerHourPicker);
        niceWheelMinutePicker = rootView.findViewById(R.id.niceDateAndTimePickerMinutePicker);
//        niceWheelAmPmPicker = rootView.findViewById(R.id.niceDateAndTimePickerAmPmPicker);


//        setDefaultDate(new Date());
//
        initStyle();
//
//        if (displayDaysOfMonth) {
//            updateDaysOfMonth(Calendar.getInstance());
//        }

    }

    private void initStyle() {
        // TODO: 2019-06-29 自定义属性过段时间写
//        checkSettings();
        niceWheelDayPicker.setDisplayPrevious(displayPrevious);
        niceWheelDayPicker.setDisplayFuture(displayFuture);
        niceWheelDayPicker.setDisplayNow(displayNow);
        niceWheelDayPicker.setDisplayTomorrow(displayTomorrow);
        niceWheelDayPicker.setDisplayTheDayAfterTomorrow(displayTheDayAfterTomorrow);
    }

    @NonNull
    public Button getNiceDateAndTimePickerTopLeftButton() {
        return niceDateAndTimePickerTopLeftButton;
    }


    public Button getNiceDateAndTimePickerTopRightButton() {
        return niceDateAndTimePickerTopRightButton;
    }


}
