package com.zhou.test.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhou.test.R;
import com.zhou.test.util.UIHelper;

public class ExclusiveAnim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exclusive_of_anchor);
        initView();
    }

    private void initView() {
        final ImageView stage = (ImageView) findViewById(R.id.stage);
        final ImageView stage_effect = (ImageView) findViewById(R.id.stage_effect);
        final ImageView right_light = (ImageView) findViewById(R.id.right_light);
        final ImageView left_light = (ImageView) findViewById(R.id.left_light);
        final ImageView center_light = (ImageView) findViewById(R.id.center_light);
        final RelativeLayout photo = (RelativeLayout) findViewById(R.id.photo);
        final ImageView stage_light = (ImageView) findViewById(R.id.stage_light);
        final ImageView stage_light2 = (ImageView) findViewById(R.id.stage_light2);
        final ImageView stage_border = (ImageView) findViewById(R.id.stage_border);
        final ImageView stage_border2 = (ImageView) findViewById(R.id.stage_border2);
        final ImageView stage_border3 = (ImageView) findViewById(R.id.stage_border3);

        ObjectAnimator anim = ObjectAnimator.ofFloat(findViewById(R.id.rl), View.ALPHA, 1);
        anim.setDuration(1100);
        anim.start();

        photo.setPivotX(UIHelper.dip2px(75));
        photo.setPivotY(UIHelper.dip2px(100));
        stage_effect.setPivotX(UIHelper.dip2px(90));
        stage_effect.setPivotY(UIHelper.dip2px(218));
        stage_light.setPivotX(UIHelper.dip2px(124));
        stage_light.setPivotY(UIHelper.dip2px(230));
        stage_light2.setPivotX(UIHelper.dip2px(124));
        stage_light2.setPivotY(UIHelper.dip2px(230));

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.3f, 0.7f);
        valueAnimator.setDuration(600);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                findViewById(R.id.star_bg).setAlpha(1 - value);
                findViewById(R.id.star_bg2).setAlpha(value);
                findViewById(R.id.star_bg3).setAlpha(value);
                findViewById(R.id.star_bg4).setAlpha(value);
                findViewById(R.id.star_bg5).setAlpha(1 - value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                findViewById(R.id.point).setVisibility(View.VISIBLE);
            }
        });

        final ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.setDuration(1300);
        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                stage.setAlpha(value);
                stage.setScaleX(value * 1.3f);
                stage.setScaleY(value * 1.3f);
                if (value > 0.5) {
                    stage_effect.setScaleX((value - 0.5f) * 2.4f);
                    stage_effect.setScaleY((value - 0.5f) * 2.4f);
                    stage_effect.setAlpha(0.3f + value - 0.5f);
                }
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                valueAnimator.start();
                valueAnimator2.start();
            }
        });

        int lightTime = 500;
        final ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(right_light, View.TRANSLATION_X, UIHelper.dip2px(80));
        final ObjectAnimator rightAnimator2 = ObjectAnimator.ofFloat(right_light, View.ALPHA, 0.3f, 1);
        final ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(left_light, View.TRANSLATION_X, -UIHelper.dip2px(80));
        final ObjectAnimator leftAnimator2 = ObjectAnimator.ofFloat(left_light, View.ALPHA, 0.3f, 1);
        final ObjectAnimator centerAnimator = ObjectAnimator.ofFloat(center_light, View.TRANSLATION_Y, UIHelper.dip2px(50));
        final ObjectAnimator centerAnimator2 = ObjectAnimator.ofFloat(center_light, View.ALPHA, 0.3f, 1);
        rightAnimator.setDuration(lightTime);
        rightAnimator2.setDuration(lightTime);

        leftAnimator.setDuration(lightTime);
        leftAnimator.setStartDelay(lightTime);
        leftAnimator2.setDuration(lightTime);
        leftAnimator2.setStartDelay(lightTime);

        centerAnimator.setDuration(lightTime);
        centerAnimator.setStartDelay(lightTime * 2);
        centerAnimator2.setDuration(lightTime);
        centerAnimator2.setStartDelay(lightTime * 2);

        final ValueAnimator valueAnimator4 = ValueAnimator.ofFloat(1, 0.3f, 1, 0);
        valueAnimator4.setStartDelay(lightTime * 3);
        valueAnimator4.setInterpolator(new LinearInterpolator());
        valueAnimator4.setDuration(2000);
        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                right_light.setAlpha(value);
                left_light.setAlpha(value);
                center_light.setAlpha(value);
            }
        });
        final ValueAnimator valueAnimator3 = ValueAnimator.ofFloat(0, 1);
        valueAnimator3.setInterpolator(new LinearInterpolator());
        valueAnimator3.setDuration(700);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                photo.setAlpha(value);
                photo.setScaleX(value * 1f);
                photo.setScaleY(value * 1f);
                photo.setRotation(1080 * value);
            }
        });

        final ObjectAnimator photoAnimator1 = ObjectAnimator.ofFloat(photo, View.SCALE_X, 1.6f);
        final ObjectAnimator photoAnimator2 = ObjectAnimator.ofFloat(photo, View.SCALE_Y, 1.6f);
        photoAnimator1.setDuration(1000);
        photoAnimator1.setStartDelay(lightTime * 3 + 1000);
        photoAnimator2.setDuration(1000);
        photoAnimator2.setStartDelay(lightTime * 3 + 1000);


        valueAnimator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rightAnimator.start();
                leftAnimator.start();
                centerAnimator.start();
                rightAnimator2.start();
                leftAnimator2.start();
                centerAnimator2.start();
                valueAnimator4.start();
                valueAnimator3.start();
                photoAnimator1.start();
                photoAnimator2.start();
            }

