package com.zhou.test.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhou.test.R;
import com.zhou.test.util.UIHelper;
import com.zhou.test.view.DiffusionAnimator;

public class FujiAnim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuji_mount);
        initView();
    }

    private void initView() {
        final ImageView fuji_mount = (ImageView) findViewById(R.id.fuji_mount);
        final ImageView fuji_bg_cherry = (ImageView) findViewById(R.id.fuji_bg_cherry);
        final ImageView fuji_left_cherry = (ImageView) findViewById(R.id.fuji_left_cherry);
        final ImageView fuji_left_cherry2 = (ImageView) findViewById(R.id.fuji_left_cherry2);
        final ImageView fuji_right_cherry = (ImageView) findViewById(R.id.fuji_right_cherry);
        final ImageView fuji_right_cherry2 = (ImageView) findViewById(R.id.fuji_right_cherry2);
        final ImageView fuji_left_cherry_down = (ImageView) findViewById(R.id.fuji_left_cherry_down);
        final ImageView fuji_right_cherry_down = (ImageView) findViewById(R.id.fuji_right_cherry_down);
        final ImageView fuji_left_up_cherry = (ImageView) findViewById(R.id.fuji_left_up_cherry);
        final ImageView fuji_left_cherry_up = (ImageView) findViewById(R.id.fuji_left_cherry_up);
        final RelativeLayout final_petals = (RelativeLayout) findViewById(R.id.final_petals);

        int[] petals = new int[]{R.drawable.fuji_petals1,
                                R.drawable.fuji_petals2,
                                R.drawable.fuji_petals3,
                                R.drawable.fuji_petals4,
                                R.drawable.fuji_petals5,
                                R.drawable.fuji_petals6,
                                R.drawable.fuji_petals7,
                                R.drawable.fuji_petals8,
                                R.drawable.fuji_petals9};
        final DiffusionAnimator a = DiffusionAnimator.ofPosition(final_petals
                , DiffusionAnimator.BEHAVIOR_CENTER, 250, petals);
        a.setDuration(2500);

        fuji_right_cherry_down.setPivotY(UIHelper.dip2px(133));
        fuji_right_cherry_down.setPivotX(UIHelper.dip2px(100));
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 1.4f);
        animator.setDuration(1700);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                fuji_mount.setScaleX(scale);
                fuji_mount.setScaleY(scale);
                fuji_bg_cherry.setScaleX(scale);
                fuji_bg_cherry.setScaleY(scale);
                fuji_bg_cherry.setAlpha(5 - scale * 4);
                fuji_left_up_cherry.setScaleX(scale);
                fuji_left_up_cherry.setScaleY(scale);

                fuji_left_cherry_down.setScaleX(1.3f * scale);
                fuji_left_cherry_down.setScaleY(1.2f * scale);
                fuji_right_cherry_down.setScaleX(scale);
                fuji_right_cherry_down.setScaleY(scale);
                fuji_left_cherry_up.setScaleX(scale);
                fuji_left_cherry_up.setScaleY(scale);
            }
        });

        final ValueAnimator animator2 = ValueAnimator.ofFloat(-1, 1);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                float rotate = value * 3;
                fuji_left_cherry_up.setRotation(rotate);
                fuji_left_cherry_down.setRotation(-rotate);
                fuji_right_cherry_down.setRotation(rotate);
            }
        });
        animator2.setDuration(1000);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setRepeatCount(Animation.INFINITE);

        final ValueAnimator animator3 = ValueAnimator.ofFloat(1, 1.04f);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                fuji_left_cherry.setScaleX(scale);
                fuji_left_cherry.setScaleY(scale);
                fuji_left_cherry2.setScaleX(scale);
                fuji_left_cherry2.setScaleY(scale);
                fuji_right_cherry.setScaleX(scale);
                fuji_right_cherry.setScaleY(scale);
                fuji_right_cherry2.setScaleX(scale);
                fuji_right_cherry2.setScaleY(scale);
            }
        });
        animator3.setDuration(300);
        final DiffusionAnimator diffusionAnimator = DiffusionAnimator.ofPosition((ViewGroup) findViewById(R.id.petals_rl)
                , DiffusionAnimator.BEHAVIOR_DROP, 60, petals);
        diffusionAnimator.setDuration(4300);

        final DiffusionAnimator diffusionAnimator1 = DiffusionAnimator.ofPosition((ViewGroup) findViewById(R.id.rl_left)
                , DiffusionAnimator.BEHAVIOR_DROP, 5, new int[]{R.drawable.fuji_right_petals});
        diffusionAnimator1.setDuration(6000);

        final DiffusionAnimator diffusionAnimator2 = DiffusionAnimator.ofPosition((ViewGroup) findViewById(R.id.rl_right)
                , DiffusionAnimator.BEHAVIOR_DROP, 5, new int[]{R.drawable.fuji_right_petals});
        diffusionAnimator2.setDuration(6000);

        final ObjectAnimator animatorHeart = ObjectAnimator.ofFloat(findViewById(R.id.frame), View.TRANSLATION_Y, -UIHelper.getScreenHeight() / 4);
        animatorHeart.setDuration(1600);
        animatorHeart.setStartDelay(2000);

        final ObjectAnimator animatorHeart2 = ObjectAnimator.ofFloat(findViewById(R.id.heart_center), View.SCALE_X, 0.1f, 1.2f, 1.1f, 1.4f, 1.4f, 1.4f, 2.5f);
        final ObjectAnimator animatorHeart3 = ObjectAnimator.ofFloat(findViewById(R.id.heart_center), View.SCALE_Y, 0.1f, 1.2f, 1.1f, 1.4f, 1.4f, 1.4f, 2.5f);
        animatorHeart2.setDuration(5400);
        animatorHeart2.setStartDelay(2000);
        animatorHeart3.setDuration(5400);
        animatorHeart3.setStartDelay(2000);

        final ObjectAnimator animatorHeart4 = ObjectAnimator.ofFloat(findViewById(R.id.heart_center), View.ALPHA, 0.3f, 1, 1, 1, 1, 0);
        animatorHeart4.setDuration(4500);
        animatorHeart4.setStartDelay(2000);

        final ObjectAnimator animatorPetals = ObjectAnimator.ofFloat(final_petals, View.SCALE_X, 1.7f);
        final ObjectAnimator animatorPetals2 = ObjectAnimator.ofFloat(final_petals, View.SCALE_Y, 1.7f);
        animatorPetals.setDuration(700);
