package com.zhou.test.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lq
 * @since 2017/4/27 14:39
 */

public class DiffusionAnimator {
    public static final int BEHAVIOR_CUSTOM = -1;
    public static final int BEHAVIOR_CENTER = 0;
    public static final int BEHAVIOR_DROP = 1;
    public static final int BEHAVIOR_VIEWS = 2;
    public static final int BEHAVIOR_RISE = 4;
    private ArrayList<ParticleAnimBean> list;
    private ArrayList<ParticleBean> particleBeenList;
    private Context context = null;
    private RelativeLayout relativeLayout;
    private ViewGroup container;
    private Random random;
    private int density = 100;
    private int batch = 3;
    private int count = 1;
    private int[] srcId = {};
    private int currentBehavior = 0;
    private int duration = 2000;

    public void setDiffusionListenerAdapter(DiffusionListenerAdapter diffusionListenerAdapter) {
        this.diffusionListenerAdapter = diffusionListenerAdapter;
    }

    private DiffusionListenerAdapter diffusionListenerAdapter;

    public void setCustomInitViewPosition(CustomInitViewPosition customInitViewPosition) {
        this.customInitViewPosition = customInitViewPosition;
    }

    private CustomInitViewPosition customInitViewPosition;

    private boolean isReady;

    private DiffusionAnimator() {
    }


    public DiffusionAnimator(ArrayList<ParticleBean> views, int behaviorViews) {
        random = new Random();
        particleBeenList = views;
        currentBehavior = behaviorViews;
        isReady = true;
        initPosition(currentBehavior);
    }


