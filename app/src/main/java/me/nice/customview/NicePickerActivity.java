package me.nice.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.nice.customview.dialog.NiceChooseTimeDialog;

public class NicePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_picker);
    }


    public void onClick(View view) {
        NiceChooseTimeDialog.Builder builder = new NiceChooseTimeDialog.Builder();
        NiceChooseTimeDialog niceChooseTimeDialog = builder.Build(this);
        niceChooseTimeDialog.show();
    }
}
