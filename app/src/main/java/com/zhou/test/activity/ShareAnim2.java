package com.zhou.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhou.test.GlideApp;
import com.zhou.test.R;

import java.util.ArrayList;
import java.util.List;

public class ShareAnim2 extends AppCompatActivity {

    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_anim2);
        // 延迟共享动画的执行
        postponeEnterTransition();
        urls = getIntent().getStringArrayListExtra("urls");
        int pos = getIntent().getIntExtra("position", 0);
//        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setResizeClip(true);
//        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setResizeClip(true);
//        changeBounds.setDuration(5000);
//        changeBounds.captureStartValues(new TransitionValues());

//        ChangeClipBounds changeClipBounds = new ChangeClipBounds();
//        ChangeTransform changeTransform = new ChangeTransform();
//        ChangeImageTransform changeImageTransform = new ChangeImageTransform();
//            changeImageTransform.createAnimator();
//        getWindow().setSharedElementEnterTransition(changeBounds);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
//        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new MyAdapter(urls));
        recyclerView.scrollToPosition(pos);
//        PhotoView a = findViewById(R.id.image2);
//        GlideApp.with(ShareAnim2.this).load("http://img1.lespark.cn/86cYopcNivJBHEdsXetm").into(a);
//        GlideApp.with(ShareAnim2.this).load("http://img1.lespark.cn/86cYopcNivJBHEdsXetm_640x640")
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//
//                        Handler sMainHandler = new Handler(ShareApplication.getInstance().getMainLooper());
//                        sMainHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                GlideApp.with(ShareAnim2.this).load("http://img1.lespark.cn/86cYopcNivJBHEdsXetm").into(a);
//                            }
//                        },400);
//                        return false;
//                    }
//                })
////                .thumbnail(Glide.with(ShareAnim2.this).load("http://img1.lespark.cn/86cYopcNivJBHEdsXetm_640x640"))
//                .into(a);
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        //启动动画
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishAfterTransition();
    }

    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        MyAdapter(@Nullable List<String> data) {
            super(R.layout.photo_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            scheduleStartPostponedTransition(helper.getView(R.id.photo));
            GlideApp.with(helper.getView(R.id.photo)).load(item).into((PhotoView) helper.getView(R.id.photo));
        }
    }
}
