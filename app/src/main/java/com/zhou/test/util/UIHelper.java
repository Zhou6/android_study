package com.zhou.test.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.zhou.test.ShareApplication;

/**
 * @author zqm
 * @since 2017/9/22
 */

public class UIHelper {


    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    private static DisplayMetrics getDisplayMetricsInternal(Context activity) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metric);
        return metric;
    }

    public static int getScreenWidth() {
        if (sScreenWidth == 0) {
            DisplayMetrics metric = getDisplayMetricsInternal(ShareApplication.getInstance());
            sScreenWidth = metric.widthPixels;
        }
        return sScreenWidth;
    }

    public static int getScreenHeight() {
        if (sScreenHeight == 0) {
            DisplayMetrics metric = getDisplayMetricsInternal(ShareApplication.getInstance());
            sScreenHeight = metric.heightPixels;
        }
        return sScreenHeight;
    }


    public static int px2dip(float pxValue) {
        final float scale = ShareApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(double dpValue) {
        final float scale = ShareApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
