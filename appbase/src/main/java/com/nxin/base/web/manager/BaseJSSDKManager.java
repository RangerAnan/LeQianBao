package com.nxin.base.web.manager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.google.gson.JsonObject;
import com.nxin.base.R;
import com.nxin.base.model.base.BaseManager;
import com.nxin.base.utils.Logger;
import com.nxin.base.web.BaseWebViewActivity;
import com.nxin.base.web.constant.BaseJSFunctionConstant;
import com.nxin.base.web.constant.BaseWebConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by fcl on 18.9.20
 * desc:
 */

public class BaseJSSDKManager extends BaseManager {

    protected BaseWebViewActivity mContext;

    public HashMap<String, String> functionHash = new HashMap<>();

    public BaseJSSDKManager(BaseWebViewActivity mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("HandlerLeak")
    protected Handler jsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundleData = msg.getData();

            String cmd = bundleData.get("f").toString();
            if (!functionHash.containsKey(cmd)) {
                return;
            }
            String fun = functionHash.get(cmd);
            int r = bundleData.getInt("r", -1);
            if (r == 0) {
                //错误情况
                Logger.i(initTag() + "---jsHandler--error");
                callbackJsFun(fun, getParentJson(0, bundleData.get("error").toString(), bundleData.get("errorCode").toString()).toString());
                removeFunction(cmd);
                return;
            }

            JsonObject ParentJson = getParentJson(bundleData);
            switch (msg.what) {
                case BaseWebConstant.JS_CLOSE_WIN:
                    callbackJsFun(fun, ParentJson.toString());
                    mContext.finish();
                    break;
                default:
                    handleChildMessage(msg.what, bundleData, ParentJson, fun);
                    break;
            }
            removeFunction(cmd);
        }
    };

    /**
     * js接口
     */
    @JavascriptInterface
    public void invoke(String request) {
        Logger.i(initTag() + "--invoke:" + request);
        String m;                     //回调js方法
        String cmd = "";             //客户端请求方法

        try {
            JSONObject jsonObjParent = new JSONObject(request);
            m = jsonObjParent.getString("m");
            cmd = jsonObjParent.getString("cmd");

            functionHash.put(cmd, m);

            //1.验证
            if (!checkUserState()) {
                sendErrorHandler(BaseWebConstant.ERROR_LOGIN_USER_INFO + "", mContext.getString(R.string.error_not_login), cmd, BaseWebConstant.JS_LOGIN_ERROR);
                return;
            }

            //2.比对方法
            switch (cmd) {
                case BaseJSFunctionConstant.closeWin:
                    sendSuccessHandler(BaseJSFunctionConstant.closeWin, BaseWebConstant.JS_CLOSE_WIN);
                    break;
                default:
                    handlerJSFunction(jsonObjParent, cmd);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            sendErrorHandler("-1", mContext.getString(R.string.param_error), cmd, BaseWebConstant.JS_CALLBACK_ERROR);
        }

    }

    /**
     * 处理js方法
     */
    protected void handlerJSFunction(JSONObject jsonObjParent, String cmd) {

    }

    protected void handleChildMessage(int what, Bundle bundleData, JsonObject parentJson, String fun) {

    }

    /**
     * 用户检查
     */
    protected boolean checkUserState() {
        return true;
    }

    public void sendErrorHandler(String errorCode, String error, String funName, int what) {
        Bundle lbd = new Bundle();
        lbd.putString("errorCode", errorCode);
        lbd.putString("error", error);
        lbd.putInt("r", 0);
        lbd.putString("f", funName);
        sendHandler(what, lbd);
    }

    public void sendSuccessHandler(String funName, int what, String data) {
        Bundle lbd = new Bundle();
        lbd.putString("error", "");
        lbd.putString("errorCode", "");
        lbd.putInt("r", 1);
        lbd.putString("f", funName);
        lbd.putString("data", data);
        sendHandler(what, lbd);
    }

    public void sendSuccessHandler(String funName, int what) {
        Bundle lbd = new Bundle();
        lbd.putString("error", "");
        lbd.putString("errorCode", "");
        lbd.putInt("r", 1);
        lbd.putString("f", funName);
        sendHandler(what, lbd);
    }

    public void sendSuccessHandler(String funName, int what, Bundle lbd) {
        lbd.putString("error", "");
        lbd.putString("errorCode", "");
        lbd.putInt("r", 1);
        lbd.putString("f", funName);
        sendHandler(what, lbd);
    }

    public void sendSuccessHandler(int r, String errorCode, String error, String funName, int what, String data) {
        Bundle lbd = new Bundle();
        lbd.putString("error", error);
        lbd.putString("errorCode", errorCode);
        lbd.putInt("r", r);
        lbd.putString("f", funName);
        lbd.putString("data", data);
        sendHandler(what, lbd);
    }

    private void sendHandler(int what, Bundle data) {
        Message msg = jsHandler.obtainMessage();
        msg.what = what;
        msg.setData(data);
        jsHandler.sendMessage(msg);
    }

    protected void removeFunction(String key) {
        functionHash.remove(key);
    }

    public synchronized void callbackJsFun(String fun, String data) {
        try {
            String strData = data;
            strData = strData.replaceAll("\\\\n", "。");
            strData = strData.replaceAll("\\\\r", "。");
            String weburl = "javascript:znt.fn." + fun + "('" + strData + "')";
            mContext.reLoadUrl(weburl);
        } catch (Exception ext) {
            ext.printStackTrace();
        }
    }

    /**
     * 封装公共返回数据
     */
    public JsonObject getParentJson(int r, String error, String errorCode) {
        JsonObject ParentJson = new JsonObject();
        ParentJson.addProperty("code", r);

        //错误
        JsonObject ErrorJson = new JsonObject();
        ErrorJson.addProperty("errorCode", errorCode);
        ErrorJson.addProperty("msg", error);
        ParentJson.add("error", ErrorJson);

        return ParentJson;
    }

    /**
     * 封装公共返回数据
     */
    private JsonObject getParentJson(Bundle bundleData) {
        int r = 0;
        String error = "";
        String errorCode = "";

        if (bundleData.get("r") != null) {
            r = bundleData.getInt("r", 0);
        }
        if (bundleData.get("error") != null) {
            error = bundleData.get("error").toString();
        }
        if (bundleData.get("errorCode") != null) {
            errorCode = bundleData.get("errorCode").toString();
        }
        JsonObject ParentJson = new JsonObject();
        ParentJson.addProperty("code", r);

        //错误
        JsonObject ErrorJson = new JsonObject();
        ErrorJson.addProperty("errorCode", errorCode);
        ErrorJson.addProperty("msg", error);
        ParentJson.add("error", ErrorJson);

        return ParentJson;
    }
}
