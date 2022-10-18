using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public delegate void OnPageFinished();
public delegate void OnActivityJoin(bool isFirst);
public delegate void OnAdvertClick();
public class AppLuckAndroidWebView : MonoBehaviour
{
    AndroidJavaObject aObject;

    private void Awake()
    {
        aObject = new AndroidJavaObject("com.appluck.webview_library.AppLuckWebView");
       
    }
    // Start is called before the first frame update
    void Start()
    {
       
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void show(string sk, string gaid)
    {
        if (gaid == null) {
            gaid = "";
        }
        aObject.CallStatic("openUrl", sk, gaid);
    }

    public void showPreLoad()
    {

        aObject.CallStatic("openUrlWithPreloadWebView");
    }

    public void initPreLoadWebView(string sk ,string gaid) {
        if (gaid == null)
        {
            gaid = "";
        }
        aObject.CallStatic("initPreloadWebView", sk, gaid);
    }

    public OnPageFinished onPageFinished;

    public OnActivityJoin onActivityJoin;

    public OnAdvertClick onAdvertClick;

    public void pageFinished() {
        this.onPageFinished();
    }

    public void activityJoin(string isFirst) {

        this.onActivityJoin(bool.Parse(isFirst));
    }

    public void advertClick() {
        this.onAdvertClick();
    }
}
