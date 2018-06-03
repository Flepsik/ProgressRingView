package flepsik.github.com.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import flepsik.github.com.progress_ring.ProgressRingView;


public class SampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final ProgressRingView progress = findViewById(R.id.first);
        progress.cornerEdges(false);
        progress.setProgress(.3f);
        progress.setAnimated(false);
        progress.setRingWidth(20);
        progress.setAnimationDuration(ProgressRingView.DEFAULT_ANIMATION_DURATION);
        progress.setBackgroundColor(Color.parseColor("#F29F8E"));
        progress.fillProgress(true);
        progress.setProgressFillColor(Color.parseColor("#F76156"));
        progress.setProgressColor(Color.RED);
        progress.setBackgroundProgressColor(Color.LTGRAY);

        final ProgressRingView secondProgress = findViewById(R.id.second);
        secondProgress.setInterpolator(new DecelerateInterpolator());
        secondProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProgress(secondProgress);
            }
        });
    }

    private float lastProgress = 0f;
    private void changeProgress(final ProgressRingView progressRingView) {
        final float targetProgress = lastProgress == 1f ? 0f : 1f;
        lastProgress = targetProgress;
        progressRingView.setProgress(targetProgress);
    }
}
