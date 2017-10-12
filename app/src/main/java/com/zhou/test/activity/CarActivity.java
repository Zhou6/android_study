package com.zhou.test.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhou.test.R;
import com.zhou.test.view.DiffusionAnimator;
import com.zhou.test.view.FrameAnimation;
import com.zhou.test.util.UIHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author zqm
 * @since 2017/9/29
 */

public class CarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_porsche);

        final ImageView car1 = (ImageView) findViewById(R.id.new_porsche_car1);
        final ImageView car2 = (ImageView) findViewById(R.id.new_porsche_car2);
        ObjectAnimator.ofFloat(findViewById(R.id.new_prosche_bg), View.ALPHA, 0, 1).setDuration(1000).start();
        ValueAnimator carAnimator = ValueAnimator.ofFloat(0.5f, 0.4f, 0.31f, 0.22f, 0.13f, 0.04f, -0.05f, -0.025f, 0).setDuration(1500);
        carAnimator.setInterpolator(new LinearInterpolator());
        carAnimator.setStartDelay(1000);
        carAnimator.start();
        carAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                car1.setTranslationX((UIHelper.getScreenWidth() + car1.getMeasuredWidth()) * value);
                car2.setTranslationX(-(UIHelper.getScreenWidth() + car2.getMeasuredWidth()) * value);
                car1.setAlpha(1f);
                car2.setAlpha(1f);
            }
        });

        final boolean[] isStartAlpha = {false};

        final ValueAnimator carScaleAnimator = ValueAnimator.ofFloat(0.8f, 1, 0.5f).setDuration(1000);

        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                findViewById(R.id.porsche_top).setAlpha(value);
                findViewById(R.id.porsche_middel_frame).setAlpha(value);

                findViewById(R.id.porsche_left_frame).setAlpha(1);
                findViewById(R.id.porsche_right_frame).setAlpha(1);
                findViewById(R.id.porsche_under).setAlpha(1);

                findViewById(R.id.porsche_left_frame).setTranslationX((value - 1) * (UIHelper.dip2px(20) + findViewById(R.id.porsche_left_frame).getMeasuredWidth()));
                findViewById(R.id.porsche_right_frame).setTranslationX((1 - value) * (UIHelper.dip2px(20) + findViewById(R.id.porsche_right_frame).getMeasuredWidth()));
                findViewById(R.id.porsche_under).setTranslationY((1 - value) * (UIHelper.dip2px(15) + findViewById(R.id.porsche_under).getMeasuredHeight()));
            }
        });

        final int RepeatNum = 25;
        final ValueAnimator starAnimator = ValueAnimator.ofFloat(0, 1).setDuration(250);
        starAnimator.setRepeatMode(ValueAnimator.REVERSE);
        starAnimator.setRepeatCount(RepeatNum);
        starAnimator.setInterpolator(new LinearInterpolator());

        final int[] count = {0};
        starAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (count[0] == 0) {
                    findViewById(R.id.star_frame).setAlpha((Float) animation.getAnimatedValue());
                } else if (count[0] == RepeatNum) {
                    findViewById(R.id.star_frame).setAlpha((Float) animation.getAnimatedValue());
                }
                findViewById(R.id.star_frame).setTranslationY(findViewById(R.id.star_frame).getTranslationY() - UIHelper.dip2px(0.17));
