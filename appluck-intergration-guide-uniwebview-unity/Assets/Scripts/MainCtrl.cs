using UnityEngine;
using UnityEngine.UI;

public class MainCtrl : MonoBehaviour
{
    Button webView_btn;
    Button preload_webView_btn;
    Button act_join_btn;
    Button ad_click_btn;
    AppLuckAndroidWebView appLuckAndroidWebView;

    //广告位id，商务获取
    private string placementId = "q842c2e079a1b32c8";
    //gaid，通过google sdk获取
    private string gaid = "";
    // Start is called before the first frame update
    void Start()
    {
        //普通webview触发按钮
        webView_btn = GameObject.Find("webView_btn").GetComponent<Button>();
        //预加载webview触发按钮
        preload_webView_btn = GameObject.Find("preload_webView_btn").GetComponent<Button>();
        //预加载webview触发按钮默认隐藏
        preload_webView_btn.gameObject.SetActive(false);

        act_join_btn = GameObject.Find("act_join_btn").GetComponent<Button>();
        act_join_btn.gameObject.SetActive(false);
        ad_click_btn = GameObject.Find("ad_click_btn").GetComponent<Button>();
        ad_click_btn.gameObject.SetActive(false);
        //创建AppLuckAndroidWebView GameObject
        var appLuckAndroidWebViewGO = new GameObject("AppLuckAndroidWebView");
        //挂载AppLuckAndroidWebView
        appLuckAndroidWebView = appLuckAndroidWebViewGO.AddComponent<AppLuckAndroidWebView>();

        //预加载网页成功回调
        appLuckAndroidWebView.onPageFinished += () =>{
            //预加载成功后显示按钮
            preload_webView_btn.gameObject.SetActive(true);
        };

        //TODO 活动参与回调,isFirst:是否当日首次参与
        /*appLuckAndroidWebView.onActivityJoin += (isFirst) =>
        {
            act_join_btn.gameObject.SetActive(true); 
        };*/

        //TODO 广告点击回调
       /* appLuckAndroidWebView.onAdvertClick += () =>
        {
            ad_click_btn.gameObject.SetActive(true);
        };*/

        //普通webview触发按钮点击事件绑定
        webView_btn.onClick.AddListener(() => {
            //唤起webview并加载活动，请传入placementId和gaid
            appLuckAndroidWebView.show(placementId, gaid);
        });

        //预加载webview触发按钮点击事件绑定
        preload_webView_btn.onClick.AddListener(()=> {
            //唤起预加载webview
            appLuckAndroidWebView.showPreLoad();
        });
        //预加载webview初始化
        appLuckAndroidWebView.initPreLoadWebView(placementId, gaid);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
