package com.zhou.test.activity;

import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.zhou.test.R;
import com.zhou.test.util.UIHelper;
import com.zhou.test.view.MySlide;

public class TransitionActivity extends AppCompatActivity {

    private RelativeLayout all;
    private View view2;

    private float startX, startY;
    private boolean isStart = false;
    private int activityCloseEnterAnimation, activityCloseExitAnimation;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        initData();
        if (style == 0) {
            Fade fade = new Fade();
            fade.setDuration(300);

            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setResizeClip(true);
            changeBounds.setDuration(5000);

            ChangeClipBounds changeClipBounds = new ChangeClipBounds();
            ChangeTransform changeTransform = new ChangeTransform();
            ChangeImageTransform changeImageTransform = new ChangeImageTransform();
//            changeImageTransform.createAnimator();
            getWindow().setSharedElementEnterTransition(changeBounds);
            getWindow().setEnterTransition(fade);
        } else if (style == 1) {
            MySlide slide = new MySlide();
            slide.setDuration(250);
            getWindow().setEnterTransition(slide);
        } else if (style == 2) {
            Slide slide = new Slide();
            slide.setMode(Visibility.MODE_IN);
            slide.setInterpolator(new OvershootInterpolator());
//            slide.setPathMotion();
            slide.setDuration(250);
            getWindow().setEnterTransition(slide);
        } else if (style == 3) {
            getOutAnimResource();
        }
        setContentView(R.layout.activity_transition);
        view2 = findViewById(R.id.view2);
        all = (RelativeLayout) findViewById(R.id.all);
        view2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = event.getX();
                    startY = event.getY();
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    Log.i("AAAA", event.getY() - startY + "");
                    if (v.getTranslationY() > 200 || (event.getY() - startY == 0 && event.getX() - startX == 0)) {
                        myFinish();
                    } else {
                        v.setTranslationX(0);
                        v.setTranslationY(0);
                        v.setScaleX(1);
                        v.setScaleY(1);
                    }
                    startY = 0;
                    startX = 0;
                    isStart = false;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.i("AAAA", event.getY() - startY + "");
                    if (event.getY() - startY <= 0 && !isStart) {
                        return true;
                    }
                    isStart = true;
                    v.setX(v.getX() + event.getX() - startX);
                    v.setY(v.getY() + event.getY() - startY);
                    Log.i("AAAA tran", v.getTranslationY() + "");
                    float alpha = v.getTranslationY() / UIHelper.getScreenHeight() * 1.2f;
                    alpha = alpha > 0 ? alpha : 0;
                    Log.i("AAAA alpha", alpha + "");
                    all.setAlpha(1 - alpha);
                    v.setScaleX(1 - alpha);
                    v.setScaleY(1 - alpha);
                }
                return true;
            }
        });
    }

    private void getOutAnimResource() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    private int style = -1;

    private void initData() {
        if (getIntent() != null) {
            style = getIntent().getIntExtra("style", -1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        myFinish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void myFinish() {
        if (style == 0) {
            Slide slide = new Slide(Gravity.LEFT);
            slide.setDuration(250);
            getWindow().setReturnTransition(slide);//返回上一activity时的动画
            finishAfterTransition();
        } else if (style == 2) {
            Slide slide = new Slide(Gravity.LEFT);
            slide.setDuration(250);
            getWindow().setExitTransition(slide); //跳转到新activity时的动画,所以这句代码在此处无效果
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (style == 3) {
            overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
        }
    }
}
