package com.appluck.webview_library;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.unity3d.player.UnityPlayer;

import org.apache.commons.lang3.StringUtils;

public class AppLuckWebView {

    private final static String PLACEMENT_URL = "https://aios.soinluck.com/scene?sk=%s&lzdid=%s";

    private AppLuckWebView() {

    }

    public static void openUrl(String sk, String gaid) {
        final Activity currentActivity = UnityPlayer.currentActivity;
        Intent intent = new Intent(currentActivity, AppLuckWebViewActivity.class);
        intent.putExtra("url", String.format(PLACEMENT_URL, sk, gaid));
        currentActivity.startActivity(intent);
    }

    public static void openUrlWithPreloadWebView() {
        final Activity currentActivity = UnityPlayer.currentActivity;
        Intent intent = new Intent(currentActivity, AppLuckPreLoadWebViewActivity.class);
        currentActivity.startActivity(intent);
    }

    public static void initPreloadWebView(String sk, String gaid) {
        if (AppLuckPreLoadWebViewActivity.getWebView() != null) {
            return;
        }
        UnityPlayer.currentActivity.runOnUiThread(() -> {
            final WebView webView = new WebView(UnityPlayer.currentActivity);
            AppLuckPreLoadWebViewActivity.setAppLuckActivityInterface(new AppLuckActivityInterface(UnityPlayer.currentActivity));
            webView.addJavascriptInterface(AppLuckPreLoadWebViewActivity.getAppLuckActivityInterface(), "AppLuckActivityInterface");
            WebViewClient client = new WebViewClient() {
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
                        UnityPlayer.currentActivity.startActivity(intent);
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (view.getProgress() < 100) {
                        return;
                    }
                    //目标页面加载完成后展示组件
                    if (StringUtils.contains(url, "/baltan")) {
                        try {
                            //Thread.sleep(100);
                            UnityPlayer.UnitySendMessage("AppLuckAndroidWebView", "pageFinished", "");
                        } catch (Exception e) {

                        }
                    }
                }
            };

            DownloadListener downloadListener = (url, userAgent, contentDisposition, mimetype, contentLength) -> {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                UnityPlayer.currentActivity.getApplicationContext().startActivity(intent);
            };
            webView.setWebViewClient(client);
            webView.setDownloadListener(downloadListener);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); // 允许javascript执行
            webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要，开启DOM缓存，开启LocalStorage存储
            webView.loadUrl(String.format(PLACEMENT_URL, sk, gaid));
            AppLuckPreLoadWebViewActivity.setWebView(webView);
        });
    }
}
