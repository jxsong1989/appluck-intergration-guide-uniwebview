package com.appluck.webview_library;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.unity3d.player.UnityPlayer;

public class AppLuckActivityInterface {
    Context mContext;

    public AppLuckActivityInterface(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 活动加载成功
     */
    @JavascriptInterface
    public void activityLoadSuccess() {

    }

    /**
     * 活动参与
     *
     * @param isFirst 是否首次,true:首次;false:非首次
     */
    @JavascriptInterface
    public void activityJoin(boolean isFirst) {
        UnityPlayer.UnitySendMessage("AppLuckAndroidWebView", "activityJoin", isFirst + "");
    }


    /**
     * 广告点击
     */
    @JavascriptInterface
    public void advertClick() {
        UnityPlayer.UnitySendMessage("AppLuckAndroidWebView", "advertClick", "");
    }

    /**
     * 广告点击
     */
    @JavascriptInterface
    public void closeBtnHide() {
        if (mContext instanceof AppLuckBaseActivity) {
            ((AppLuckBaseActivity) mContext).closeHide();
        }
    }

    /**
     * 广告关闭
     */
    @JavascriptInterface
    public void closeBtnShow() {
        if (mContext instanceof AppLuckBaseActivity) {
            ((AppLuckBaseActivity) mContext).closeShow();
        }
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
