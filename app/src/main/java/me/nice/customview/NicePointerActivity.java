package me.nice.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.nice.view.widget.NiceDotLoadingView;
import me.nice.view.widget.NicePointerView;

public class NicePointerActivity extends AppCompatActivity implements View.OnClickListener {

    private NicePointerView nicePointerView;

    private Button startJumpAnimator;
    private Button spreadAnimator;
    private NiceDotLoadingView niceDotLoadingView;

    private Button niceDotLoadingViewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_pointer);

        nicePointerView = findViewById(R.id.nicePointerView);
        niceDotLoadingView = findViewById(R.id.niceDotLoadingView);

        startJumpAnimator = findViewById(R.id.jumpAnimator);
        startJumpAnimator.setOnClickListener(this);


        spreadAnimator = findViewById(R.id.spreadAnimator);
        spreadAnimator.setOnClickListener(this);

        niceDotLoadingViewButton = findViewById(R.id.niceDotLoadingViewButton);
        niceDotLoadingViewButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.jumpAnimator) {
            nicePointerView.startJumpAnimation();
        }
        if (id == R.id.spreadAnimator) {
            nicePointerView.startZoomAnimation();
        }

        if (id == R.id.niceDotLoadingViewButton) {
            niceDotLoadingView.startAnim();
        }

    }

}
