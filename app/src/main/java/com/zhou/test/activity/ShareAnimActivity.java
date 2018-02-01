package com.zhou.test.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhou.test.GlideApp;
import com.zhou.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShareAnimActivity extends AppCompatActivity {

    private ImageView mImageView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    GlideApp.with(ShareAnimActivity.this).load("http://img1.lespark.cn/86cYopcNivJBHEdsXetm_640x640").into(mImageView);
                    return true;
                case R.id.navigation_dashboard:
                    GlideApp.with(ShareAnimActivity.this).load("http://img1.lespark.cn/86LHlIsBpkgAVrfXsRXk_640x640").into(mImageView);
                    return true;
                case R.id.navigation_notifications:
                    GlideApp.with(ShareAnimActivity.this).load("http://img1.lespark.cn/86ZnNalzcpVWhpatDFrC_640x640").into(mImageView);
                    return true;
            }
            return false;
        }
    };
    private ImageView mImageView2, mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_anim);

        mImageView = findViewById(R.id.image);
        mImageView2 = findViewById(R.id.image2);
        mImageView3 = findViewById(R.id.image3);
        final Intent intent = new Intent(ShareAnimActivity.this, ShareAnim2.class);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("http://img1.lespark.cn/86cYopcNivJBHEdsXetm");
        arrayList.add("http://img1.lespark.cn/86LHlIsBpkgAVrfXsRXk");
        arrayList.add("http://img1.lespark.cn/86ZnNalzcpVWhpatDFrC");
        intent.putExtra("urls", arrayList);
        GlideApp.with(ShareAnimActivity.this).load(arrayList.get(0) + "_640x640").into(mImageView);
        GlideApp.with(ShareAnimActivity.this).load(arrayList.get(1) + "_640x640").into(mImageView2);
        GlideApp.with(ShareAnimActivity.this).load(arrayList.get(2) + "_640x640").into(mImageView3);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("position", 0);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(ShareAnimActivity.this,
                                mImageView, "image_share").toBundle());
            }
        });

        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("position", 1);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(ShareAnimActivity.this,
                                mImageView2, "image_share").toBundle());
            }
        });

        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("position", 2);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(ShareAnimActivity.this,
                                mImageView3, "image_share").toBundle());
            }
        });
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//
//        setExitSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                super.onMapSharedElements(names, sharedElements);
//                sharedElements.put("image_share", mImageView3);
//            }
//        });
    }

}
