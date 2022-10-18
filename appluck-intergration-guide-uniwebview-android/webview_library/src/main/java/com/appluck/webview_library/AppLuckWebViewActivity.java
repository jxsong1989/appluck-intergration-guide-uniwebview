package com.appluck.webview_library;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

/**
 * webview activity
 */
public class AppLuckWebViewActivity extends Activity implements AppLuckBaseActivity {

    private WebView webView;

    private Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_luck_web_view);
        webView = findViewById(R.id.webview);
        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener((view) -> {
            if (webView != null) {
                final String url = webView.getUrl();
                if (StringUtils.contains(url, "/baltan")) {
                    super.onBackPressed();
                    return;
                } else {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return;
                    }
                }
            }
            super.onBackPressed();
        });
        webView.addJavascriptInterface(new AppLuckActivityInterface(this), "AppLuckActivityInterface");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(@NonNull WebView view, @NonNull WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.d("url", url);
                //支持google play
                if (StringUtils.startsWith(url, "market:")
                        || StringUtils.startsWith(url, "https://play.google.com/store/")
                        || StringUtils.startsWith(url, "http://play.google.com/store/")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        //下载处理
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 允许javascript执行
        webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要，开启DOM缓存，开启LocalStorage存储
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void closeShow() {
        if (closeBtn != null) {
            this.runOnUiThread(() -> {
                closeBtn.setVisibility(View.VISIBLE);
            });
        }
    }

    @Override
    public void closeHide() {
        if (closeBtn != null) {
            this.runOnUiThread(() -> {
                closeBtn.setVisibility(View.INVISIBLE);
            });
        }
    }
}