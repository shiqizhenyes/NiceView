package me.nice.customview.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.StringRes;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import me.nice.customview.R;
import me.nice.view.dialog.NiceDateAndTimePickerDialog;
import me.nice.view.helper.DateHelper;
import me.nice.view.inter.OnDateSelectedListener;


/**
 * 通用时间选择器
 */
public class NiceChooseTimeDialog implements View.OnClickListener {

    private final String TAG = NiceChooseTimeDialog.class.getSimpleName();

    private NiceDateAndTimePickerDialog niceDateAndTimePickerDialog;
    private WeakReference<Context> contextWeakReference;

//    private TextView commonChooseTimeTitle;
//    private ImageView commonChooseTimeClose;
//    private Button commonChooseTimeButton;

    private Date defaultDate;
    private Date minDate;
    private Date maxDate;
    private boolean displayNow = false;
    private Date selectDate;
    private String selectDay;
    private int titleResId;

    private final NiceDateAndTimePickerDialog.Builder builder;

    private OnDateSelectedListener onDateSelectedListener;
    private Date todayDate;


    public NiceChooseTimeDialog setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
        if (defaultDate != null) {
            builder.defaultDate(defaultDate);
        }
        return this;
    }


    public NiceChooseTimeDialog setMinDate(Date minDate) {
        this.minDate = minDate;
        if (minDate != null) {
            builder.minDate(minDate);
        }
        return this;
    }

    public NiceChooseTimeDialog setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
        if (maxDate != null) {
            builder.maxDate(maxDate);
        }
        return this;
    }


    public NiceChooseTimeDialog setDisplayNow(boolean displayNow) {
        this.displayNow = displayNow;
        if (displayNow) {
            builder.displayNow();
        }
        return this;
    }

    public NiceChooseTimeDialog setCommonChooseTimeTitle(int id) {
        if (id != -1) {
            titleResId = id;
        }
        return this;
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }


    private NiceChooseTimeDialog(Context context) {
        contextWeakReference = new WeakReference<>(context);
        builder = new NiceDateAndTimePickerDialog.Builder(context, R.layout.choose_choose_time_view,
                R.id.niceChooseTimeTimePicker);
        builder.hideTopLayout()
            .displayDays()
            .displayHours()
            .displayMinutes()
            .displayTomorrow()
            .displayTheDayAfterTomorrow();
        todayDate = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(todayDate);
        todayCalendar.set(Calendar.MINUTE, DateHelper
                .getMinuteOf(todayDate, 5));
        todayCalendar.add(Calendar.MINUTE, 30);
        todayDate = todayCalendar.getTime();
        todayCalendar.add(Calendar.DATE, 2);
        Date afterTomorrow = todayCalendar.getTime();
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.setTime(afterTomorrow);
        maxCalendar.set(Calendar.HOUR_OF_DAY, 23);
        maxCalendar.set(Calendar.MINUTE, 55);
        builder.defaultDate(todayDate)
                .minDate(todayDate)
                .maxDate(maxCalendar.getTime());
    }


    public void show() {

        niceDateAndTimePickerDialog = builder.Build();
        if (niceDateAndTimePickerDialog != null) {
            niceDateAndTimePickerDialog.show();
//            commonChooseTimeTitle = (TextView) niceDateAndTimePickerDialog.getView(R.id.commonChooseTimeTitle);
//            commonChooseTimeClose = (ImageView) niceDateAndTimePickerDialog.getView(R.id.commonChooseTimeClose);
//            commonChooseTimeButton = (Button) niceDateAndTimePickerDialog.getView(R.id.commonChooseTimeButton);
//            commonChooseTimeClose.setOnClickListener(this);
//            commonChooseTimeButton.setOnClickListener(this);
//            commonChooseTimeTitle.setText(titleResId);
//
//            if (contextWeakReference.get() != null) {
//                if (displayNow) {
//                    selectDay = contextWeakReference.get().getString(R.string.res_now);
//                    selectDate = DateUtil.getToday();
//                    commonChooseTimeButton.setText(contextWeakReference.get().getString(R.string.res_now_departure));
//                } else {
//                    selectDay = contextWeakReference.get().getString(R.string.res_today);
//                    selectDate = todayDate;
//                    commonChooseTimeButton.setText(String.format("%s%s%s%s%s", contextWeakReference.get().getString(R.string.res_today) ,
//                            contextWeakReference.get().getString(R.string.res_backspace),
//                            DateUtil.stampToHourAndMinuteUseMillisecond(todayDate.getTime()),
//                            contextWeakReference.get().getString(R.string.res_backspace),
//                            contextWeakReference.get().getString(R.string.res_departure)));
//                }
//            }
//
//            niceDateAndTimePickerDialog.setOnDateSelectedListener((day, date) -> {
//
//                LogUtils.d(TAG,"choose day " + day);
//                if (!TextUtils.isEmpty(day) || null != day) {
//                    selectDay = day;
//                }
//                if (contextWeakReference.get() != null) {
//                    if (selectDay.equals(contextWeakReference.get().getString(R.string.res_now))) {
//                        commonChooseTimeButton.setText(contextWeakReference.get().getString(R.string.res_now_departure));
//                        selectDate = DateUtil.getToday();
//                    } else {
//                        selectDate = date;
//                        commonChooseTimeButton.setText(String.format("%s%s%s%s%s", selectDay,
//                                contextWeakReference.get().getString(R.string.res_backspace),
//                                DateUtil.stampToHourAndMinuteUseMillisecond(date.getTime()),
//                                contextWeakReference.get().getString(R.string.res_backspace),
//                                contextWeakReference.get().getString(R.string.res_departure)));
//                    }
//                }
//            });
        }
    }


    public void dismiss() {

        if (niceDateAndTimePickerDialog != null) {
            niceDateAndTimePickerDialog.dismiss();
        }

    }

    @Override
    public void onClick(View v) {

//        if (v.getId() == R.id.commonChooseTimeClose) {
//            dismiss();
//        }else if (v.getId() == R.id.commonChooseTimeButton) {
//
//            if (onDateSelectedListener != null) {
//                onDateSelectedListener.onDateSelected(selectDay, selectDate);
//            }
//
//        }
    }


    public static class Builder {

        private Date defaultDate;
        private Date minDate;
        private Date maxDate;
        private boolean displayNow = false;
        @StringRes
        private int titleResId = -1;


        public Builder defaultDate(Date defaultDate) {
            this.defaultDate = defaultDate;
            return this;
        }


        public Builder minDate(Date minDate) {
            this.minDate = minDate;
            return this;
        }


        public Builder maxDate(Date maxDate) {
            this.maxDate = maxDate;
            return this;
        }


        public Builder displayNow() {
            this.displayNow = true;
            return this;
        }

        public Builder title(@StringRes int titleResId) {
            this.titleResId = titleResId;
            return this;
        }

        public NiceChooseTimeDialog Build(Context context) {
            return new NiceChooseTimeDialog(context)
                    .setDefaultDate(defaultDate)
                    .setMinDate(minDate)
                    .setMaxDate(maxDate)
                    .setDisplayNow(displayNow)
                    .setCommonChooseTimeTitle(titleResId);
        }

    }

}
