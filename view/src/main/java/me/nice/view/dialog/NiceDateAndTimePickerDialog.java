package me.nice.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.nice.view.R;
import me.nice.view.inter.OnDateSelectedListener;
import me.nice.view.inter.OnLeftTitleClickListener;
import me.nice.view.inter.OnRightTitleClickListener;

import static me.nice.view.inter.SingleDateAndTimeConstants.*;

public class NiceDateAndTimePickerDialog extends BaseDialog implements View.OnClickListener {

    private WeakReference<Context> contextWeakReference;

    private int dialogLayout;

    private int niceDateAndTimePickerId;

    private int style = BOTTOM_SHEET;

    private NiceDateAndTimePicker niceDateAndTimePicker;

    private OnDateSelectedListener onDateSelectedListener;

    private OnLeftTitleClickListener onLeftTitleClickListener;

    private OnRightTitleClickListener onRightTitleClickListener;

    private BottomSheetDialog niceDateAndTimePickerBottomDialog;
    private View rootView;

    private NiceDateAndTimePickerDialog() { }


    private boolean displayTomorrow = false;
    private boolean displayTheDayAfterTomorrow = false;
    private boolean displayNow = false;

    private boolean displayPrevious = false;
    private boolean displayFuture = false;
    private String title;
    private int titleId;


    public NiceDateAndTimePickerDialog(WeakReference<Context> context, int dialogLayout, int niceDateAndTimePickerId) {
        this.contextWeakReference = context;
        this.dialogLayout = dialogLayout;
        this.niceDateAndTimePickerId = niceDateAndTimePickerId;
        if (contextWeakReference.get() != null) {
            rootView = LayoutInflater.from(contextWeakReference.get()).inflate(dialogLayout, null);
            niceDateAndTimePicker = rootView.findViewById(niceDateAndTimePickerId);
            niceDateAndTimePicker.getNiceDateAndTimePickerTopLeftButton().setOnClickListener(this);
            niceDateAndTimePicker.getNiceDateAndTimePickerTopRightButton().setOnClickListener(this);
        }
    }


    private NiceDateAndTimePickerDialog setStyle(int style) {
        this.style = style;

        if (this.style == BOTTOM_SHEET) {
            niceDateAndTimePickerBottomDialog = new BottomSheetDialog(contextWeakReference.get());
        }

        return this;
    }

    public NiceDateAndTimePickerDialog setMustBeOnFuture(boolean mustBeOnFuture) {
        this.mustBeOnFuture = mustBeOnFuture;
        if (niceDateAndTimePicker != null) {
//            niceDateAndTimePicker.setMustBeOnFuture(mustBeOnFuture);
        }
        return this;
    }


