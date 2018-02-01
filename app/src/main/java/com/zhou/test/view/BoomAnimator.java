package com.zhou.test.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zhou.test.util.Tasks;
import com.zhou.test.util.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zqm
 * @since 2018/1/23
 */

public class BoomAnimator {
    private final Random random;
    private final List<Integer> srcList;//粒子样本
    private int circleCount = 5; //整个动画出现的圆动画个数
    private int dur; //每次爆炸时间
    private int radius; //每次爆炸半径
    private int circleItemCount; //每次爆炸粒子个数
    private ViewGroup viewGroup; //放动画的父布局
    private ArrayList<CircleBean> circleBeans = new ArrayList<>();
    private boolean isReady = false;
    private ArrayList<ImageView> unUseImageViews = new ArrayList<>();
    private ArrayList<ImageView> useImageViews = new ArrayList<>();

    public BoomAnimator(final ViewGroup viewGroup, List<Integer> srcList, int dur, int radius, int circleItemCount) {
        this.viewGroup = viewGroup;
        this.circleCount = srcList.size();
        this.dur = dur;
        this.radius = radius;
        this.circleItemCount = circleItemCount;
        this.srcList = srcList;

        random = new Random();
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (viewGroup.getContext() != null && !((Activity) viewGroup.getContext()).isFinishing()) {
                    initCircleBean();
                    initPosition();
                    isReady = true;
                    if (isStart) {
                        start();
                    }
                }
            }
        });
    }

    private void initCircleBean() {
        for (int j = 0; j < circleCount; j++) {
            //radius 爆炸半径
            int radius = UIHelper.dip2px(this.radius + random.nextInt(this.radius));
            //x,y 圆心位置
            int x = random.nextInt(viewGroup.getMeasuredWidth() - radius * 2) + radius;
            int y = random.nextInt(viewGroup.getMeasuredHeight() - radius * 2) + radius;
            CircleBean circleBean = new CircleBean(1, dur / 3 * 2 + random.nextInt(dur / 3), radius, circleItemCount + random.nextInt(circleItemCount), x, y);
            for (int i = 0; i < circleBean.circleItemTypeCount; i++) {
                int randomCount = 0;
                if (srcList.size() > 1) {
                    randomCount = random.nextInt(srcList.size() - 1);
                }
                //随机粒子类型
                circleBean.addItem(srcList.get(randomCount));
                srcList.remove(randomCount);
            }
            addData(circleBean);
        }
    }

    private void initPosition() {
        for (int i = 0; i < circleBeans.size(); i++) {
            CircleBean circleBean = circleBeans.get(i);
            final FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
            //30为粒子图片大概的大小
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(circleBean.radius * 2 + UIHelper.dip2px(30),
                    circleBean.radius * 2 + UIHelper.dip2px(30));
            frameLayout.setX(circleBean.centerPoint.x - circleBean.radius);
            frameLayout.setY(circleBean.centerPoint.y - circleBean.radius);
            frameLayout.setLayoutParams(lp);
            for (int j = 0; j < circleBean.items.size(); j++) {
                int resource = circleBean.items.get(j);
                for (int k = 0; k < circleBean.circleItemCount; k++) {
//                    if (unUseImageViews.size() == 0) {
//                        ImageView imageView = new ImageView(viewGroup.getContext());
//                        useImageViews.add(imageView);
//                        imageView.setImageResource(resource);
//                        circleBeans.get(i).imageViews.add(imageView);
//                    }
                    //粒子运动角度和旋转角度
                    double angle = 360 / circleBean.circleItemCount * (k);
                    float x = (float) (circleBean.radius * Math.sin(Math.toRadians(angle)));
                    float y = (float) (circleBean.radius * Math.cos(Math.toRadians(angle)));
                    final ImageView imageView = new ImageView(viewGroup.getContext());
                    AnimationSet animationSet = new AnimationSet(false);
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, x, 0, y);
                    translateAnimation.setDuration((long) (circleBean.duration * 0.45));
                    translateAnimation.setInterpolator(new DecelerateInterpolator());
                    AlphaAnimation alphaAnimation2 = new AlphaAnimation(0, 255);
                    alphaAnimation2.setDuration((long) 100);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setStartOffset((long) (circleBean.duration * 0.5));
                    scaleAnimation.setDuration((long) (circleBean.duration * 0.5));
                    TranslateAnimation translateAnimation2 = new TranslateAnimation(0, 0, 0, UIHelper.dip2px(20));
                    translateAnimation2.setStartOffset((long) (circleBean.duration * 0.5));
                    translateAnimation2.setDuration((long) (circleBean.duration * 0.5));
                    translateAnimation2.setInterpolator(new LinearInterpolator());
                    animationSet.addAnimation(scaleAnimation);
                    animationSet.addAnimation(translateAnimation2);
                    animationSet.addAnimation(translateAnimation);
                    animationSet.addAnimation(alphaAnimation2);
                    animationSet.setStartOffset((long) (i * circleBean.duration * 0.5));
                    animationSet.setFillAfter(true);
                    imageView.setImageResource(resource);
                    imageView.setAnimation(animationSet);
                    imageView.setRotation((float) -angle);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER;
                    imageView.setLayoutParams(layoutParams);
                    frameLayout.addView(imageView);
                }
            }
            viewGroup.addView(frameLayout);
        }
    }

    private boolean isStart = false;

    public void start() {
        isStart = true;
        if (isReady) {
            final int otherChildNum = viewGroup.getChildCount() - circleCount;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof FrameLayout) {
                    final FrameLayout frameLayout = (FrameLayout) viewGroup.getChildAt(i);
                    for (int j = 0; j < frameLayout.getChildCount(); j++) {
                        final View view = frameLayout.getChildAt(j);
                        Animation animation = view.getAnimation();
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Tasks.post2MainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (viewGroup == null || viewGroup.getContext() == null || ((Activity) viewGroup.getContext()).isFinishing()) {
                                            return;
                                        }
                                        if (viewGroup.indexOfChild(frameLayout) > -1) {
                                            viewGroup.removeView(frameLayout);

                                            if (viewGroup.getChildCount() == otherChildNum) {
                                                viewGroup.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        view.startAnimation(animation);
                    }
                }
            }
        }
    }

    public void addData(CircleBean circleBean) {
        circleBeans.add(circleBean);
    }

    public void setData(ArrayList<CircleBean> circleBeans) {
        this.circleBeans = circleBeans;
    }


    public static class CircleBean {
        public ArrayList<Integer> items = new ArrayList<>(); //粒子列表
        public Point centerPoint; //圆心
        public int duration; //动画时间
        public int radius; //爆炸半径
        public int circleItemTypeCount = 1; //每个圆动画用几种粒子样式
        public int circleItemCount = 6; //一个圈的粒子个数
        public ArrayList<ImageView> imageViews = new ArrayList<>();

        public ValueAnimator valueAnimator;

        public CircleBean(int circleItemTypeCount, int duration, int radius, int circleItemCount, int x, int y) {
            this.circleItemTypeCount = circleItemTypeCount;
            this.duration = duration;
            this.radius = radius;
            this.circleItemCount = circleItemCount;
            centerPoint = new Point(x, y);
        }

        public void addItem(int r) {
            items.add(r);
        }

    }

}
