package com.zhou.test.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zhou.test.R;
import com.zhou.test.util.Tasks;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 遮罩动画
 */
public class TestImageActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private Bitmap original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("AAAA", "create");
        setContentView(R.layout.activity_test_image);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        Log.i("AAAA", mControlsView.getWidth() + "");

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i("AAAA", "focus");
        Log.i("AAAA", mControlsView.getWidth() + "");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("AAAA", "post");
        Log.i("AAAA", mControlsView.getWidth() + "");
//         Trigger the initial hide() shortly after the activity has been
//         created, to briefly hint to the user that UI controls
//         are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AAAA", "start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AAAA", "resume");
        startDeng();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop = true;
        // 先判断是否已经回收
        if(original != null && !original.isRecycled()){
            // 回收并且置为null
            original.recycle();
            original = null;
        }
    }

    private boolean isVerticalPlus = false;
    private boolean isVerticalSub = true;
    private int verticalCount = 0;
    private boolean isHorizontalPlus = false;
    private boolean isHorizontalSub = false;
    private int horizontalCount = 0;
    boolean stop = false;
    boolean purple = false;
    private int frameAnimTime;


    public void startDeng() {
        stop = false;
        final View view = findViewById(R.id.rl);
        final ImageView iv = (ImageView) view.findViewById(R.id.light);
        iv.setVisibility(View.VISIBLE);
        final int verticalNum = 12;
        final int horizontalNum = 6;
        original = BitmapFactory.decodeResource(getResources(), R.drawable.sports_car_round_light);
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (stop) {
                    timer.cancel();
                    return;
                }
                Tasks.post2MainThread(new Runnable() {
                    @Override
                    public void run() {
                        controller(view, iv, original, verticalNum, horizontalNum);
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 400);
    }

    public void controller(View view, ImageView iv, Bitmap lightImg, int verticalNum, int horizontalNum) {
        if (isVerticalSub) {
            showDeng(verticalCount, view, iv, lightImg, verticalNum);
            verticalCount++;
            if (verticalCount >= verticalNum / 2) {
                isHorizontalSub = true;
                isVerticalSub = false;
                return;
            }
        }
        if (isHorizontalSub) {
            horizontalCount++;
            showDeng2(horizontalCount, view, iv, lightImg, horizontalNum);
            if (horizontalCount > horizontalNum / 2) {
                isHorizontalPlus = true;
                isHorizontalSub = false;
                return;
            }
        }
        if (isHorizontalPlus) {
            horizontalCount--;
            showDeng2(horizontalCount, view, iv, lightImg, horizontalNum);
            if (horizontalCount == 1) {
                isVerticalPlus = true;
                isHorizontalPlus = false;
                horizontalCount--;
                return;
            }
        }
        if (isVerticalPlus) {
            verticalCount--;
            showDeng(verticalCount, view, iv, lightImg, verticalNum);
            if (verticalCount == 1) {
                isVerticalSub = true;
                isVerticalPlus = false;
                verticalCount--;
                return;
            }
        }
    }

    public void showDeng(int percentHeight, View view, ImageView iv, Bitmap original, int verticalNum) {
        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(),
                percentHeight == 0 ? 1 : original.getHeight() / (verticalNum / 2) * percentHeight, Bitmap.Config.ARGB_8888);
        Bitmap result = original.copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(bitmap, 0, original.getHeight() / verticalNum * ((verticalNum / 2) - percentHeight), paint);
        paint.setXfermode(null);
        iv.setImageBitmap(result);
    }

    public void showDeng2(int percentHeight, View view, ImageView iv, Bitmap original, int horizontalNum) {
//        Bitmap original = BitmapFactory.decodeResource(view.getResources(), lightImg);
        Bitmap bitmap1 = Bitmap.createBitmap(original.getWidth() / horizontalNum * percentHeight, original.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap2 = Bitmap.createBitmap(original.getWidth() / horizontalNum * percentHeight, original.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap result = original.copy(Bitmap.Config.ARGB_8888, true);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(bitmap1, 0, 0, paint);
        mCanvas.drawBitmap(bitmap2, original.getWidth() / horizontalNum * (horizontalNum - percentHeight), 0, paint);
        paint.setXfermode(null);
        iv.setImageBitmap(result);
    }

}
