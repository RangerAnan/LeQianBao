package com.leqian.bao.model.bll;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.leqian.bao.common.sp.ShareUtilMain;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.model.account.LoginResp;
import com.leqian.bao.model.account.UserInfoResp;
import com.leqian.bao.view.activity.account.LoginActivity;
import com.nxin.base.model.base.BaseManager;
import com.nxin.base.utils.JsonUtils;
import com.nxin.base.utils.Logger;

/**
 * Created by fcl on 19.4.15
 * desc:
 */
public class LoginBLL extends BaseManager {

    private static LoginBLL instentce;

    public static LoginBLL getInstance() {
        if (instentce == null) {
            instentce = new LoginBLL();
        }
        return instentce;
    }


    public UserInfoResp getUserInfo() {
        String string = ShareUtilUser.getString(ShareUtilUser.USER_INFO, "");
        if (TextUtils.isEmpty(string)) {
            Logger.e(initTag() + "--getUserInfo-- userInfo is empty");
            return new UserInfoResp();
        }
        return JsonUtils.parserJSONObject(string, UserInfoResp.class);
    }


    public void exitAccount(Context context) {
        ShareUtilMain.setBoolean(ShareUtilMain.LOGIN_STATE, false);
        ShareUtilUser.clear();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
