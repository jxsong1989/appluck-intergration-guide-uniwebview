package com.appluck.webview_library;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 支持预加载的webview
 */
public class AppLuckPreLoadWebViewActivity extends AppCompatActivity {

    public static WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(webView);
        /*ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        decorView.addView(webView);*/
        //setContentView(R.layout.activity_app_luck_pre_load_web_view);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(webView);
            }
        }
        webView.reload();
        super.onBackPressed();
    }
}