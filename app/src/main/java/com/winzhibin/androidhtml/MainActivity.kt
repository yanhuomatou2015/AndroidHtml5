package com.winzhibin.androidhtml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONObject

/**
 * Created by wenzhibin(yanhuomatou2015) on 2020/10/30.
 * 
 */
class MainActivity : AppCompatActivity() {

    private val mWebView: WebView by lazy {
        webView
    }
    private  val mbtn:Button by lazy {
        btn
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWebView()

        //android调用H5方法
       mbtn.onClick {

           var json=JSONObject()
           json.put("alert","android调用js的showMessage()")
           mWebView.loadUrl("javascript:showMessage("+json.toString()+")")
       }

    }
    var setWebView={
        //1.开启android与h5通信的开关
        mWebView.settings.javaScriptEnabled=true
        //2.设置两个WebViewClinet
        //2.1设置默认的web浏览器
        mWebView.webViewClient=MyWebViewClient()
        //2.2设置chrome浏览器
        mWebView.webChromeClient=MyWebChromeClient()
        //设置android与h5通信的桥梁
        //"jsInterface"是JavaScriptMethods类的别名
        mWebView.addJavascriptInterface(JavaScriptMethods(this,mWebView),"jsInterface")

        //3.加载网页
        mWebView.loadUrl("http://192.168.254.159:8080/index.html")
    }
   inner private class MyWebViewClient:WebViewClient(){

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

        }
    }

    private class MyWebChromeClient:WebChromeClient(){
        //加载进度条
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }


    }

}
