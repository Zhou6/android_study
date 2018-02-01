package com.zhou.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.zhou.test.R;
import com.zhou.test.ShareApplication;
import com.zhou.test.view.BoomAnimator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom);
        RelativeLayout rl = findViewById(R.id.rl);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            list.add(getResources().getIdentifier("boom_" + (i + 1), "drawable", ShareApplication.getInstance().getPackageName()));
        }
        BoomAnimator animator = new BoomAnimator(rl, list, 1800, 60, 6);
        animator.start();
    }
}
