package flepsik.github.com.sample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import flepsik.github.com.progress_ring.ProgressRingView;


public class SampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ProgressRingView firstProgress = (ProgressRingView) findViewById(R.id.first);
        firstProgress.setProgress(.5f);

        final ProgressRingView secondProgress = (ProgressRingView) findViewById(R.id.second);
        secondProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateProgress(secondProgress);
            }
        });
    }

    private float lastProgress = 0f;
    private void animateProgress(ProgressRingView progressRingView) {
        float targetProgress = lastProgress == 1f ? 0f : 1f;
        lastProgress = targetProgress;
        progressRingView.setProgress(targetProgress);
    }
}
