package com.zhou.test.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhou.test.R;
import com.zhou.test.util.UIHelper;
import com.zhou.test.view.AlwaysMarqueeTextView;

import java.util.Random;

public class BarrageActivity extends AppCompatActivity {

    private RelativeLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrage);
        rl = (RelativeLayout) findViewById(R.id.barrage_rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNewHornInComing();
            }
        });
    }

    private void onNewHornInComing() {
        final int mLayoutWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        int mLayoutHeight = rl.getMeasuredHeight();
        View v = getLayoutInflater().inflate(R.layout.text, null);
        final AlwaysMarqueeTextView tv = (AlwaysMarqueeTextView) v.findViewById(R.id.float_view_comment_tv);

        // 得到使用该paint写上text的时候,像素为多少
        String ss = "1234567890123456789012345678901234567890123456789";
        String a = ss;
        TextPaint paint = tv.getPaint();
        for (int i = 0; i < 100; i++) {
            float textLength = paint.measureText(a);
            if (textLength < mLayoutWidth) {
                a += "          " + ss;
            } else {
                break;
            }
        }
        tv.setText(a);
        rl.addView(tv);

        final int translationY;
        Random random = new Random();
        mLayoutHeight = random.nextInt(mLayoutHeight);
        if (mLayoutHeight - 75 < 0) {
            translationY = 0;
        } else {
            translationY = mLayoutHeight;
        }

        final ValueAnimator anim = ValueAnimator.ofInt(mLayoutWidth, UIHelper.dip2px(10));
        final ValueAnimator anim2 = ValueAnimator.ofInt(UIHelper.dip2px(10), -mLayoutWidth - UIHelper.dip2px(10));
        anim.setDuration(3000);
        anim.setInterpolator(new LinearInterpolator());
        anim.setTarget(tv);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //回调方法在主线程执行
                int currentValue = (int) animation.getAnimatedValue();
                tv.setTranslationX(currentValue);
                tv.setTranslationY(translationY);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    }
                }, 1500);
                anim2.setStartDelay(10000);
                anim2.start();
            }
        });
        anim.start();

        anim2.setDuration(1000);
        anim2.setInterpolator(new AccelerateDecelerateInterpolator());
        anim2.setTarget(tv);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //回调方法在主线程执行
                int currentValue = (int) animation.getAnimatedValue();
                tv.setTranslationX(currentValue);
                tv.setTranslationY(translationY);
            }
        });
        anim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tv.clearAnimation();
            }
        });
    }

    // 计算出该TextView中文字的长度(像素)
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }
}
