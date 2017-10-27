package com.zhou.test.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhou.test.R;
import com.zhou.test.util.UIHelper;


/**
 * @author zqm
 * @since 2017/3/27.
 */

public class NewFeedView extends RelativeLayout implements View.OnClickListener {
    public static final String NEW_FEED = "new_feed";
    private final Context mContext;
    private ImageView parent_iv, textImg, redPacketImg, photoImg, recordingImg;
    private RelativeLayout all_rl;

    private int buttonMarginLeft, buttonMarginTop = 0;
    private int buttonMarginRight;
    private int buttonMarginBottom;
    private int buttonLayoutAlignParent = 12; //默认右下角
    private int radius;//默认半径120dp

    public NewFeedView(Context context) {
        this(context, null);
    }

    public NewFeedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewFeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        buttonMarginRight = UIHelper.dip2px(20);
        buttonMarginBottom = UIHelper.dip2px(20);
        radius = UIHelper.dip2px(120);//默认半径120dp
        initAttrs(attrs);
        initView();
        initListener();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.NewFeedView);
        buttonMarginLeft = a.getDimensionPixelSize(R.styleable.NewFeedView_button_marginLeft, buttonMarginLeft);
        buttonMarginRight = a.getDimensionPixelSize(R.styleable.NewFeedView_button_marginRight, buttonMarginRight);
        buttonMarginTop = a.getDimensionPixelSize(R.styleable.NewFeedView_button_marginTop, buttonMarginTop);
        buttonMarginBottom = a.getDimensionPixelSize(R.styleable.NewFeedView_button_marginBottom, buttonMarginBottom);
        radius = a.getDimensionPixelSize(R.styleable.NewFeedView_radius, radius);
        buttonLayoutAlignParent = a.getInteger(R.styleable.NewFeedView_button_layout_alignParent, buttonLayoutAlignParent);
        a.recycle();
    }

    public void initView() {
        inflate(mContext, R.layout.new_feed_view, this);
        parent_iv = (ImageView) findViewById(R.id.parent_iv);
        all_rl = (RelativeLayout) findViewById(R.id.all_rl);
        textImg = (ImageView) findViewById(R.id.text_only);
        redPacketImg = (ImageView) findViewById(R.id.red_packet);
        photoImg = (ImageView) findViewById(R.id.photo);
        recordingImg = (ImageView) findViewById(R.id.recording);

        RelativeLayout.LayoutParams lp = setLayoutAlignParent();
        lp.setMargins(0, 0, buttonMarginRight, buttonMarginBottom);
        parent_iv.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp1 = setLayoutAlignParent();
        lp1.setMargins(0, 0, buttonMarginRight, buttonMarginBottom + radius);
        textImg.setLayoutParams(lp1);

        RelativeLayout.LayoutParams lp2 = setLayoutAlignParent();
        lp2.setMargins(0, 0, buttonMarginRight + radius / 2, buttonMarginBottom + (int) (radius / 2 * 1.732));
        redPacketImg.setLayoutParams(lp2);

        RelativeLayout.LayoutParams lp3 = setLayoutAlignParent();
        lp3.setMargins(0, 0, buttonMarginRight + (int) (radius / 2 * 1.732), buttonMarginBottom + radius / 2);
        photoImg.setLayoutParams(lp3);

        RelativeLayout.LayoutParams lp4 = setLayoutAlignParent();
        lp4.setMargins(0, 0, buttonMarginRight + radius, buttonMarginBottom);
        recordingImg.setLayoutParams(lp4);
    }

    private RelativeLayout.LayoutParams setLayoutAlignParent() {
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (buttonLayoutAlignParent == 1) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else if (buttonLayoutAlignParent == 2) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (buttonLayoutAlignParent == 4) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else if (buttonLayoutAlignParent == 8) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (buttonLayoutAlignParent == 12) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        return layoutParams;
    }

    private void initListener() {
        parent_iv.setOnClickListener(this);
        textImg.setOnClickListener(this);
        redPacketImg.setOnClickListener(this);
        photoImg.setOnClickListener(this);
        recordingImg.setOnClickListener(this);
        all_rl.setOnClickListener(this);
    }

    private boolean isGoingToGone = false;

    private ObjectAnimator setParentIvAnimation(boolean isOpen, View view) {
        int start = 0;
        int end = 135;
        if (!isOpen) {
            start = 135;
            end = 0;
        }
        ObjectAnimator parentAnim = ObjectAnimator.ofFloat(view, "rotation", start, end);
        parentAnim.setDuration(200);
        return parentAnim;
    }

    private ObjectAnimator setBackgroundAnim(final boolean isOpen, View view) {
        int start = 0;
        int end = 1;
        if (!isOpen) {
            start = 1;
            end = 0;
        }
        ObjectAnimator backgroundAnim = ObjectAnimator.ofFloat(view, "Alpha", start, end);
        backgroundAnim.setDuration(200).setStartDelay(100);
        backgroundAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isOpen) {
                    all_rl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (isOpen) {
                    all_rl.setVisibility(View.VISIBLE);
                    isGoingToGone = false;
                } else {
                    isGoingToGone = true;
                }
            }
        });
        return backgroundAnim;
    }

    private ObjectAnimator setRotateAnim(boolean isOpen, View view) {
        int start = 0;
        int end = 360;
        if (!isOpen) {
            start = 360;
            end = 0;
        }
        ObjectAnimator childCommonAnim = ObjectAnimator.ofFloat(view, "rotation", start, end);
        childCommonAnim.setDuration(100).setInterpolator(new AccelerateInterpolator());
        if (isOpen) {
            childCommonAnim.setStartDelay(200);
        }
        return childCommonAnim;
    }

    private ObjectAnimator setChildAnim(boolean isOpen, View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        if (!isOpen) {
            float tempX = fromXDelta;
            fromXDelta = toXDelta;
            toXDelta = tempX;
            float tempY = fromYDelta;
            fromYDelta = toYDelta;
            toYDelta = tempY;
        }
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", fromXDelta, toXDelta);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", fromYDelta, toYDelta);
        ObjectAnimator childAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY);
        childAnim.setDuration(200);
        if (!isOpen) {
            childAnim.setStartDelay(100);
        }
        return childAnim;
    }

    public void setAllAnimotion(boolean isOpen) {
        ObjectAnimator parentAnim = setParentIvAnimation(isOpen, parent_iv);
        ObjectAnimator backgroundAnim = setBackgroundAnim(isOpen, all_rl);
        ObjectAnimator rotateAnim1 = setRotateAnim(isOpen, textImg);
        ObjectAnimator rotateAnim2 = setRotateAnim(isOpen, redPacketImg);
        ObjectAnimator rotateAnim3 = setRotateAnim(isOpen, photoImg);
        ObjectAnimator rotateAnim4 = setRotateAnim(isOpen, recordingImg);
        ObjectAnimator anim1 = setChildAnim(isOpen, textImg, 0, 0, radius, 0);
        ObjectAnimator anim2 = setChildAnim(isOpen, redPacketImg, radius / 2, 0, radius / 2 * 1.732f, 0);
        ObjectAnimator anim3 = setChildAnim(isOpen, photoImg, radius / 2 * 1.732f, 0, radius / 2, 0);
        ObjectAnimator anim4 = setChildAnim(isOpen, recordingImg, radius, 0, 0, 0);

        AnimatorSet as = new AnimatorSet();
        as.play(parentAnim).with(backgroundAnim)
                .with(rotateAnim1).with(rotateAnim2).with(rotateAnim3).with(rotateAnim4)
                .with(anim1).with(anim2).with(anim3).with(anim4);
        as.start();
    }

    public interface ButtonClickListener {
        void TextOnlyClickListner();

        void RedPacketClickListner();

        void PhotoClickListner();

        void RecordingClickListner();
    }

    private ButtonClickListener listener;

    public void setListener(ButtonClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parent_iv:
                if (all_rl.getVisibility() == VISIBLE) {
                    setAllAnimotion(false);
                    return;
                }
                setAllAnimotion(true);
                break;
            case R.id.text_only:
                if (listener != null) {
                    listener.TextOnlyClickListner();
                }
                setAllAnimotion(false);
                break;
            case R.id.red_packet:
                if (listener != null) {
                    listener.RedPacketClickListner();
                }
                setAllAnimotion(false);
                break;
            case R.id.photo:
                if (listener != null) {
                    listener.PhotoClickListner();
                }
                setAllAnimotion(false);
                break;
            case R.id.recording:
                if (listener != null) {
                    listener.RecordingClickListner();
                }
                setAllAnimotion(false);
                break;
            case R.id.all_rl:
                if (all_rl.getVisibility() == VISIBLE && !isGoingToGone) {
                    setAllAnimotion(false);
                }
                break;
        }
    }
}
