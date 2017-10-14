package com.zhou.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.zhou.test.R;
import com.zhou.test.model.Model;
import com.zhou.test.util.UIHelper;
import com.zhou.test.view.ConventionDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Model> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        list.add(new Model("富士山", FujiAnim.class));
        list.add(new Model("主播专属礼物", ExclusiveAnim.class));
        list.add(new Model("大喇叭", BarrageActivity.class));
        list.add(new Model("新跑车", CarActivity.class));
        list.add(new Model("tz", SurfaceActivity.class));
        list.add(new Model("法拉利", FLLActivity.class));
        list.add(new Model("遮挡", TestImageActivity.class));
    }

    private ConventionDialog conventionDialog;
    private void showConventionDialog() {
        if (conventionDialog != null && conventionDialog.isShowing()) {
            conventionDialog.dismiss();
        }
        conventionDialog = new ConventionDialog(this);
        Window dialogWindow = conventionDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        conventionDialog.show();
        WindowManager.LayoutParams lp = conventionDialog.getWindow().getAttributes();
        lp.width = UIHelper.getScreenWidth() - UIHelper.dip2px(38);
        lp.height = UIHelper.getScreenHeight() - UIHelper.dip2px(182);
        conventionDialog.getWindow().setAttributes(lp);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.all);
        MyAdapter adapter = new MyAdapter(R.layout.item_layout, list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this, ((Model) adapter.getData().get(position)).clazz));
            }
        });
        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConventionDialog();
            }
        });
    }

    class MyAdapter extends BaseQuickAdapter<Model, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Model item) {
            helper.setText(R.id.text, item.text).addOnClickListener(R.id.text);
        }
    }
}