//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                effectAnimator.start();
//            }
        });

        final ObjectAnimator sLightAnim = ObjectAnimator.ofFloat(stage_light, View.SCALE_X, 1.3f);
        final ObjectAnimator sLightAnim2 = ObjectAnimator.ofFloat(stage_light, View.SCALE_Y, 1.3f);
        final ObjectAnimator sLightAnim3 = ObjectAnimator.ofFloat(stage_light2, View.SCALE_X, 1.3f);
        final ObjectAnimator sLightAnim4 = ObjectAnimator.ofFloat(stage_light2, View.SCALE_Y, 1.3f);
        sLightAnim.setDuration(1000);
        sLightAnim2.setDuration(1000);
        sLightAnim3.setDuration(1000);
        sLightAnim4.setDuration(1000);

        final ValueAnimator circleAnim = ValueAnimator.ofFloat(0, 1, 1, 1);
        circleAnim.setDuration(4500);
        circleAnim.setRepeatCount(ValueAnimator.INFINITE);
        circleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                stage_border.setAlpha(1 - value);
                stage_border.setScaleX(1.2f + value);
                stage_border.setScaleY(1.2f + value);
            }
        });
        circleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });

        final ValueAnimator circleAnim2 = ValueAnimator.ofFloat(0, 1, 1, 1);
        circleAnim2.setDuration(4500);
        circleAnim2.setStartDelay(1300);
        circleAnim2.setRepeatCount(ValueAnimator.INFINITE);
        circleAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                stage_border2.setAlpha(1 - value);
                stage_border2.setScaleX(1.2f + value);
                stage_border2.setScaleY(1.2f + value);
            }
        });

        final ValueAnimator circleAnim3 = ValueAnimator.ofFloat(0, 1, 1, 1);
        circleAnim3.setDuration(4500);
        circleAnim3.setStartDelay(2600);
        circleAnim3.setRepeatCount(ValueAnimator.INFINITE);
        circleAnim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                stage_border3.setAlpha(1 - value);
                stage_border3.setScaleX(1.2f + value);
                stage_border3.setScaleY(1.2f + value);
            }
        });

        photoAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                sLightAnim.start();
                sLightAnim2.start();
                sLightAnim3.start();
                sLightAnim4.start();
                circleAnim.start();
                circleAnim2.start();
                circleAnim3.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                photo.setPivotY(UIHelper.dip2px(200));
            }
        });

        final ValueAnimator lightEndAnim = ValueAnimator.ofFloat(0, 1);
        lightEndAnim.setStartDelay(2000);
        lightEndAnim.setDuration(2000);
        lightEndAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                stage_light.setRotation(-5 - 15 * value);
                stage_light2.setRotation(5 + 15 * value);
                stage_light.setAlpha(0.7f - value / 2);
                stage_light2.setAlpha(0.7f - value / 2);
            }
        });

        final ObjectAnimator photoEndAnim = ObjectAnimator.ofFloat(photo, View.SCALE_X, 2);
        final ObjectAnimator photoEndAnim2 = ObjectAnimator.ofFloat(photo, View.SCALE_Y, 2);
        final ObjectAnimator photoEndAnim3 = ObjectAnimator.ofFloat(photo, View.ALPHA, 0f);
        photoEndAnim.setDuration(1200);
        photoEndAnim2.setDuration(1200);
        photoEndAnim3.setDuration(1200);
        photoEndAnim.setStartDelay(5000);
        photoEndAnim2.setStartDelay(5000);
        photoEndAnim3.setStartDelay(5000);

        sLightAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                lightEndAnim.start();
                photoEndAnim.start();
                photoEndAnim2.start();
                photoEndAnim3.start();
            }
        });

    }

}
