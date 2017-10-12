package com.zhou.test.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.zhou.test.R;
import com.zhou.test.view.FrameAnimation;

public class FLLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.porsche);
        addPorsche2();
    }

    public void addPorsche2() {
        final ImageView door = (ImageView) findViewById(R.id.door);
        final ImageView car_last = (ImageView) findViewById(R.id.car_last);
        final ImageView light = (ImageView) findViewById(R.id.light);
        final FrameAnimation car = (FrameAnimation) findViewById(R.id.car);
        car.setGapTime(165);
        int[] car_bg = new int[]{
                R.drawable.sports_car_01,
                R.drawable.sports_car_02,
                R.drawable.sports_car_03,
                R.drawable.sports_car_05,
                R.drawable.sports_car_04,
                R.drawable.sports_car_05,
                R.drawable.sports_car_06,
                R.drawable.sports_car_07,
                R.drawable.sports_car_08,
                R.drawable.sports_car_09,
                R.drawable.sports_car_10,
                R.drawable.sports_car_11};
        car.setBitmapResoursID(car_bg);
        final FrameAnimation windows = (FrameAnimation) findViewById(R.id.windows);
        int[] window_bg = new int[]{
                R.drawable.sports_car_windows_01,
                R.drawable.sports_car_windows_02,
                R.drawable.sports_car_windows_03,
                R.drawable.sports_car_windows_04,
                R.drawable.sports_car_windows_05,
                R.drawable.sports_car_windows_06};
        windows.setBitmapResoursID(window_bg);
        windows.setGapTime(200);
        car.start();
        car.setOnFrameFinisedListener(new FrameAnimation.OnFrameFinishedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {
                car.post(new Runnable() {
                    @Override
                    public void run() {
                        car_last.setVisibility(View.VISIBLE);
                        car.setVisibility(View.GONE);
                        windows.start();
                    }
                });
            }
        });

        final ObjectAnimator lightAnimator = ObjectAnimator.ofFloat(light, "Alpha", 0, 0, 0, 0.25f, 0.5f, 0.75f, 1, 0, 1, 0, 1, 0, 1, 0);
        lightAnimator.setInterpolator(null);
        lightAnimator.setDuration(4000);

        final ObjectAnimator lightAnimator2 = ObjectAnimator.ofFloat(light, "Alpha", 0, 0.25f, 0.5f, 0.75f, 1, 0, 1, 0, 1, 0, 1, 0);
        lightAnimator2.setStartDelay(1000);
        lightAnimator2.setDuration(3500);
        lightAnimator2.setInterpolator(null);

        final AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        final AlphaAnimation alphaAnimation2 = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        alphaAnimation2.setDuration(1500);
        alphaAnimation2.setStartOffset(1700);
        alphaAnimation2.setFillAfter(true);

        windows.setOnFrameFinisedListener(new FrameAnimation.OnFrameFinishedListener() {
            @Override
            public void onStart() {
                car.post(new Runnable() {
                    @Override
                    public void run() {
                        light.setVisibility(View.VISIBLE);
                        lightAnimator.start();
                    }
                });
            }

            @Override
            public void onStop() {
                windows.post(new Runnable() {
                    @Override
                    public void run() {
                        windows.setVisibility(View.GONE);
                    }
                });
            }
        });

        lightAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                door.setVisibility(View.VISIBLE);
                door.startAnimation(alphaAnimation);
            }
        });
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                lightAnimator2.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                door.startAnimation(alphaAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lightAnimator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                endGiftAnim();
            }
        });
    }
}
