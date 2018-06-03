package flepsik.github.com.progress_ring;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class ProgressRingView extends View {
    public static final int DEFAULT_RING_WIDTH = -1;
    @ColorInt
    public static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;
    @ColorInt
    public static final int DEFAULT_BACKGROUND_PROGRESS_COLOR = Color.parseColor("#e9e9e9");
    @ColorInt
    public static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#27cf6b");
    public static final int DEFAULT_ANIMATION_DURATION = 300;

    private static final float DEFAULT_SIZE_DP = 50;
    private static final float DEFAULT_RING_WIDTH_RATIO = .1f;
    private static final float DEFAULT_RING_RADIUS_RATIO = .9f;
    private static final int DEFAULT_START_ANGLE = -90;
    private static final int DEFAULT_SWEEP_ANGLE_DEGREE = 360;

    @FloatRange(from = 0f, to = 1f)
    private float progress;
    @Px
    private int ringWidth = DEFAULT_RING_WIDTH;
    private boolean animated = false;
    private int animationDuration = DEFAULT_ANIMATION_DURATION;

    private ValueAnimator progressAnimator;
    private BackgroundPainter background;
    private EmptyRingPainter emptyRing;
    private ProgressRingPainter progressRing;
    private AnimationUpdateListener listener = null;

    public ProgressRingView(final Context context) {
        super(context);
        initialize(context, null);
    }

    public ProgressRingView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public ProgressRingView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressRingView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    public void cornerEdges(final boolean state) {
        if (progressRing.shouldCornerEdges() != state) {
            progressRing.cornerEdges(state);
            emptyRing.cornerEdges(state);
            invalidate();
        }
    }

    public boolean shouldCornerEdges() {
        return progressRing.shouldCornerEdges();
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(final boolean animated) {
        this.animated = animated;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(final int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void fillProgress(final boolean state) {
        if (progressRing.shouldFill() != state) {
            emptyRing.setShouldFill(state);
            progressRing.setShouldFill(state);
            invalidate();
        }
    }

    public boolean shouldFillProgress() {
        return progressRing.shouldFill();
    }

    public void setProgressFillColor(@ColorInt final int color) {
        if (progressRing.getColor() != color) {
            progressRing.setInnerColor(color);
            invalidate();
        }
    }

    @ColorInt
    public int getProgressInnerColor() {
        return progressRing.getInnerColor();
    }

    public void setListener(final AnimationUpdateListener listener){
        this.listener = listener;
    }

    public void setProgress(@FloatRange(from = 0f, to = 1f) final float newProgress) {
        if (isInEditMode()) animated = false;
        if (this.progress != newProgress) {
            emptyRing.calculateStartAngleOvalPoint();
            if (animated) {
                if (progressAnimator != null) {
                    progressAnimator.cancel();
                }

                progressAnimator = ObjectAnimator.ofFloat(this.progress, newProgress);
                progressAnimator.setDuration(animationDuration);
                progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        progress = (float) valueAnimator.getAnimatedValue();
                        if (listener != null){
                            listener.onAnimationProgress(progress);
                        }
                        progressRing.setProgress(progress);
                        ProgressRingView.this.invalidate();
                    }
                });
                progressAnimator.start();
            } else {
                progressRing.setProgress(newProgress);
                invalidate();
            }
        }
    }

    @FloatRange(from = 0f, to = 1f)
    public float getProgress() {
        return progress;
    }

    public void setBackgroundProgressColor(@ColorInt final int color) {
        emptyRing.setColor(color);
        invalidate();
    }

    @ColorInt
    public int getBackgroundProgressColor() {
        return emptyRing.color;
    }

    public void setProgressColor(@ColorInt final int color) {
        if (progressRing.getColor() != color) {
            progressRing.setColor(color);
            invalidate();
        }
    }

    @ColorInt
    public int getProgressColor() {
        return progressRing.color;
    }

    public void setBackgroundColor(@ColorInt final int color) {
        if (background.getColor() != color) {
            background.setColor(color);
            invalidate();
        }
    }

    @ColorInt
    public int getBackgroundColor() {
        return background.getColor();
    }

    public void setRingWidth(@Px final int ringWidth) {
        this.ringWidth = ringWidth;
        emptyRing.setRingWidth(ringWidth);
        progressRing.setRingWidth(ringWidth);
    }

    @Px
    public int getRingWidth() {
        return ringWidth;
    }

    protected void initialize(final Context context, @Nullable final AttributeSet attributeSet) {
        int backgroundColor = DEFAULT_BACKGROUND_COLOR;
        int backgroundProgressColor = DEFAULT_BACKGROUND_PROGRESS_COLOR;
        int progressColor = DEFAULT_PROGRESS_COLOR;
        int progressInnerColor = DEFAULT_PROGRESS_COLOR;
        int sweepAngleDegree = DEFAULT_SWEEP_ANGLE_DEGREE;
        int startAngle = DEFAULT_START_ANGLE;
        boolean fillProgress = false;
        boolean cornerEdges = true;
        float progress = 0f;

        if (attributeSet != null) {
            final TypedArray attrsArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressRingView);
            progress = attrsArray.getFloat(R.styleable.ProgressRingView_progress, .0f);
            sweepAngleDegree = attrsArray.getInt(
                    R.styleable.ProgressRingView_sweep_angle_degree,
                    DEFAULT_SWEEP_ANGLE_DEGREE);
            startAngle = attrsArray.getInt(
                    R.styleable.ProgressRingView_start_angle,
                    DEFAULT_START_ANGLE);
            ringWidth = attrsArray.getDimensionPixelSize(
                    R.styleable.ProgressRingView_ring_width,
                    DEFAULT_RING_WIDTH
            );
            backgroundColor = attrsArray.getColor(
                    R.styleable.ProgressRingView_background_color,
                    DEFAULT_BACKGROUND_COLOR
            );
            backgroundProgressColor = attrsArray.getColor(
                    R.styleable.ProgressRingView_background_progress_color,
                    DEFAULT_BACKGROUND_PROGRESS_COLOR
            );
            progressColor = attrsArray.getColor(
                    R.styleable.ProgressRingView_progress_color,
                    DEFAULT_PROGRESS_COLOR
            );
            progressInnerColor = attrsArray.getColor(
                    R.styleable.ProgressRingView_progress_fill_color,
                    DEFAULT_PROGRESS_COLOR
            );
            animationDuration = attrsArray.getInt(
                    R.styleable.ProgressRingView_animation_duration,
                    DEFAULT_ANIMATION_DURATION
            );
            fillProgress = attrsArray.getBoolean(
                    R.styleable.ProgressRingView_progress_fill,
                    false
            );
            cornerEdges = attrsArray.getBoolean(
                    R.styleable.ProgressRingView_corner_edges,
                    true
            );
            animated = attrsArray.getBoolean(R.styleable.ProgressRingView_animated, false);
            attrsArray.recycle();
        }

        background = new BackgroundPainter(backgroundColor);
        emptyRing = new EmptyRingPainter(backgroundProgressColor, startAngle, sweepAngleDegree);
        progressRing = new ProgressRingPainter(progressColor, startAngle, sweepAngleDegree);
        progressRing.setInnerColor(progressInnerColor);
        emptyRing.setShouldFill(fillProgress);
        progressRing.setShouldFill(fillProgress);
        emptyRing.cornerEdges(cornerEdges);
        progressRing.cornerEdges(cornerEdges);
        setProgress(progress);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int size = convertDpToPixel(DEFAULT_SIZE_DP, getContext());

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        final int width;
        final int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(size, widthSize);
        } else {
            width = size;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(size, heightSize);
        } else {
            height = size;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        final Point center = new Point(width / 2, height / 2);
        final int smallestSide = Math.min(width, height);
        final int radius = smallestSide / 2;

        final int ringRadius;
        final int ringWidth;
        if (this.ringWidth != DEFAULT_RING_WIDTH) {
            ringRadius = radius - this.ringWidth;
            ringWidth = this.ringWidth;
        } else {
            ringRadius = (int) (radius * DEFAULT_RING_RADIUS_RATIO);
            ringWidth = (int) (radius * DEFAULT_RING_WIDTH_RATIO);
        }
        background.onSizeChanged(center, ringRadius);
        emptyRing.onSizeChanged(center, ringRadius);
        progressRing.onSizeChanged(center, ringRadius);
        setRingWidth(ringWidth);
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        background.draw(canvas);
        emptyRing.draw(canvas);
        progressRing.draw(canvas);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final SavedState savedState = new SavedState(superState);
        savedState.animationDuration = animationDuration;
        savedState.animated = animated;
        savedState.progress = progress;
        savedState.ringWidth = ringWidth;
        return savedState;
    }

    protected void onRestoreInstanceState(final Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        animationDuration = savedState.animationDuration;
        animated = savedState.animated;
        setProgress(savedState.progress);
        setRingWidth(savedState.ringWidth);
    }

    private static int convertDpToPixel(final float dp, final Context context) {
        return Math.round(context.getResources().getDisplayMetrics().density * dp);
    }

    private static class ProgressRingPainter extends EmptyRingPainter {
        @FloatRange(from = 0f, to = 1f)
        private float progress;
        @ColorInt
        private int innerColor = color;

        private int sweepAngle;

        ProgressRingPainter(@ColorInt final int color, final int startAngle, final int sweepAngleDegree) {
            super(color, startAngle, sweepAngleDegree);
            initialize();
        }

        @Override
        void onSizeChanged(final Point center, final int newRadius) {
            super.onSizeChanged(center, newRadius);
            rect.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
            setProgress(progress);
        }

        @Override
        void draw(final Canvas canvas) {
            if (shouldFill) {
                paint.setColor(innerColor == DEFAULT_PROGRESS_COLOR ? color : innerColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
            }
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(rect, startAngle, sweepAngle, false, paint);
            paint.setStyle(Paint.Style.FILL);
            if (shouldCornerEdges && sweepAngle > 0) {
                canvas.drawCircle(startCircle.x, startCircle.y, ringWidth / 2, paint);
                canvas.drawCircle(endCircle.x, endCircle.y, ringWidth / 2, paint);
            }
        }

        void setProgress(@FloatRange(from = 0f, to = 1f) final float progress) {
            this.progress = progress;
            sweepAngle = (int) (sweepAngleDegree * progress);
            startCircle = calculateStartAngleOvalPoint(startAngle);
            endCircle = calculateStartAngleOvalPoint(sweepAngle + startAngle);
        }

        @ColorInt
        int getInnerColor() {
            return innerColor;
        }

        void setInnerColor(@ColorInt final int innerColor) {
            this.innerColor = innerColor;
        }

        private void initialize() {
            paint.setColor(color);
        }
    }

    private static class EmptyRingPainter extends Painter {
        @ColorInt
        protected int color;
        int ringWidth;
        int startAngle;
        int sweepAngleDegree;
        RectF rect = new RectF();
        Point endCircle = new Point();
        Point startCircle = new Point();
        boolean shouldFill = false;
        boolean shouldCornerEdges = true;

        EmptyRingPainter(@ColorInt final int color, final int startAngle, final int sweepAngleDegree) {
            setColor(color);
            setStartAngle(startAngle);
            setSweepAngleDegree(sweepAngleDegree);
            initialize();
        }

        @Override
        void onSizeChanged(final Point center, final int newRadius) {
            super.onSizeChanged(center, newRadius);
            rect.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
            calculateStartAngleOvalPoint();
        }

        @Override
        void draw(final Canvas canvas) {
            if (shouldFill) {
                paint.setStyle(Paint.Style.FILL);
                canvas.drawArc(rect, startAngle, sweepAngleDegree, true, paint);
            }
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(rect, startAngle, sweepAngleDegree, false, paint);
            paint.setStyle(Paint.Style.FILL);
            if (shouldCornerEdges) {
                canvas.drawCircle(startCircle.x, startCircle.y, ringWidth / 2, paint);
                canvas.drawCircle(endCircle.x, endCircle.y, ringWidth / 2, paint);
            }
        }

        void setColor(@ColorInt final int color) {
            this.color = color;
            paint.setColor(color);
        }

        @ColorInt
        public int getColor() {
            return color;
        }

        void setRingWidth(@Px final int width) {
            paint.setStrokeWidth(width);
            this.ringWidth = width;
        }

        public int getStartAngle() {
            return startAngle;
        }

        void setStartAngle(final int startAngle) {
            this.startAngle = startAngle;
        }

        public int getSweepAngleDegree() {
            return sweepAngleDegree;
        }

        void setSweepAngleDegree(final int sweepAngleDegree) {
            this.sweepAngleDegree = sweepAngleDegree;
        }

        boolean shouldCornerEdges() {
            return shouldCornerEdges;
        }

        void cornerEdges(final boolean state) {
            shouldCornerEdges = state;
        }

        boolean shouldFill() {
            return shouldFill;
        }

        void setShouldFill(final boolean shouldFill) {
            this.shouldFill = shouldFill;
        }

        private void initialize() {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(color);
        }

        private void calculateStartAngleOvalPoint() {
            startCircle = calculateStartAngleOvalPoint(startAngle);
            endCircle = calculateStartAngleOvalPoint(sweepAngleDegree + startAngle);
        }

        Point calculateStartAngleOvalPoint(final int angle) {
            final Point result = new Point();
            final double radians = Math.toRadians(angle);
            result.x = (int) (center.x + radius * Math.cos(radians));
            result.y = (int) (center.y + radius * Math.sin(radians));
            return result;
        }
    }

    private static class BackgroundPainter extends Painter {
        @ColorInt
        protected int color;

        BackgroundPainter(@ColorInt final int color) {
            this.color = color;
            initialize();
        }

        int getColor() {
            return color;
        }

        void setColor(final int color) {
            this.color = color;
            paint.setColor(color);
        }

        @Override
        void draw(final Canvas canvas) {
            if (color != DEFAULT_BACKGROUND_COLOR) {
                canvas.drawCircle(center.x, center.y, radius, paint);
            }
        }

        private void initialize() {
            paint.setColor(color);
        }
    }

    private abstract static class Painter {
        Point center = new Point();
        int radius;

        Paint paint;

        Painter() {
            paint = new Paint();
            paint.setAntiAlias(true);
        }

        @CallSuper
        void onSizeChanged(final Point center, final int newRadius) {
            this.center = center;
            this.radius = newRadius;
        }

        abstract void draw(Canvas canvas);
    }

    public abstract static class AnimationUpdateListener {

        public void onAnimationProgress(float progress) {
            // Do nothing
        }
    }

    private static class SavedState extends BaseSavedState {
        float progress;
        int ringWidth;
        boolean animated;
        int animationDuration;

        SavedState(final Parcelable superState) {
            super(superState);
        }

        private SavedState(final Parcel in) {
            super(in);
            this.progress = in.readFloat();
            this.ringWidth = in.readInt();
            this.animated = in.readByte() != 0;
            this.animationDuration = in.readInt();
        }

        @Override
        public void writeToParcel(final Parcel out, final int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.progress);
            out.writeInt(this.ringWidth);
            out.writeByte(this.animated ? (byte) 1 : (byte) 0);
            out.writeInt(this.animationDuration);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(final Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(final int size) {
                return new SavedState[size];
            }
        };
    }
}
