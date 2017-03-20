package flepsik.github.com.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import flepsik.github.com.progress_ring.ProgressRingView;


public class SampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ProgressRingView progress = (ProgressRingView) findViewById(R.id.first);
        progress.setProgress(.3f);
        progress.setAnimated(false);
        progress.setRingWidth(10);
        progress.setAnimationDuration(ProgressRingView.ANIMATION_DURATION);
        progress.setProgressColor(Color.RED);
        progress.setBackgroundEmptyColor(Color.LTGRAY);

        final ProgressRingView secondProgress = (ProgressRingView) findViewById(R.id.second);
        secondProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProgress(secondProgress);
            }
        });
    }

    private float lastProgress = 0f;
    private void changeProgress(ProgressRingView progressRingView) {
        float targetProgress = lastProgress == 1f ? 0f : 1f;
        lastProgress = targetProgress;
        progressRingView.setProgress(targetProgress);
    }
}
