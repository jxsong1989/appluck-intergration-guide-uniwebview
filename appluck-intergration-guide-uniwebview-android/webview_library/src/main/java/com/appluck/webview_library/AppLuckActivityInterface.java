package com.appluck.webview_library;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class AppLuckActivityInterface {
    Context mContext;

    public static AppLuckActivityInterface instance;

    private AppLuckActivityInterface(Context mContext) {
        this.mContext = mContext;
    }

    public static AppLuckActivityInterface getInstance(Context mContext) {
        if (instance == null) {
            instance = new AppLuckActivityInterface(mContext);
        }
        return instance;
    }

    /**
     * 活动加载成功
     */
    @JavascriptInterface
    public void activityLoadSuccess() {
        Toast.makeText(mContext, "activity load success", Toast.LENGTH_SHORT).show();
    }

    /**
     * 活动参与
     *
     * @param isFirst 是否首次,true:首次;false:非首次
     */
    @JavascriptInterface
    public void activityJoin(boolean isFirst) {
        Toast.makeText(mContext, "activity join, isFirst: " + isFirst, Toast.LENGTH_SHORT).show();
    }

    /**
     * 广告点击
     */
    @JavascriptInterface
    public void advertShow() {
        Toast.makeText(mContext, "advert show", Toast.LENGTH_SHORT).show();
    }

    /**
     * 广告关闭
     */
    @JavascriptInterface
    public void advertClose() {
        Toast.makeText(mContext, "advert close", Toast.LENGTH_SHORT).show();
    }

    /**
     * 广告点击
     */
    @JavascriptInterface
    public void advertClick() {
        Toast.makeText(mContext, "advert click", Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭
     */
    @JavascriptInterface
    public void killSelf() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
        }
    }
}
