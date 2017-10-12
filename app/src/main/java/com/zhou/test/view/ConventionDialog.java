package com.zhou.test.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhou.test.R;

/**
 * @author zqm
 * @since 2017/8/31.
 */

public class ConventionDialog extends Dialog {

    public String conventionUrl = "https://www.bing.com/dict?FORM=Z9LH3";

    public ConventionDialog(final Activity context) {
        super(context);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.convention);
        initView();
    }

    private void initView() {
        findViewById(R.id.agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSaveFormData(true);
        webView.setInitialScale(50);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(conventionUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
