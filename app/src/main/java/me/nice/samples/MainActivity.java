package me.nice.samples;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.nice.camera.Camera2RecordVideoActivity;
import me.nice.camera.RecordVideoActivity;
import me.nice.camera.VideoCaptureActivity;
import me.nice.samples.adapter.MainViewAdapter;
import me.nice.samples.bean.MainView;
import me.nice.samples.inter.OnItemClickListener;

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
                    if (mainView.getId() == R.string.nice_picker) {
                        goToNicePicker();
                    } else if (mainView.getId() == R.string.nice_pointer) {
                        goToNicePointer();
                    } else if (mainView.getId() == R.string.nice_circle_ripple) {
                        goToNiceCircleRipple();
                    } else if (mainView.getId() == R.string.nice_bubble_layout) {
                        goToNiceBubbleLayout();
                    } else if (mainView.getId() == R.string.nice_radio_button) {
                        goToRadioButton();
                    } else if (mainView.getId() == R.string.nice_camera) {
                        goToCamera();
                    }
                }
            }
        });
        viewAdapter.setMainViews(mainViews);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mainRecyclerView.setLayoutManager(gridLayoutManager);
        mainRecyclerView.setAdapter(viewAdapter);
    }



    private void goToNicePicker() {
        Intent intent = new Intent(this, NicePickerActivity.class);
        startActivity(intent);
    }

    private void goToNicePointer() {
        Intent intent = new Intent(this, NicePointerActivity.class);
        startActivity(intent);
    }

    private void goToNiceCircleRipple() {
        Intent intent = new Intent(this, NiceCircleRippleViewActivity.class);
        startActivity(intent);
    }

    private void goToNiceBubbleLayout() {
        Intent intent = new Intent(this, NiceBubbleLayoutActivity.class);
        startActivity(intent);
    }

    private void goToRadioButton() {
        Intent intent = new Intent(this, NiceRadioButtonActivity.class);
        startActivity(intent);
    }

    private void goToCamera() {
        startActivity(new Intent(this, NiceCameraActivity.class));
//        startActivity(new Intent(this, Camera2RecordVideoActivity.class));
    }

    private void displayViews() {
        mainViews.add(new MainView(getString(R.string.nice_pointer), R.string.nice_pointer));
        mainViews.add(new MainView(getString(R.string.nice_picker), R.string.nice_picker));
        mainViews.add(new MainView(getString(R.string.nice_circle_ripple), R.string.nice_circle_ripple));
        mainViews.add(new MainView(getString(R.string.nice_bubble_layout), R.string.nice_bubble_layout));
        mainViews.add(new MainView(getString(R.string.nice_radio_button), R.string.nice_radio_button));
        mainViews.add(new MainView(getString(R.string.nice_camera), R.string.nice_camera));
        viewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
