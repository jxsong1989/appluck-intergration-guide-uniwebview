using System.Collections;
using System.Collections.Generic;
using UnityEngine;

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

    public delegate void OnPageFinished();

    public OnPageFinished onPageFinished;

    public void pageFinished() {
        this.onPageFinished();
    }
}