//                findViewById(R.id.star_frame).setScaleX(findViewById(R.id.star_frame).getScaleX() + 0.0005f);
//                findViewById(R.id.star_frame).setScaleY(findViewById(R.id.star_frame).getScaleY() + 0.0005f);
//                float v1 = 1 - Math.abs(-0.5f + value * 0.5f);
//                float v2 = Math.abs(1 - value * 0.5f);
                float v1 = value;
                float v2 = 1 - value;
                findViewById(R.id.star1).setAlpha(v1);
                findViewById(R.id.star2).setAlpha(v2);
                findViewById(R.id.star3).setAlpha(v1);
                findViewById(R.id.star4).setAlpha(v2);
            }
        });

        starAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                count[0]++;
            }
        });

        carScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                car1.setScaleX(value);
                car1.setScaleY(value);
                car2.setScaleX(value);
                car2.setScaleY(value);
                if (!isStartAlpha[0] && value >= 0.95) {
                    isStartAlpha[0] = true;
                    animator.start();
                    starAnimator.start();
                }
                if (isStartAlpha[0]) {
                    car1.setAlpha(2 * value - 1);
                    car2.setAlpha(2 * value - 1);
                }
            }
        });

        carAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                carScaleAnimator.start();
            }
        });
        final ObjectAnimator rlAnim = ObjectAnimator.ofFloat(findViewById(R.id.second_rl), View.ALPHA, 1, 0).setDuration(1000);

        final ValueAnimator rotateValue = ValueAnimator.ofFloat(0, -220).setDuration(500);
        final ValueAnimator rotateValue0 = ValueAnimator.ofFloat(-220, 100).setDuration(300);
        final ValueAnimator rotateValue1 = ValueAnimator.ofFloat(100, -170).setDuration(700);
        final ValueAnimator rotateValue2 = ValueAnimator.ofFloat(-170, 0).setDuration(300);

        rotateValue.setInterpolator(new LinearInterpolator());
        rotateValue0.setInterpolator(new DecelerateInterpolator());
        rotateValue1.setInterpolator(new LinearInterpolator());