    public NiceDateAndTimePickerDialog displayTopLayout(boolean display) {
        this.displayTopLayout = display;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDisplayTopLayout(displayTopLayout);
        }
        return this;
    }

    public NiceDateAndTimePickerDialog setTitle(String title) {
        this.title = title;
        if (niceDateAndTimePicker != null) {
            if (!TextUtils.isEmpty(title)) {
                niceDateAndTimePicker.setCenterTitle(this.title);
            }
        }
        return this;
    }


    public NiceDateAndTimePickerDialog setTitle(@StringRes int titleId) {
        this.titleId = titleId;
        if (niceDateAndTimePicker != null) {
            if (titleId != -1) {
                niceDateAndTimePicker.setCenterTitle(this.titleId);
            }
        }
        return this;
    }


    public NiceDateAndTimePickerDialog setDisplayNow(boolean displayNow) {
        this.displayNow = displayNow;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDisplayNow(this.displayNow);
        }
        return this;
    }

    public NiceDateAndTimePickerDialog setDisplayTomorrow(boolean displayTomorrow) {
        this.displayTomorrow = displayTomorrow;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDisplayTomorrow(this.displayTomorrow);
        }
        return this;
    }

    public NiceDateAndTimePickerDialog setDisplayTheDayAfterTomorrow(boolean displayTheDayAfterTomorrow) {
        this.displayTheDayAfterTomorrow = displayTheDayAfterTomorrow;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDisplayTheDayAfterTomorrow(this.displayTheDayAfterTomorrow);
        }
        return this;
    }


    public NiceDateAndTimePickerDialog setDisplayPrevious(boolean displayPrevious) {
        this.displayPrevious = displayPrevious;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDisplayPrevious(this.displayPrevious);
        }

        return this;
    }


    public NiceDateAndTimePickerDialog setDisplayFuture(boolean displayFuture) {
        this.displayFuture = displayFuture;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDisplayFuture(this.displayFuture);
        }
        return this;
    }


    public NiceDateAndTimePickerDialog displayYears(boolean display) {
        this.displayYears = display;
        if (niceDateAndTimePicker != null) {
//            niceDateAndTimePicker.setdis
        }
        return this;
    }

    public NiceDateAndTimePickerDialog displayMonths(boolean display) {
        this.displayMonths = display;
        return this;
    }


    public NiceDateAndTimePickerDialog displayMonthNumbers(boolean display) {
        this.displayMonthNumbers = display;
        return this;
    }


    public NiceDateAndTimePickerDialog displayDaysOfMonth(boolean display) {
        this.displayDaysOfMonth = display;
        return this;
    }

    public NiceDateAndTimePickerDialog displayDays(boolean display) {
        this.displayDays = display;

        return this;
    }

    public NiceDateAndTimePickerDialog displayHours(boolean display) {
        this.displayHours = display;
        return this;
    }


    public NiceDateAndTimePickerDialog displayMinutes(boolean display) {
        this.displayMinutes = display;
        return this;
    }


    public NiceDateAndTimePickerDialog displayAmAndPm(boolean display) {
        this.amPm = display;
        return this;
    }


    public NiceDateAndTimePickerDialog setMinDate(Date minDate) {
        this.minDate = minDate;

        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setMinDate(this.minDate);
        }

        return this;
    }


    public NiceDateAndTimePickerDialog setMaxDate(Date maxDate) {
        this.maxDate = maxDate;

        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setMaxDate(this.maxDate);
        }

        return this;
    }


    public NiceDateAndTimePickerDialog setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;


        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setDefaultDate(this.defaultDate);
        }

        return this;
    }


    public NiceDateAndTimePickerDialog setMinutesStep(int minutesStep) {
        this.minutesStep = minutesStep;

        if (niceDateAndTimePicker != null) {
            if (minutesStep <= 0) {
                minutesStep = STEP_MINUTES_DEFAULT;
            }
            niceDateAndTimePicker.setStepMinutes(minutesStep);
        }

        return this;
    }


    private boolean curved = true;


    public NiceDateAndTimePickerDialog setCurved(boolean curved) {
        this.curved = curved;
        if (niceDateAndTimePicker != null) {
            niceDateAndTimePicker.setCurved(curved);
        }
        return this;
    }


    public NiceDateAndTimePickerDialog setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;

        if(null != niceDateAndTimePicker) {
            niceDateAndTimePicker.setOnDateSelectedListener(onDateSelectedListener);
        }

        return this;
    }

    public NiceDateAndTimePickerDialog setOnLeftTitleClickListener(OnLeftTitleClickListener onLeftTitleClickListener) {
        this.onLeftTitleClickListener = onLeftTitleClickListener;
        return this;
    }

    public NiceDateAndTimePickerDialog setOnRightTitleClickListener(OnRightTitleClickListener onRightTitleClickListener) {
        this.onRightTitleClickListener = onRightTitleClickListener;
        return this;
    }


    private boolean show = false;


    public View getView(int id) {
        if (rootView != null) {
            return rootView.findViewById(id);
        }
        return null;
    }


    /**
     *
     */
    @Override
    public void show() {
        super.show();
        if (style == BOTTOM_SHEET) {

            if (contextWeakReference != null) {

                if (contextWeakReference.get() != null) {

                    niceDateAndTimePicker.setDisplayTopLayout(displayTopLayout);
                    niceDateAndTimePicker.setCurved(curved);

//                    niceDateAndTimePicker.setDisplayYears(displayYears);
//                    niceDateAndTimePicker.setDisplayMonths(displayMonths);
//                    niceDateAndTimePicker.setDisplayDaysOfMonth(displayDaysOfMonth);
//                    niceDateAndTimePicker.setDisplayDays(displayDays);
//                    niceDateAndTimePicker.setDisplayHours(displayHours);
//                    niceDateAndTimePicker.setDisplayMinutes(displayMinutes);
//
                    niceDateAndTimePicker.setDisplayNow(displayNow);
                    niceDateAndTimePicker.setDisplayTomorrow(displayTomorrow);
                    niceDateAndTimePicker.setDisplayTheDayAfterTomorrow(displayTheDayAfterTomorrow);
                    niceDateAndTimePicker.setDisplayPrevious(displayPrevious);
                    niceDateAndTimePicker.setDisplayFuture(displayFuture);


                    if (minDate != null) {
                        niceDateAndTimePicker.setMinDate(minDate);
                    }
                    if (maxDate != null) {
                        niceDateAndTimePicker.setMaxDate(maxDate);
                    }

                    if (defaultDate != null) {
                        niceDateAndTimePicker.setDefaultDate(defaultDate);
                    }
//
//                    niceDateAndTimePicker.setMustBeOnFuture(mustBeOnFuture);
                    niceDateAndTimePicker.setStepMinutes(minutesStep);
                    if (TextUtils.isEmpty(title)) {
                        niceDateAndTimePicker.hideTitle();
                    }
                    niceDateAndTimePicker.setCenterTitle(title);

                    niceDateAndTimePicker.setOnDateSelectedListener(onDateSelectedListener);

//                    niceDateAndTimePicker.setAmPm(amPm);
//                    if (curved) {
//                        niceDateAndTimePicker.setVisibleItemCount(5);
//                    }else {
//                        niceDateAndTimePicker.setVisibleItemCount(7);
//                    }

                    niceDateAndTimePickerBottomDialog.setContentView(rootView);
                    niceDateAndTimePickerBottomDialog.setCanceledOnTouchOutside(false);
                    if (!show) {
                        niceDateAndTimePickerBottomDialog.show();
                        show = true;
                    }
                }
            }
        }else {

            // TODO: 2019-06-30 普通弹框
            
        }

    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (niceDateAndTimePickerBottomDialog != null) {
            niceDateAndTimePickerBottomDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.niceDateAndTimePickerTopLeftButton) {
            if (onLeftTitleClickListener != null) {
                onLeftTitleClickListener.onClick(v, this);
            }
        }else if (id == R.id.niceDateAndTimePickerTopRightButton) {
            if (onRightTitleClickListener != null) {
                onRightTitleClickListener.onClick(v, this, niceDateAndTimePicker.getDate());
            }
        }


    }


    public static class Builder {

        private int style = BOTTOM_SHEET;

        private WeakReference<Context> context;

        private int dialogLayout;

        private int pickerId;

        private String leftTitle;
        private String title;
        @StringRes
        private int titleId = -1;
        private String rightTitle;

        private boolean mustBeOnFuture = false;

        private int minutesStep = STEP_MINUTES_DEFAULT;

        private boolean displayTopLayout = true;
        private boolean displayDays = true;
        private boolean displayMinutes = true;
        private boolean displayHours = true;
        private boolean displayMonths = false;
        private boolean displayDaysOfMonth = false;
        private boolean displayYears = false;
        private boolean displayMonthNumbers = false;
        private boolean amPm = false;

        private boolean displayTomorrow = false;
        private boolean displayTheDayAfterTomorrow = false;
        private boolean displayNow = false;

        private boolean displayPrevious = false;
        private boolean displayFuture = false;

        private boolean curved = true;

        @Nullable
        private Date minDate;
        @Nullable
        private Date maxDate;
        @Nullable
        private Date defaultDate;
        @Nullable
        private SimpleDateFormat dayFormatter;
        @Nullable
        private Locale customLocale;



        public Builder(Context context, int dialogLayout, int pickerId) {
            this.context = new WeakReference<>(context);
            this.dialogLayout = dialogLayout;
            this.pickerId = pickerId;
        }


        public Builder setStyle(int style) {
            this.style = style;
            return this;
        }

        public Builder dialogStyle() {
            setStyle(COMMON_DIALOG);
            return this;
        }


        public Builder setLeftTitle(String leftTitle) {
            this.leftTitle = leftTitle;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleId) {
            this.titleId = titleId;
            return this;
        }

        public Builder setRightTitle(String rightTitle) {
            this.rightTitle = rightTitle;
            return this;
        }


        public Builder mustBeOnFuture(boolean mustBeOnFuture) {
            this.mustBeOnFuture = mustBeOnFuture;
            return this;
        }


        public Builder hideTopLayout() {
            this.displayTopLayout = false;
            return this;
        }


        public Builder disableCurved() {
            this.curved = false;
            return this;
        }

        public Builder displayNow() {
            this.displayNow = true;
            return this;
        }

        public Builder displayTomorrow() {
            this.displayTomorrow = true;
            return this;
        }

        public Builder displayTheDayAfterTomorrow() {
            this.displayTheDayAfterTomorrow = true;
            return this;
        }

        public Builder displayPrevious() {
            this.displayPrevious = true;
            return this;
        }


        public Builder displayFuture() {
            this.displayFuture = true;
            return this;
        }


        public Builder displayYears() {
            this.displayYears = true;
            return this;
        }


        public Builder displayMonths() {
            this.displayMonths = true;
            return this;
        }


        public Builder displayMonthNumbers() {
            this.displayMonthNumbers = true;
            return this;
        }


        public Builder displayDaysOfMonth() {
            this.displayDaysOfMonth = true;
            return this;
        }


        public Builder displayDays() {
            this.displayDays = true;
            return this;
        }


        public Builder displayHours() {
            this.displayHours = true;
            return this;
        }


        public Builder displayMinutes() {
            this.displayMinutes = true;
            return this;
        }


        public Builder displayAmAndPm() {
            this.amPm = true;
            return this;
        }


        public Builder defaultDate(Date date) {
            this.defaultDate = date;
            return this;
        }


        public Builder minDate(Date date) {
            this.minDate = date;

            if (defaultDate == null || defaultDate.before(minDate)) {
                defaultDate = minDate;
            }

            return this;
        }


        public Builder maxDate(Date date) {
            this.maxDate = date;
            return this;
        }


        public Builder minutesStep(int minutesStep) {
            this.minutesStep = minutesStep;
            return this;
        }

        public NiceDateAndTimePickerDialog Build() {

            return new NiceDateAndTimePickerDialog(context, dialogLayout, pickerId)
                    .setStyle(style)
                    .setMustBeOnFuture(mustBeOnFuture)
                    .setCurved(curved)
                    .setDisplayNow(displayNow)
                    .setDisplayTomorrow(displayTomorrow)
                    .setDisplayTheDayAfterTomorrow(displayTheDayAfterTomorrow)
                    .setDisplayPrevious(displayPrevious)
                    .setDisplayFuture(displayFuture)
                    .displayTopLayout(displayTopLayout)
                    .displayYears(displayYears)
                    .displayMonths(displayMonths)
                    .displayMonthNumbers(displayMonthNumbers)
                    .displayDaysOfMonth(displayDaysOfMonth)
                    .displayDays(displayDays)
                    .displayHours(displayHours)
                    .displayMinutes(displayMinutes)
                    .displayAmAndPm(amPm)
                    .setDefaultDate(defaultDate)
                    .setMinDate(minDate)
                    .setMaxDate(maxDate)
                    .setMinutesStep(minutesStep)
                    .setTitle(title)
                    .setTitle(titleId);
        }

    }




}