    private DiffusionAnimator(ViewGroup container, int behavior, int count , int[] srcId) {
        this(container,behavior,count,srcId,null);
    }
    private DiffusionAnimator(final ViewGroup container, final int behavior, int count, int[] srcId, CustomInitViewPosition customInitViewPosition) {
        this.container = container;
        list = new ArrayList<>();
        random = new Random();
        context = container.getContext();
        this.count = count;
        this.srcId = srcId;
        this.customInitViewPosition = customInitViewPosition;
        if (customInitViewPosition == null) {
            currentBehavior = behavior;
        } else {
            currentBehavior = BEHAVIOR_CUSTOM;
        }
        relativeLayout = new RelativeLayout(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1,-1);
        container.addView(relativeLayout,layoutParams);
        for (int i = 0; i < count; i++) {
            final ParticleAnimBean particleAnimBean = new ParticleAnimBean();
            particleAnimBean.imageView = new ImageView(context);
            particleAnimBean.imageView.setVisibility(View.INVISIBLE);
            particleAnimBean.imageView.setImageResource(srcId[random.nextInt(srcId.length)]);
            RelativeLayout.LayoutParams  params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            relativeLayout.addView(particleAnimBean.imageView,params);
            list.add(particleAnimBean);
        }
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initPosition(behavior);
                isReady = true;
            }
        });

    }



    private void  initPosition(int behavior) {
        switch (behavior) {
            case BEHAVIOR_CENTER:
                center();
                break;
            case BEHAVIOR_DROP:
                drop();
                break;
            case BEHAVIOR_RISE:
                rise();
                break;
            case BEHAVIOR_CUSTOM:
                customInitViewPosition.initViewPosition(list,relativeLayout.getWidth(),relativeLayout.getHeight(),density,duration);
                break;
            case BEHAVIOR_VIEWS:
                views();
                break;
        }
    }

    private void views() {
        for (final ParticleBean particleAnimBean : particleBeenList) {
            BezierEvaluator bezierEvaluator = new BezierEvaluator(particleAnimBean.controlPoint);
            particleAnimBean.bezierEvaluator = bezierEvaluator;
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
            particleAnimBean.valueAnimator = valueAnimator;
            valueAnimator.setDuration(duration + random.nextInt(1000) - 500);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point pointF = particleAnimBean.bezierEvaluator.evaluate((float) animation.getAnimatedValue(), particleAnimBean.startPoint, particleAnimBean.endPoint);
                    particleAnimBean.view.setX(pointF.x);
                    particleAnimBean.view.setY(pointF.y);
                    particleAnimBean.view.setAlpha((Float) animation.getAnimatedValue());
                }
            });
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    particleAnimBean.view.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void drop() {
        for (final ParticleAnimBean particleAnimBean : list) {
            final float x = particleAnimBean.imageView.getX();
            final int height = relativeLayout.getHeight();
            final int width = relativeLayout.getWidth();
            int top1 = particleAnimBean.imageView.getTop();
            int height1 = particleAnimBean.imageView.getHeight();
            int top =  - particleAnimBean.imageView.getHeight();
            int left = random.nextInt(width);
            final float y = particleAnimBean.imageView.getY();
            final Point start = new Point(left,
                    top);
            particleAnimBean.startPoint = start;
            particleAnimBean.imageView.setX(start.x);
            particleAnimBean.imageView.setY(start.y);
            final Point end = new Point(left + random.nextInt(density) - density / 2,top + height + particleAnimBean.imageView.getHeight());
            particleAnimBean.endPoint = end;
            Point controllPoint = new Point(start.x + random.nextInt(600) - 300, start.y + random.nextInt(600) + particleAnimBean.imageView.getHeight());
            BezierEvaluator bezierEvaluator = new BezierEvaluator(controllPoint);
            particleAnimBean.bezierEvaluator = bezierEvaluator;
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
            particleAnimBean.valueAnimator = valueAnimator;
            valueAnimator.setDuration(duration + random.nextInt(1000) - 500);
            valueAnimator.setStartDelay(random.nextInt((int) (duration * 1.5)));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point pointF = particleAnimBean.bezierEvaluator.evaluate((float) animation.getAnimatedValue(), particleAnimBean.startPoint, particleAnimBean.endPoint);
                    particleAnimBean.imageView.setX(pointF.x);
                    particleAnimBean.imageView.setY(pointF.y);
                    particleAnimBean.imageView.setAlpha(1 - (Float) animation.getAnimatedValue());
                }
            });
            //valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    particleAnimBean.imageView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void rise() {
        for (final ParticleAnimBean particleAnimBean : list) {
            final float x = particleAnimBean.imageView.getX();
            final int height = relativeLayout.getHeight();
            final int width = relativeLayout.getWidth();
            int top1 = particleAnimBean.imageView.getTop();
            int height1 = particleAnimBean.imageView.getHeight();
            int top =  particleAnimBean.imageView.getHeight() + height;
            int left = random.nextInt(width);
            final float y = particleAnimBean.imageView.getY();
            final Point start = new Point(left, top);
            particleAnimBean.startPoint = start;
            particleAnimBean.imageView.setX(start.x);
            particleAnimBean.imageView.setY(start.y);
            final Point end = new Point(left + random.nextInt(density) - density / 2, 0);
            particleAnimBean.endPoint = end;
            Point controllPoint = new Point(start.x + random.nextInt(600) - 300, start.y + random.nextInt(600) + particleAnimBean.imageView.getHeight());
            BezierEvaluator bezierEvaluator = new BezierEvaluator(controllPoint);
            particleAnimBean.bezierEvaluator = bezierEvaluator;
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
            particleAnimBean.valueAnimator = valueAnimator;
            valueAnimator.setDuration(duration + random.nextInt(1000) - 500);
            valueAnimator.setStartDelay(random.nextInt((int) (duration * 1.5)));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point pointF = particleAnimBean.bezierEvaluator.evaluate((float) animation.getAnimatedValue(), particleAnimBean.startPoint, particleAnimBean.endPoint);
                    particleAnimBean.imageView.setX(pointF.x);
                    particleAnimBean.imageView.setY(pointF.y);
                    particleAnimBean.imageView.setAlpha(1 - (Float) animation.getAnimatedValue());
                }
            });
            //valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    particleAnimBean.imageView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void center() {
        for (final ParticleAnimBean particleAnimBean : list) {
            final float x = particleAnimBean.imageView.getX();
            final int height = relativeLayout.getHeight();
            final int width = relativeLayout.getWidth();
            final float y = particleAnimBean.imageView.getY();
            final Point start = new Point((int) (x + random.nextInt(density) - density / 2),
                    (int) (y + random.nextInt(density / 2) - density / 4));
            particleAnimBean.startPoint = start;
            particleAnimBean.imageView.setX(start.x);
            particleAnimBean.imageView.setY(start.y);
            final Point end = new Point(random.nextInt(width),random.nextInt(height));
            particleAnimBean.endPoint = end;
            Point controllPoint = new Point(start.x + random.nextInt(600) - 300, start.y + random.nextInt(600) - 300);
            BezierEvaluator bezierEvaluator = new BezierEvaluator(controllPoint);
            particleAnimBean.bezierEvaluator = bezierEvaluator;
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
            final ValueAnimator valueAnimatortanchu = ValueAnimator.ofFloat(0,1);
            particleAnimBean.valueAnimator = valueAnimator;
            particleAnimBean.valueAnimatortanchu = valueAnimatortanchu;
            valueAnimatortanchu.setDuration(300);
            valueAnimatortanchu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    particleAnimBean.imageView.setScaleX((Float) animation.getAnimatedValue());
                    particleAnimBean.imageView.setScaleY((Float) animation.getAnimatedValue());
                }
            });
            valueAnimatortanchu.setInterpolator(new OvershootInterpolator());
            valueAnimatortanchu.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {


                }

                @Override
                public void onAnimationStart(Animator animation) {
                    particleAnimBean.imageView.setVisibility(View.VISIBLE);
                }
            });
            valueAnimator.setDuration(duration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point pointF = particleAnimBean.bezierEvaluator.evaluate((float) animation.getAnimatedValue(), particleAnimBean.startPoint, particleAnimBean.endPoint);
                    particleAnimBean.imageView.setX(pointF.x);
                    particleAnimBean.imageView.setY(pointF.y);
                    particleAnimBean.imageView.setAlpha(1 - (Float) animation.getAnimatedValue());
                }
            });
            valueAnimator.setInterpolator(new DecelerateInterpolator());
        }
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        final ValueAnimator valueAnimatortanchu = ValueAnimator.ofFloat(0,1);
        valueAnimatortanchu.setDuration(300);
        valueAnimatortanchu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (ParticleAnimBean particleAnimBean : list) {
                    particleAnimBean.imageView.setScaleX((Float) animation.getAnimatedValue());
                    particleAnimBean.imageView.setScaleY((Float) animation.getAnimatedValue());
                }
            }
        });
        valueAnimatortanchu.setInterpolator(new OvershootInterpolator());
        valueAnimatortanchu.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationStart(Animator animation) {
                for (ParticleAnimBean particleAnimBean : list) {
                    particleAnimBean.imageView.setVisibility(View.VISIBLE);
                }
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (ParticleAnimBean particleAnimBean : list) {
                    Point pointF = particleAnimBean.bezierEvaluator.evaluate((float) animation.getAnimatedValue(), particleAnimBean.startPoint, particleAnimBean.endPoint);
                    particleAnimBean.imageView.setX(pointF.x);
                    particleAnimBean.imageView.setY(pointF.y);
                    particleAnimBean.imageView.setAlpha(1 - (Float) animation.getAnimatedValue());
                }
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
    }

    public DiffusionAnimator setDuration(final int duration) {

        if (this.duration == duration || duration < 0) {
            return this;
        }
        if (isReady) {
            end();
            if (list != null) {
                for (ParticleAnimBean particleAnimBean : list) {
                    particleAnimBean.valueAnimator.setDuration(duration);
                }
            }
            if (particleBeenList != null) {
                for (ParticleBean particleBean : particleBeenList) {
                    particleBean.valueAnimator.setDuration(duration);
                }
            }
        } else {
            final Timer timer = new Timer();
            final TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (isReady) {
                        relativeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                end();
                                if (list != null) {
                                    for (ParticleAnimBean particleAnimBean : list) {
                                        particleAnimBean.valueAnimator.setDuration(duration);
                                    }
                                }
                                if (particleBeenList != null) {
                                    for (ParticleBean particleBean : particleBeenList) {
                                        particleBean.valueAnimator.setDuration(duration);
                                    }
                                }
                            }
                        });
                        timer.cancel();
                    }
                }
            };
            timer.schedule(timerTask, 0, 10);
        }
        return this;
    }
    public void destroy() {
        relativeLayout.removeAllViews();
        container.removeView(relativeLayout);
        diffusionListenerAdapter = null;
    }

    public void end() {
        if(list != null && list.size() > 0) {
            for (ParticleAnimBean particleAnimBean : list) {
                particleAnimBean.imageView.setVisibility(View.INVISIBLE);
//            particleAnimBean.valueAnimatortanchu.removeAllListeners();
                if (particleAnimBean.valueAnimatortanchu != null) {
                    particleAnimBean.valueAnimatortanchu.end();
                }
//            particleAnimBean.valueAnimator.removeAllListeners();
                if (particleAnimBean.valueAnimator != null) {
                    particleAnimBean.valueAnimator.end();
                }
            }
        }
        if (particleBeenList != null && particleBeenList.size() > 0) {
            for (ParticleBean particleAnimBean : particleBeenList) {
                particleAnimBean.view.setVisibility(View.INVISIBLE);
//            particleAnimBean.valueAnimatortanchu.removeAllListeners();
                if (particleAnimBean.valueAnimatortanchu != null) {
                    particleAnimBean.valueAnimatortanchu.end();
                }
//            particleAnimBean.valueAnimator.removeAllListeners();
                if (particleAnimBean.valueAnimator != null) {
                    particleAnimBean.valueAnimator.end();
                }
            }
        }
    }

    public static DiffusionAnimator ofPosition(ViewGroup container, int behavior, int count , int[] srcId) {
        DiffusionAnimator diffusionAnimator = new DiffusionAnimator(container,behavior,count,srcId);
        return diffusionAnimator;
    }

    public static DiffusionAnimator ofParticleAnimBean(ArrayList<ParticleBean> views) {
        DiffusionAnimator diffusionAnimator = new DiffusionAnimator(views,BEHAVIOR_VIEWS);
        return diffusionAnimator;
    }

    public static DiffusionAnimator ofCustomPosition(ViewGroup container, int count , int[] srcId, CustomInitViewPosition customInitViewPosition) {
        DiffusionAnimator diffusionAnimator = new DiffusionAnimator(container,BEHAVIOR_CUSTOM,count,srcId,customInitViewPosition);
        return diffusionAnimator;
    }

    public void start() {
        if (isReady) {
            end();
            if (currentBehavior == BEHAVIOR_CENTER) {
                startStandard();
            } else if (currentBehavior == BEHAVIOR_DROP) {
                startDrop();
            } else if (currentBehavior == BEHAVIOR_CUSTOM) {
                startCustom();
            } else if (currentBehavior == BEHAVIOR_VIEWS) {
                startViews();
            } else if (currentBehavior == BEHAVIOR_RISE) {
                startRise();
            }
        } else {
            final Timer timer = new Timer();
            final TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (isReady) {
                        relativeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                end();
                                if (currentBehavior == BEHAVIOR_CENTER) {
                                    startStandard();
                                } else if (currentBehavior == BEHAVIOR_DROP) {
                                    startDrop();
                                } else if (currentBehavior == BEHAVIOR_RISE) {
                                    startRise();
                                }
                                Log.e("ggg", "start");
                            }
                        });
                        timer.cancel();
                    }
                }
            };
            timer.schedule(timerTask, 0, 10);
        }
    }

    private void startViews() {
        for (int i = 0; i < particleBeenList.size(); i++) {
            ParticleBean particleAnimBean = particleBeenList.get(i);
            particleAnimBean.valueAnimator.start();
            if (particleAnimBean.valueAnimatortanchu != null) {
                particleAnimBean.valueAnimatortanchu.start();
            }
            if (i == particleBeenList.size() - 1) {
                particleAnimBean.valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (diffusionListenerAdapter != null) {
                            diffusionListenerAdapter.end();

                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                });
            }
        }
    }

    private void startCustom() {
        for (int i = 0; i < list.size(); i++) {
            ParticleAnimBean particleAnimBean = list.get(i);
            particleAnimBean.valueAnimator.start();
            if (particleAnimBean.valueAnimatortanchu != null) {
                particleAnimBean.valueAnimatortanchu.start();
            }
            if (i == list.size() - 1) {
                particleAnimBean.valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (diffusionListenerAdapter != null) {
                            diffusionListenerAdapter.end();

                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                });
            }
        }
    }

    private void startDrop() {
        for (int i = 0; i < list.size(); i++) {
            ParticleAnimBean particleAnimBean = list.get(i);
            particleAnimBean.valueAnimator.start();
            if (i == list.size() - 1) {
                particleAnimBean.valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (diffusionListenerAdapter != null) {
                            diffusionListenerAdapter.end();

                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                });
            }
        }
    }

    private void startRise() {
        for (int i = 0; i < list.size(); i++) {
            ParticleAnimBean particleAnimBean = list.get(i);
            particleAnimBean.valueAnimator.start();
            if (i == list.size() - 1) {
                particleAnimBean.valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (diffusionListenerAdapter != null) {
                            diffusionListenerAdapter.end();

                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                });
            }
        }
    }

    private void startStandard() {
        int i1 = list.size() / batch;
        for (int i = 0 ; i < batch; i ++) {
            for (int j = i * i1; j < (i == batch - 1 ? list.size() : i1 + i1 * i); j++) {
                final ParticleAnimBean particleAnimBean = list.get(j);
                particleAnimBean.valueAnimatortanchu.setStartDelay(i * 200);
                particleAnimBean.valueAnimatortanchu.start();
                particleAnimBean.valueAnimator.setStartDelay(i * 200);
                particleAnimBean.valueAnimator.start();
                if (j == list.size() - 1) {
                    particleAnimBean.valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (diffusionListenerAdapter != null) {
                                diffusionListenerAdapter.end();

                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }
                    });
                }
            }
        }
    }

    public static class DiffusionListenerAdapter {
        public void end() {

        }
        public void cancel() {

        }
    }

    public static class BezierEvaluator implements TypeEvaluator<Point> {

        private Point controllPoint;
        private int behavior;

        public BezierEvaluator(Point controllPoint) {
            this.controllPoint = controllPoint;
        }

        public BezierEvaluator(Point controllPoint, int behavior) {
            this.controllPoint = controllPoint;
            this.controllPoint = controllPoint;
            this.behavior = behavior;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            if (behavior != DiffusionAnimator.BEHAVIOR_CUSTOM) {
                int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
                int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
                return new Point(x, y);
            } else {
                int x = (int) (startValue.x - (startValue.x - endValue.x) * t);
                int y = (int) (startValue.y - (startValue.y - endValue.y) * t);
                return new Point(x,y);
            }
        }
    }

    public interface CustomInitViewPosition{
        void initViewPosition(ArrayList<ParticleAnimBean> list, int width, int height, int density, int duration);
    }

    public  static class ParticleAnimBean {
        public ImageView imageView;
        public Point startPoint;
        public Point endPoint;
        public Point controlPoint;
        public BezierEvaluator bezierEvaluator;
        public ValueAnimator valueAnimator;
        public ValueAnimator valueAnimatortanchu;
    }
    public static class ParticleBean {
        public View view;
        public Point startPoint;
        public Point endPoint;
        public Point controlPoint;
        public BezierEvaluator bezierEvaluator;
        public ValueAnimator valueAnimator;
        public ValueAnimator valueAnimatortanchu;
    }
}
