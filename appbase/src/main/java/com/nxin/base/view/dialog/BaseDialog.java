package com.nxin.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.nxin.base.R;
import com.nxin.base.model.BaseConstant;
import com.nxin.base.utils.ProHelper;


public class BaseDialog extends Dialog {

    protected Context mContext;
    protected LayoutInflater inflater;
    protected View view;
    protected OnImageViewClick shareClick;

    public BaseDialog(Context context) {
        super(context, R.style.dialog_common);
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public BaseDialog(Context context, int dialog_common) {
        super(context, dialog_common);
        mContext = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (view == null) {
            throw new RuntimeException("BaseDialog子类不能为null");
        }
        setContentView(view);
        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        ProHelper.getScreenHelper().currentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        p.width = (int) (dm.widthPixels * BaseConstant.DIALOG_WIDTH);
        getWindow().setAttributes(p);
    }

    protected View.OnClickListener dismissOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    public View getDialogView() {
        return view;
    }

    public interface OnImageViewClick {
        void onclicked(String s);
    }

    public void setImageViewClickListener(OnImageViewClick shareClick) {
        this.shareClick = shareClick;
    }
}
