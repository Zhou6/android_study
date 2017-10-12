package com.zhou.test;

import android.app.Application;

/**
 * @author zqm
 * @since 2017/9/22
 */

public class ShareApplication extends Application {

    private static ShareApplication sInstance;

    public static ShareApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