//        animatorPetals.setStartDelay(1500);
        animatorPetals2.setDuration(700);
//        animatorPetals2.setStartDelay(1500);

        int mount_heart = 1500;
        final ObjectAnimator animatorLeftHeart = ObjectAnimator.ofFloat(findViewById(R.id.left_heart), View.SCALE_X, 1.2f);
        final ObjectAnimator animatorLeftHeart2 = ObjectAnimator.ofFloat(findViewById(R.id.left_heart), View.SCALE_Y, 1.2f);
        final ObjectAnimator animatorRightHeart = ObjectAnimator.ofFloat(findViewById(R.id.right_heart), View.SCALE_X, 1.2f);
        final ObjectAnimator animatorRightHeart2 = ObjectAnimator.ofFloat(findViewById(R.id.right_heart), View.SCALE_Y, 1.2f);
        animatorLeftHeart.setDuration(mount_heart);
        animatorLeftHeart2.setDuration(mount_heart);
        animatorRightHeart.setDuration(mount_heart);
        animatorRightHeart2.setDuration(mount_heart);
        animatorLeftHeart.setRepeatCount(Animation.INFINITE);
        animatorLeftHeart2.setRepeatCount(Animation.INFINITE);
        animatorRightHeart.setRepeatCount(Animation.INFINITE);
        animatorRightHeart2.setRepeatCount(Animation.INFINITE);
        final ObjectAnimator animatorLeftHeart3 = ObjectAnimator.ofFloat(findViewById(R.id.left_heart), View.ALPHA, 0.5f, 1, 0);
        final ObjectAnimator animatorRightHeart3 = ObjectAnimator.ofFloat(findViewById(R.id.right_heart), View.ALPHA, 0.5f, 1, 0);
        animatorLeftHeart3.setDuration(mount_heart);
        animatorRightHeart3.setDuration(mount_heart);

        animatorLeftHeart3.setRepeatCount(Animation.INFINITE);
        animatorRightHeart3.setRepeatCount(Animation.INFINITE);

        final ObjectAnimator animatorLeftHeart4 = ObjectAnimator.ofFloat(findViewById(R.id.left_heart), View.TRANSLATION_Y, -300);
        final ObjectAnimator animatorRightHeart4 = ObjectAnimator.ofFloat(findViewById(R.id.right_heart), View.TRANSLATION_Y, -300);
        animatorLeftHeart4.setDuration(mount_heart);
        animatorRightHeart4.setDuration(mount_heart);
        animatorLeftHeart4.setRepeatCount(Animation.INFINITE);
        animatorRightHeart4.setRepeatCount(Animation.INFINITE);

        int delay = 1500;
        int delay2 = delay + 750;
        animatorLeftHeart.setStartDelay(delay);
        animatorLeftHeart2.setStartDelay(delay);
        animatorLeftHeart3.setStartDelay(delay);
        animatorLeftHeart4.setStartDelay(delay);
        animatorRightHeart.setStartDelay(delay2);
        animatorRightHeart2.setStartDelay(delay2);
        animatorRightHeart3.setStartDelay(delay2);
        animatorRightHeart4.setStartDelay(delay2);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animator2.start();
                animator3.start();
                diffusionAnimator.start();
                diffusionAnimator1.start();
                diffusionAnimator2.start();
                animatorHeart.start();
                animatorHeart2.start();
                animatorHeart3.start();
                animatorHeart4.start();

                animatorLeftHeart.start();
                animatorLeftHeart2.start();
                animatorLeftHeart3.start();
                animatorLeftHeart4.start();
                animatorRightHeart.start();
                animatorRightHeart2.start();
                animatorRightHeart3.start();
                animatorRightHeart4.start();
            }
        });

        animatorHeart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                final_petals.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animatorPetals.start();
                        animatorPetals2.start();
                        a.start();
                    }
                }, 1500);
            }
        });

        int trans = UIHelper.dip2px(40);
        final ObjectAnimator animator1Heart = ObjectAnimator.ofFloat(findViewById(R.id.heart2), View.TRANSLATION_X, -trans);
        final ObjectAnimator animator2Heart = ObjectAnimator.ofFloat(findViewById(R.id.heart1), View.TRANSLATION_X, trans);
        final ObjectAnimator animator3Heart = ObjectAnimator.ofFloat(findViewById(R.id.heart4), View.TRANSLATION_X, -trans);
        final ObjectAnimator animator4Heart = ObjectAnimator.ofFloat(findViewById(R.id.heart3), View.TRANSLATION_X, trans);

        final ObjectAnimator animator1Heart2 = ObjectAnimator.ofFloat(findViewById(R.id.heart2), View.TRANSLATION_Y, -trans);
        final ObjectAnimator animator2Heart2 = ObjectAnimator.ofFloat(findViewById(R.id.heart1), View.TRANSLATION_Y, -trans);
        final ObjectAnimator animator3Heart2 = ObjectAnimator.ofFloat(findViewById(R.id.heart4), View.TRANSLATION_Y, trans);
        final ObjectAnimator animator4Heart2 = ObjectAnimator.ofFloat(findViewById(R.id.heart3), View.TRANSLATION_Y, trans);
        int durationg = 900;
        animator1Heart.setDuration(durationg);
        animator2Heart.setDuration(durationg);
        animator3Heart.setDuration(durationg);
        animator4Heart.setDuration(durationg);
        animator1Heart2.setDuration(durationg);
        animator2Heart2.setDuration(durationg);
        animator3Heart2.setDuration(durationg);
        animator4Heart2.setDuration(durationg);

        final ObjectAnimator animatorHeartRl = ObjectAnimator.ofFloat(findViewById(R.id.heart_rl), View.ALPHA, 1, 0);
        animatorHeartRl.setDuration(durationg);

        animatorHeart2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (value >= 1.1 && value < 1.3 && !animator1Heart.isStarted()) {
                    animator1Heart.start();
                    animator2Heart.start();
                    animator3Heart.start();
                    animator4Heart.start();
                    animator1Heart2.start();
                    animator2Heart2.start();
                    animator3Heart2.start();
                    animator4Heart2.start();
                    animatorHeartRl.start();
                }
            }
        });
    }
}