//        rotateValue2.setInterpolator(new DecelerateInterpolator());
        ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                findViewById(R.id.left_small).setRotation(value);
                findViewById(R.id.middle_big).setRotation(value);
                findViewById(R.id.right_small).setRotation(-value);
            }
        };
        rotateValue.addUpdateListener(listener);
        rotateValue0.addUpdateListener(listener);
        rotateValue1.addUpdateListener(listener);
        rotateValue2.addUpdateListener(listener);

        rotateValue.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateValue0.start();
            }
        });
        rotateValue0.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateValue1.start();
            }
        });
        rotateValue1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateValue2.start();
            }
        });
        rotateValue2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rlAnim.start();
            }
        });

        final ValueAnimator ringAnim = ValueAnimator.ofFloat(0, 1).setDuration(2500);
        ringAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                findViewById(R.id.ring1).setAlpha(1 - value * 1.5f);
                findViewById(R.id.ring1).setScaleX(0.5f + value);
                findViewById(R.id.ring1).setScaleY(0.5f + value);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateValue.start();
                ringAnim.setStartDelay(1000);
                ringAnim.start();
            }
        });

        final ObjectAnimator final_car1 = ObjectAnimator.ofFloat(findViewById(R.id.rl2), View.TRANSLATION_X,
                UIHelper.getScreenWidth(), 0,
                UIHelper.dip2px(-10),UIHelper.dip2px(-5),0,UIHelper.dip2px(5), UIHelper.dip2px(10),UIHelper.dip2px(5),0,UIHelper.dip2px(-5),
                -1.5f * UIHelper.getScreenWidth()).setDuration(3700);
        final ObjectAnimator final_car2 = ObjectAnimator.ofFloat(findViewById(R.id.rl2), View.TRANSLATION_Y,
                -UIHelper.dip2px(70), 0,
                UIHelper.dip2px(4),UIHelper.dip2px(2), 0, UIHelper.dip2px(-2), UIHelper.dip2px(-4),UIHelper.dip2px(-2), 0, UIHelper.dip2px(2),
                1.5f * UIHelper.dip2px(70)).setDuration(3700);
        final_car1.setInterpolator(new LinearInterpolator());
        final_car2.setInterpolator(new LinearInterpolator());

        final ValueAnimator girlAnim = ValueAnimator.ofFloat(0, 1).setDuration(500);
        girlAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                findViewById(R.id.girl1).setAlpha(value);
                findViewById(R.id.girl2).setAlpha(value);
                findViewById(R.id.girl1).setScaleX(value);
                findViewById(R.id.girl1).setScaleY(value);
                findViewById(R.id.girl2).setScaleX(value);
                findViewById(R.id.girl2).setScaleY(value);
            }
        });

        final ObjectAnimator girl1 = ObjectAnimator.ofFloat(findViewById(R.id.girl1), View.TRANSLATION_Y, UIHelper.dip2px(30), 0).setDuration(500);
        final ObjectAnimator girl2 = ObjectAnimator.ofFloat(findViewById(R.id.girl2), View.TRANSLATION_Y, UIHelper.dip2px(30), 0).setDuration(500);

        final ObjectAnimator carLightAnim = ObjectAnimator.ofFloat(findViewById(R.id.car_light), View.ALPHA, 0, 1, 1, 1, 0).setDuration(2200);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl2);
        final DiffusionAnimator diffusionAnimator = DiffusionAnimator.ofCustomPosition(
                rl, 20, new int[]{R.drawable.new_porsche_light1}, new DiffusionAnimator.CustomInitViewPosition() {
                    @Override
                    public void initViewPosition(ArrayList<DiffusionAnimator.ParticleAnimBean> list, int width, int height, int density, int duration) {
                        for (final DiffusionAnimator.ParticleAnimBean particleAnimBean : list) {
                            int top;
                            int left = 0 - particleAnimBean.imageView.getWidth();
                            Random random = new Random();
                            top = random.nextInt(height) + particleAnimBean.imageView.getHeight();
                            final Point start = new Point(left, top);
                            particleAnimBean.startPoint = start;
                            particleAnimBean.imageView.setX(start.x);
                            particleAnimBean.imageView.setY(start.y);
                            final Point end = new Point(start.x + width + particleAnimBean.imageView.getWidth(), start.y - height/2);
                            particleAnimBean.endPoint = end;
                            Point controllPoint = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
                            DiffusionAnimator.BezierEvaluator bezierEvaluator = new DiffusionAnimator.BezierEvaluator(controllPoint, DiffusionAnimator.BEHAVIOR_CUSTOM);
                            particleAnimBean.bezierEvaluator = bezierEvaluator;
                            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                            particleAnimBean.valueAnimator = valueAnimator;
                            valueAnimator.setDuration(500);
                            valueAnimator.setStartDelay(random.nextInt((int) (4000 * 1.5)));
                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    Point pointF = particleAnimBean.bezierEvaluator.evaluate((float) animation.getAnimatedValue(), particleAnimBean.startPoint, particleAnimBean.endPoint);
                                    particleAnimBean.imageView.setX(pointF.x);
                                    particleAnimBean.imageView.setY(pointF.y);
                                }
                            });
                            valueAnimator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    particleAnimBean.imageView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                });


        int[] src1 = {R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2,
                R.drawable.new_porsche_shoe, R.drawable.new_porsche_shoe2};
        int[] src2 = {R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4,
                R.drawable.new_porsche_shoe3, R.drawable.new_porsche_shoe4};
        final FrameAnimation frameView = (FrameAnimation) findViewById(R.id.shoe1);
        frameView.setBitmapResoursID(src1);
        frameView.setGapTime(120);
        frameView.needScale(false);
//
        final FrameAnimation frameView2 = (FrameAnimation) findViewById(R.id.shoe2);
        frameView2.setBitmapResoursID(src2);
        frameView.setGapTime(120);
        frameView2.needScale(false);

        rlAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                findViewById(R.id.rl2).setAlpha(1);
                final_car1.start();
                final_car2.start();
                frameView.start();
//                ((AnimationDrawable)((ImageView)findViewById(R.id.shoe2)).getDrawable()).start();
                frameView2.start();
                girlAnim.setStartDelay(1800);
                girlAnim.start();
                girl1.setStartDelay(1800);
                girl1.start();
                girl2.setStartDelay(1800);
                girl2.start();
                carLightAnim.setStartDelay(1500);
                carLightAnim.start();
                diffusionAnimator.start();
            }
        });

        final_car1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                endGiftAnim();
            }
        });
    }
}
