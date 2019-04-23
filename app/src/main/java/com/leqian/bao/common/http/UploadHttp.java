package com.leqian.bao.common.http;

import com.leqian.bao.common.sp.ShareUtilUser;
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
                .addParams("uid", ShareUtilUser.getString(ShareUtilUser.UID, ""))
                .addFile("image", file.getName(), file)
                .url(commonUrl).build()
                .execute(callBack);
    }

    public static void uploadCover(String title, String desc, File file, ModelCallBack callBack) {
        OkHttpUtils.post().addParams("token", token)
                .addParams(method, "uploadCover")
                .addParams("uid", ShareUtilUser.getString(ShareUtilUser.UID, ""))
                .addParams("title", title)
                .addParams("desc", desc)
                .addFile("pic", file.getName(), file)
                .url(commonUrl).build()
                .execute(callBack);
    }
}
