package es.guiguegon.touchableimageview;
/**
 * Created by guiguegon on 30/11/2016.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by guiguegon on 30/11/2016.
 */

public class TouchableImageView extends ImageView implements View.OnTouchListener {

    //Default vars
    protected static final int DEFAULT_COLOR = 0xFF0000;
    protected static final int DEFAULT_ANIMATION_TIME = 300;
    protected static final int DEFAULT_STARTING_ALPHA = 255;
    protected static final float DEFAULT_RADIUS = 25f;
    protected static final boolean DEFAULT_ENABLED = true;
    protected ValueAnimator valueAnimator;
    private boolean enabled = DEFAULT_ENABLED;
    private int feedbackColor = DEFAULT_COLOR;
    private float radius = DEFAULT_RADIUS;
    private int startingAlpha = DEFAULT_STARTING_ALPHA;
    private int animationTime = DEFAULT_ANIMATION_TIME;
    private int currentAlpha;

    private OnTouchListener onTouchListener;
    private float[] lastPoint;
    private Paint mPaint;

    public TouchableImageView(Context context) {
        super(context);
        init();
    }

    public TouchableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TouchableImageView, 0, 0);
        try {
            getCustomAttributes(a);
        } finally {
            a.recycle();
        }
        init();
    }

    public TouchableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TouchableImageView, 0, 0);
        try {
            getCustomAttributes(a);
        } finally {
            a.recycle();
        }
        init();
    }

    protected void getCustomAttributes(TypedArray typedArray) {
        radius = typedArray.getDimension(R.styleable.TouchableImageView_tiv_radius, DEFAULT_RADIUS);
        startingAlpha = typedArray.getInt(R.styleable.TouchableImageView_tiv_starting_alpha, DEFAULT_STARTING_ALPHA);
        animationTime = typedArray.getInt(R.styleable.TouchableImageView_tiv_animation_time, DEFAULT_ANIMATION_TIME);
        feedbackColor = typedArray.getColor(R.styleable.TouchableImageView_tiv_color, DEFAULT_COLOR);
        enabled = typedArray.getBoolean(R.styleable.TouchableImageView_tiv_enabled, DEFAULT_ENABLED);
    }

    protected void init() {
        super.setOnTouchListener(this);
        mPaint = new Paint();
    }

    public void setFeedbackColor(int color) {
        this.feedbackColor = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setStartingAlpha(int startingAlpha) {
        this.startingAlpha = startingAlpha;
    }

    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.onTouchListener = l;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onTouchListener = null;
        lastPoint = null;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lastPoint != null && enabled) {
            mPaint.setColor(feedbackColor);
            mPaint.setAlpha(currentAlpha);
            canvas.drawCircle(lastPoint[0], lastPoint[1], radius, mPaint);
        }
    }

    private void startValueAnimator() {
        valueAnimator = ValueAnimator.ofInt(startingAlpha, 0);
        valueAnimator.setDuration(animationTime);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentAlpha = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (onTouchListener != null) {
            onTouchListener.onTouch(v, event);
        }
        lastPoint = new float[] { event.getX(), event.getY() };
        startValueAnimator();
        invalidate();
        return false;
    }
}