package com.zhou.test.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.zhou.test.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MySlide extends Slide {

    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";

    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();

    public MySlide() {
        super();
    }

    private interface CalculateSlide {

        /**
         * Returns the translation value for view when it goes out of the scene
         */
        float getGoneX(ViewGroup sceneRoot, View view, float fraction);

        /**
         * Returns the translation value for view when it goes out of the scene
         */
        float getGoneY(ViewGroup sceneRoot, View view, float fraction);
    }

    private static abstract class CalculateSlideVertical implements CalculateSlide {

        @Override
        public float getGoneX(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationX();
        }
    }

    private static abstract class CalculateSlideHorizontal implements CalculateSlide {

        @Override
        public float getGoneY(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationY();
        }
    }

    private static final CalculateSlide sCalculateLeft = new CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationX() - sceneRoot.getWidth() * fraction;
        }
    };

    private static final CalculateSlide sCalculateStart = new CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view, float fraction) {
            final boolean isRtl = sceneRoot.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
            final float x;
            if (isRtl) {
                x = view.getTranslationX() + sceneRoot.getWidth() * fraction;
            } else {
                x = view.getTranslationX() - sceneRoot.getWidth() * fraction;
            }
            return x;
        }
    };

    private static final CalculateSlide sCalculateTop = new CalculateSlideVertical() {
        @Override
        public float getGoneY(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationY() - sceneRoot.getHeight() * fraction;
        }
    };

    private static final CalculateSlide sCalculateRight = new CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationX() + sceneRoot.getWidth() * fraction;
        }
    };

    private static final CalculateSlide sCalculateBottom = new CalculateSlideVertical() {
        @Override
        public float getGoneY(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationY() + sceneRoot.getHeight() * fraction;
        }
    };

    private static final CalculateSlide sCalculateEnd = new CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view, float fraction) {
            final boolean isRtl = sceneRoot.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
            final float x;
            if (isRtl) {
                x = view.getTranslationX() - sceneRoot.getWidth() * fraction;
            } else {
                x = view.getTranslationX() + sceneRoot.getWidth() * fraction;
            }
            return x;
        }
    };

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {

        if (endValues == null) {
            return null;
        }
        int[] position = (int[]) endValues.values.get(PROPNAME_SCREEN_POSITION);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        float startX = sCalculateRight.getGoneX(sceneRoot, view, 1);
        float startY = sCalculateRight.getGoneY(sceneRoot, view, 1);
        return createAnimation(view, endValues, position[0], position[1],
                startX, startY, endX, endY, sDecelerate, this, true);

    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view,
                                TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null) {
            return null;
        }
        int[] position = (int[]) startValues.values.get(PROPNAME_SCREEN_POSITION);
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = sCalculateBottom.getGoneX(sceneRoot, view, 1);
        float endY = sCalculateBottom.getGoneY(sceneRoot, view, 1);
        return createAnimation(view, startValues, position[0], position[1],
                startX, startY, endX, endY, sAccelerate, this, false);
    }

    public Animator createAnimation(View view, TransitionValues values, int viewPosX, int viewPosY,
                                    float startX, float startY, float endX, float endY, TimeInterpolator interpolator,
                                    Transition transition, boolean isIn) {

        float terminalX = view.getTranslationX();
        float terminalY = view.getTranslationY();
        int[] startPosition = (int[]) values.view.getTag(R.id.transitionPosition);
        if (startPosition != null) {
            startX = startPosition[0] - viewPosX + terminalX;
            startY = startPosition[1] - viewPosY + terminalY;
        }
        // Initial position is at translation startX, startY, so position is offset by that amount
        int startPosX = viewPosX + Math.round(startX - terminalX);
        int startPosY = viewPosY + Math.round(startY - terminalY);

        view.setTranslationX(startX);
        view.setTranslationY(startY);
        if (startX == endX && startY == endY) {
            return null;
        }
        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, View.TRANSLATION_Y,
                path);

        anim.setInterpolator(interpolator);
        return anim;
    }
}