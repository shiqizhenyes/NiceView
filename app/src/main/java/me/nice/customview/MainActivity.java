package me.nice.customview;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

import me.nice.view.dialog.BottomSheetHelper;
import me.nice.view.dialog.NiceDateAndTimePickerDialog;
import me.nice.view.helper.DateHelper;
import me.nice.view.inter.OnDateSelectedListener;
import me.nice.view.inter.OnLeftTitleClickListener;
import me.nice.view.inter.OnRightTitleClickListener;
import me.nice.view.widget.NiceWheelYearPicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = MainActivity.class.getSimpleName();


    private Button dataPickerButton;
    private Button dataPickerButton2;
    private Button dataPickerButton3;
    private NiceWheelYearPicker NiceWheelYearPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataPickerButton = findViewById(R.id.dataPickerButton);
        Button dataPickerDemoButton = findViewById(R.id.dataPickerDemoButton);

        dataPickerButton.setOnClickListener(this);
        dataPickerDemoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dataPickerButton:

                Date todayDate = new Date();
                Calendar todayCalendar = Calendar.getInstance();
                todayCalendar.setTime(todayDate);
                todayCalendar.set(Calendar.HOUR_OF_DAY,0);
                todayCalendar.set(Calendar.MINUTE, 0);

                todayDate = todayCalendar.getTime();

                todayCalendar.add(Calendar.DATE, 2);
                Date afterTomorrow = todayCalendar.getTime();

                Calendar maxCalendar = Calendar.getInstance();
                maxCalendar.setTime(afterTomorrow);
                maxCalendar.set(Calendar.HOUR_OF_DAY, 23);
                maxCalendar.set(Calendar.MINUTE, 55);

                NiceDateAndTimePickerDialog.Builder builder = new NiceDateAndTimePickerDialog.Builder();
                builder.init(this, R.layout.nice_date_and_time_picker_dialog_layout, R.id.niceDateAndTimePicker)
//                        .displayDaysOfMonth()
                        .hideTopLayout()
                        .displayDays()
                        .displayHours()
                        .displayMinutes()
//                        .displayAmAndPm()
//                        .displayFuture()
                        .displayTomorrow()
                        .displayTheDayAfterTomorrow()
//                        .displayNow()
//                        .minutesStep(10)
//                        .mustBeOnFuture(false)
                        .defaultDate(new Date())
//                        .()
                        .minDate(new Date())
                        .maxDate(maxCalendar.getTime());


                NiceDateAndTimePickerDialog dialog = builder.Build();

                dialog.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(Date date) {
                        Log.d(TAG, "选择的时间" + date.toString());
                    }
                }).show();


                dialog.setOnLeftTitleClickListener(new OnLeftTitleClickListener() {
                    @Override
                    public void onClick(View v, NiceDateAndTimePickerDialog dialog) {
                        dialog.dismiss();
                        Log.d(TAG, "左边按钮");
                    }
                });

                dialog.setOnRightTitleClickListener(new OnRightTitleClickListener() {
                    @Override
                    public void onClick(View v, NiceDateAndTimePickerDialog dialog, Date date) {
                        Log.d(TAG, "右边按钮" + date.toString());
                        dialog.dismiss();

                    }
                });


                break;

            case R.id.dataPickerDemoButton:

                Intent intent = new Intent(this, PickerActivity.class);
                startActivity(intent);

                break;

        }

    }
}
