package com.leqian.bao.model;

import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.builder.GetBuilder;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.EncryptUtils;
import com.nxin.base.utils.JsonUtils;

import okhttp3.MediaType;

/**
 * Created by fcl on 19.4.11
 * desc:
 */
public class BaseHttp {

    public static final String hostUrl = "http://api.ahqibang.com";

    public static final String commonUrl = hostUrl + "/public/api.php";

    private static final String token = "a84d81aa54523bcab9d88c32d9e751ac";

    private static final String method = "method";


    /**
     * post请求
     */
    public static <T> void executePostHttp(T content, Object tag, ModelCallBack callback) {

        String param = content.getClass() == String.class ? (String) content : JsonUtils.object2Json(content);

        OkHttpUtils.postString()
                .url(hostUrl)
                .content(EncryptUtils.EncodeBase64String(param))
                .tag(tag)
//                .addHeader("User-Agent", StringUtil.getAppVersion())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callback);
    }

    /**
     * get请求
     */
    public static GetBuilder executeGetHttp(String method) {
        return OkHttpUtils.get().url(commonUrl)
                .addParams("token", token)
                .addParams("method", method);
    }
}
