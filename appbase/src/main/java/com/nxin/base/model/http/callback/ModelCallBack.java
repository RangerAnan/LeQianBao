package com.nxin.base.model.http.callback;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.nxin.base.R;
import com.nxin.base.model.BaseConstant;
import com.nxin.base.model.domain.BaseModel;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.utils.EncryptUtils;
import com.nxin.base.utils.JsonUtils;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.NetworkManager;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.view.dialog.LoadingDialog;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fcl on 18.10.11
 * desc:
 */

public abstract class ModelCallBack<T extends BaseModel> extends Callback<T> {

    protected LoadingDialog mProgressDialog;

    public ModelCallBack() {

    }

    public ModelCallBack(String progressDesc) {
        super(progressDesc);
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String result = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        if (EncryptUtils.isBase64Data(result)) {
            result = EncryptUtils.DeCodeBase64String(result);
        }
        return JsonUtils.parserJSONObject(result, entityClass);
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (!TextUtils.isEmpty(progressDesc)) {
            Activity currentActivity = ProHelper.getScreenHelper().currentActivity();
            if (currentActivity.isFinishing()) {
                return;
            }
            if (progressDesc.contains(BaseConstant.PROGRESS_DIALOG_UNCANCEL)) {
//                mProgressDialog = MaterialDialogUtil.showProgress(currentActivity, progressDesc.replace(BaseConstant.PROGRESS_DIALOG_UNCANCEL, ""));
//                mProgressDialog.setCancelable(false);
                mProgressDialog = new LoadingDialog(currentActivity, false, progressDesc.replace(BaseConstant.PROGRESS_DIALOG_UNCANCEL, ""));
            } else {
//                mProgressDialog = MaterialDialogUtil.showProgress(currentActivity, progressDesc);
                mProgressDialog = new LoadingDialog(currentActivity, true, progressDesc);
                mProgressDialog.show();
            }
            mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    OkHttpUtils.getInstance().cancelTag(ProHelper.getScreenHelper().currentActivity());
                }
            });
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        if (NetworkManager.getInstance().isNetworkAvailable()) {
            Toast.makeText(ProHelper.getApplication(), R.string.error_network, Toast.LENGTH_SHORT).show();
        }
        Logger.e("请求异常：" + e);
    }
}
