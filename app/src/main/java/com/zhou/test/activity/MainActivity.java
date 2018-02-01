package com.zhou.test.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.faceunity.fulivedemo.FaceUMainActivity;
import com.zhou.test.R;
import com.zhou.test.ShareApplication;
import com.zhou.test.model.Model;
import com.zhou.test.util.Tasks;
import com.zhou.test.util.UIHelper;
import com.zhou.test.view.ConventionDialog;
import com.zhou.test.view.NewFeedView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Model> list = new ArrayList<>();
    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        list.add(new Model("富士山", FujiAnim.class));
        list.add(new Model("专属礼物", ExclusiveAnim.class));
        list.add(new Model("大喇叭", BarrageActivity.class));
        list.add(new Model("新跑车", CarActivity.class));
        list.add(new Model("法拉利", FLLActivity.class));
        list.add(new Model("遮挡", TestImageActivity.class));
        list.add(new Model("共享元素", ShareAnimActivity.class));
        list.add(new Model("贴纸", FaceUMainActivity.class));
        list.add(new Model("爆炸", BoomActivity.class));
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
                switch (view.getId()) {
                    case R.id.text:
                        startActivity(new Intent(MainActivity.this, ((Model) adapter.getData().get(position)).clazz));
                        break;
                }
            }
        });
        final NewFeedView newView = (NewFeedView) findViewById(R.id.new_feed_view);
        newView.setListener(new NewFeedView.ButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void TextOnlyClickListner(View v) {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra("style", 0);
                //第三个参数决定了ActivityTwo 布局中的android:transitionName的值，它们要保持一致
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, newView.getAddView(), "shareTransition").toBundle());
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void RedPacketClickListner(View v) {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra("style", 1);
                //第三个参数决定了ActivityTwo 布局中的android:transitionName的值，它们要保持一致
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, newView.getAddView(), "shareTransition").toBundle());
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void PhotoClickListner(View v) {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra("style", 2);
                //第三个参数决定了ActivityTwo 布局中的android:transitionName的值，它们要保持一致
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, newView.getAddView(), "shareTransition").toBundle());
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void RecordingClickListner(View v) {
                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                intent.putExtra("style", 3);
                //第三个参数决定了ActivityTwo 布局中的android:transitionName的值，它们要保持一致
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, newView.getAddView(), "shareTransition").toBundle());
            }
        });
        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConventionDialog();
            }
        });
        findViewById(R.id.music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeControlStream(AudioManager.STREAM_MUSIC);
                doControlMusic();
            }
        });
        findViewById(R.id.music2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeControlStream(AudioManager.STREAM_MUSIC);
                doControlMusic2();
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

    private boolean isMusicOpen = false;
    private boolean isMusicOpen2 = false;

    private void doControlMusic() {
        isMusicOpen = !isMusicOpen;
        if (isMusicOpen) {
            if (mSoundPool == null) { //soundPool适合较小音效，延迟低
                mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 5);
                soundID.put(1, mSoundPool.load(this, R.raw.turn_table_sound, 5));
                Tasks.postDelayed2MainThread(new Runnable() {
                    @Override
                    public void run() {
                        mSoundPool.play(soundID.get(1), 1, 1, 0, -1, 1);
                    }
                }, 1000);
            } else {
                mSoundPool.resume(soundID.get(1));
            }
        } else {
            if (mSoundPool != null) {
                mSoundPool.pause(soundID.get(1));
            }
        }
    }

    private MediaPlayer mediaPlayer;

    private void doControlMusic2() {
        isMusicOpen2 = !isMusicOpen2;
        if (isMusicOpen2) {
            if (mediaPlayer == null) {
                try {
                    AssetFileDescriptor assetFileDescriptor = ShareApplication.getInstance().getAssets().openFd("wawawa.mp3");
                    mediaPlayer = new MediaPlayer();
                    long start = assetFileDescriptor.getStartOffset();
                    long end = assetFileDescriptor.getLength();
                    mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), start, end);
                    mediaPlayer.prepare();
                    mediaPlayer.setVolume(0.5f, 0.5f);
                    mediaPlayer.start();
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Log.e("AAAA", what + ":" + extra);
                            return false;
                        }
                    });
                } catch (Exception e) {
                }
            } else {
                mediaPlayer.start();
            }
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }
    }
}


