package com.winzhibin.androidhtml

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.net.URL

/**
 * Created by wenzhibin(yanhuomatou2015) on 2020/10/30.
 */

/**
 * android与h5通信的桥梁类
 */
class JavaScriptMethods {

    private  var mContext:Context?=null
    private  var mWebView:WebView?=null
    constructor(context:Context?,webView: WebView?){
        this.mContext=context
        this.mWebView=webView
    }
     //必须加上注解，否则android 4.2之后，不允许h5调用该方法
     @JavascriptInterface
     fun showToast(jsonData:String){
        //Toast.makeText(mContext,jsonData,Toast.LENGTH_LONG).show()
        // mContext?.toast(jsonData)
         mContext?.let { it.toast(jsonData) } //it指的是：当前上下文对象
     }
    @JavascriptInterface
    fun getrequestData(jsonData:String){
        var jsJson=JSONObject(jsonData)
        var callback=jsJson.optString("callback")
        doAsync {
            //模拟请求接口数据
            var url= URL("http://192.168.254.159:8080/yanhuomatou2015.json")
            //将数据转成字符串
            val result=url.readText()
            Log.e("wenzhibin",result)
            //回传数据给h5,调用js方法必须放在主线程中
            mContext?.let {
                it.runOnUiThread {
                    mWebView?.let { it.loadUrl("javascript:"+callback +
                            "("+result+")") }
                }
            }
        }
    }
}