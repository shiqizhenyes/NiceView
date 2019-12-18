package me.nice.customview;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.nice.customview.adapter.MainViewAdapter;
import me.nice.customview.bean.MainView;
import me.nice.customview.inter.OnItemClickListener;
import me.nice.view.dialog.NiceDateAndTimePickerDialog;
import me.nice.view.inter.OnDateSelectedListener;
import me.nice.view.inter.OnLeftTitleClickListener;
import me.nice.view.inter.OnRightTitleClickListener;
import me.nice.view.widget.wheel.NiceWheelYearPicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mainRecyclerView;

    private List<MainView> mainViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        initMainRecyclerView();
        displayViews();
    }

    private MainViewAdapter viewAdapter;

    private void initMainRecyclerView() {
        mainViews = new ArrayList<>();
        viewAdapter = new MainViewAdapter();
        viewAdapter.setOnItemClickListener(new OnItemClickListener<MainView>() {
            @Override
            public void onItemClick(View view, int position, MainView mainView) {
                if (view.getId() == R.id.mainViewButton) {
                    if (mainView.getId() == R.string.nice_pointer) {
                        goToNicePointer();
                    }
                }
            }
        });
        viewAdapter.setMainViews(mainViews);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mainRecyclerView.setLayoutManager(gridLayoutManager);
        mainRecyclerView.setAdapter(viewAdapter);
    }

    private void goToNicePointer() {
        Intent intent = new Intent(this, NicePointerActivity.class);
        startActivity(intent);
    }


    private void displayViews() {
        mainViews.add(new MainView(getString(R.string.nice_pointer), R.string.nice_pointer));
        mainViews.add(new MainView(getString(R.string.nice_picker), R.string.nice_picker));
        viewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }
}
