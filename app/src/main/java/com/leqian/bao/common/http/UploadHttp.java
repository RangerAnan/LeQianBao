package com.leqian.bao.common.http;

import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.bll.LoginBLL;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.callback.ModelCallBack;

import java.io.File;

/**
 * Created by fcl on 19.4.16
 * desc:
 */
public class UploadHttp extends BaseHttp {


    public static void uploadImage(File file, ModelCallBack callBack) {
        OkHttpUtils.post().addParams("token", token)
                .addParams(method, "upload")
                .addParams("uid", LoginBLL.getInstance().getUserInfo().getUid())
                .addFile("image", file.getName(), file)
                .url(commonUrl).build()
                .execute(callBack);
    }
}
