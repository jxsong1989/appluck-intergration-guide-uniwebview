using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MainCtrl : MonoBehaviour
{
    Button webView_btn;
    Button preload_webView_btn;
    AppLuckAndroidWebView appLuckAndroidWebView;

    //���λid�������ȡ
    private string placementId = "q842c2e079a1b32c8";
    //gaid��ͨ��google sdk��ȡ
    private string gaid = "";
    // Start is called before the first frame update
    void Start()
    {
        //��ͨwebview������ť
        webView_btn = GameObject.Find("webView_btn").GetComponent<Button>();
        //Ԥ����webview������ť
        preload_webView_btn = GameObject.Find("preload_webView_btn").GetComponent<Button>();
        //Ԥ����webview������ťĬ������
        preload_webView_btn.gameObject.SetActive(false);
        //����AppLuckAndroidWebView GameObject
        var appLuckAndroidWebViewGO = new GameObject("AppLuckAndroidWebView");
        //����AppLuckAndroidWebView
        appLuckAndroidWebView = appLuckAndroidWebViewGO.AddComponent<AppLuckAndroidWebView>();

        //Ԥ������ҳ�ɹ��ص�
        appLuckAndroidWebView.onPageFinished += () =>{
            //Ԥ���سɹ�����ʾ��ť
            preload_webView_btn.gameObject.SetActive(true);
        };

        //��ͨwebview������ť����¼���
        webView_btn.onClick.AddListener(() => {
            //����webview�����ػ���봫��placementId��gaid
            appLuckAndroidWebView.show(placementId, gaid);
        });

        //Ԥ����webview������ť����¼���
        preload_webView_btn.onClick.AddListener(()=> {
            //����Ԥ����webview
            appLuckAndroidWebView.showPreLoad();
        });
        //Ԥ����webview��ʼ��
        appLuckAndroidWebView.initPreLoadWebView(placementId, gaid);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
