package me.nice.customview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import me.nice.view.widget.wheel.NiceWheelDayPicker;

public class PickerActivity extends AppCompatActivity {

    private String TAG = PickerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        NiceWheelDayPicker niceWheelDayPicker = findViewById(R.id.niceWheelDayPicker);
        niceWheelDayPicker.setOnDaySelectedListener(new NiceWheelDayPicker.OnDaySelectedListener() {
            @Override
            public void onDaySelected(NiceWheelDayPicker picker, int position, String name, Date date) {
                Log.d(TAG, position + " name  " +name +  " date " + date.toString());
            }
        });
    }
}
