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
    private ValueAnimator progressAnimator;

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

    private void animateProgress(ProgressRingView progressRingView) {
        float startProgress = 0f;
        float endProgress = 1f;

        if (progressRingView.getProgress() == 1f) {
            startProgress = 1f;
            endProgress = 0f;
        } else {
            startProgress = progressRingView.getProgress();
        }

        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }

        progressAnimator = ObjectAnimator.ofFloat(progressRingView, "progress", startProgress, endProgress);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.start();
}
}
