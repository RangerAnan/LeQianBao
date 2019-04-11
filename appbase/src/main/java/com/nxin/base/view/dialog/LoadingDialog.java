package com.nxin.base.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.nxin.base.R;
import com.nxin.base.model.BaseConstant;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.utils.UIUtil;

/**
 * Created by fcl on 18.10.19
 * desc:
 */

public class LoadingDialog extends BaseDialog {

    TextView tv_loading;

    private boolean outSideCancel;
    private String title = "加载中...";

    private int dialogWidth = 230;

    public LoadingDialog(Context context, boolean outSideCancel, String title) {
        super(context, R.style.dialog_common_transparent);
        view = inflater.inflate(R.layout.dialog_loading, null);
        this.outSideCancel = outSideCancel;
        this.title = title;
    }

    public LoadingDialog(Context context, boolean outSideCancel) {
        super(context, R.style.dialog_common_transparent);
        view = inflater.inflate(R.layout.dialog_loading, null);
        this.outSideCancel = outSideCancel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(outSideCancel);
        setCancelable(outSideCancel);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;

        DisplayMetrics dm = new DisplayMetrics();
        ProHelper.getScreenHelper().currentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        int maxWidth = (int) (dm.widthPixels * BaseConstant.DIALOG_WIDTH);
        if (title.length() >= 14) {
            dialogWidth = 260;
        }
        p.width = UIUtil.dp2px(maxWidth > dialogWidth ? dialogWidth : maxWidth);
        getWindow().setAttributes(p);

        tv_loading = findViewById(R.id.tv_loading);
        tv_loading.setText(title);

    }


}
