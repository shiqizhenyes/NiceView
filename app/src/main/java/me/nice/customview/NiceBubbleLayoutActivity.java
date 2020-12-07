package me.nice.customview;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.nice.view.widget.NiceBubbleLayout;
import me.nice.view.widget.NiceDotLoadingView;

public class NiceBubbleLayoutActivity extends AppCompatActivity
        implements View.OnClickListener {

    private NiceBubbleLayout niceBubbleLayout;

    private TextView niceText;
    private NiceDotLoadingView niceDotLoadingView;

    private Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_bubble_layout);
        niceBubbleLayout = findViewById(R.id.niceBubbleLayout);
        niceText = findViewById(R.id.niceText);
        niceDotLoadingView = findViewById(R.id.niceDotLoadingView);
        change = findViewById(R.id.change);
        change.setOnClickListener(this);
        startAnimation();
    }


    private void startAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.5f, 1.0f, 0.5f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setDuration(400);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        niceBubbleLayout.setAnimation(scaleAnimation);
        scaleAnimation.start();
    }

    private boolean hide = true;

    @Override
    public void onClick(View v) {
        if (hide) {
            hide = false;
            niceText.setVisibility(View.VISIBLE);
            if (niceText.getText().equals(getText(R.string.nice_picker))) {
                niceText.setText(R.string.nice_circle_ripple);
            } else {
                niceText.setText(R.string.nice_picker);
            }
            niceDotLoadingView.setVisibility(View.INVISIBLE);
            niceDotLoadingView.startAnim();
        } else {
            hide = true;
            niceText.setVisibility(View.INVISIBLE);
            niceDotLoadingView.setVisibility(View.VISIBLE);
        }

    }
}
