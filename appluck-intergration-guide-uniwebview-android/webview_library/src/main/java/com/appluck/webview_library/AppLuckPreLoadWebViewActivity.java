package com.appluck.webview_library;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * 支持预加载的webview
 */
public class AppLuckPreLoadWebViewActivity extends Activity implements AppLuckBaseActivity {

    private static WebView webView;

    private static AppLuckActivityInterface appLuckActivityInterface;

    public Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(webView);
            }
        }
        //setContentView(webView);
        appLuckActivityInterface.mContext = this;
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        setContentView(constraintLayout);
        ConstraintLayout.LayoutParams webViewLP = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        webViewLP.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        webViewLP.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        webView.setLayoutParams(webViewLP);
        constraintLayout.addView(webView);
        closeBtn = new Button(this);
        closeBtn.setBackgroundResource(R.drawable.close);
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
        int width = this.getResources().getDimensionPixelSize(R.dimen.close_btn_width);
        ConstraintLayout.LayoutParams closeBtnLP = new ConstraintLayout.LayoutParams(width, width);
        closeBtnLP.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        closeBtnLP.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        int margin = this.getResources().getDimensionPixelSize(R.dimen.close_btn_margin);
        closeBtnLP.topMargin = margin;
        closeBtnLP.setMarginStart(margin);
        closeBtn.setLayoutParams(closeBtnLP);
        closeBtn.setId(R.id.pre_load_close_btn);
        constraintLayout.addView(closeBtn);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        //super.onBackPressed();
    }

    @Override
    public void finish() {
        webView.reload();
        super.finish();
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

    public static void setAppLuckActivityInterface(AppLuckActivityInterface appLuckActivityInterface) {
        AppLuckPreLoadWebViewActivity.appLuckActivityInterface = appLuckActivityInterface;
    }

    public static AppLuckActivityInterface getAppLuckActivityInterface() {
        return appLuckActivityInterface;
    }

    public static void setWebView(WebView webView) {
        AppLuckPreLoadWebViewActivity.webView = webView;
    }

    public static WebView getWebView() {
        return webView;
    }
}