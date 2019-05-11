package com.leqian.bao.view.dialog.updat;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leqian.bao.R;
import com.nxin.base.view.dialog.BaseDialog;


/**
 * fix by wzy on 2017/6/8
 */
public class VersionUpgradeDialog extends BaseDialog {

    private ImageView upgrade_close;
    private Button btn_cancel, btn_upgrade;

    public VersionUpgradeDialog(Context context, String contentStr, String cancelStr) {
        super(context, R.style.dialog_common_transparent);
        view = inflater.inflate(R.layout.dialog_version_upgrade, null);
        initViews(contentStr, cancelStr);
        setListener();
    }

    private void initViews(String contentStr, String cancelStr) {
        TextView upgrade_content = view.findViewById(R.id.dialog_version_upgrade_content_txt);
        upgrade_close = view.findViewById(R.id.iv_version_upgrade_close);
        btn_cancel = view.findViewById(R.id.dialog_version_upgrade_btn_cancel);
        btn_cancel.setText(cancelStr);
        btn_upgrade = view.findViewById(R.id.dialog_version_upgrade_btn_upgrade);
        if (TextUtils.isEmpty(contentStr)) {
            upgrade_content.setText("");
        } else {
            upgrade_content.setText(contentStr);
        }
    }

    private void setListener() {
        upgrade_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCancelButton(null);
        setUpgradeButton(null);
    }

    public VersionUpgradeDialog setCancelButton(final View.OnClickListener onClickListener) {
        if (btn_cancel == null) return this;
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                dismiss();
            }
        });
        return this;
    }

    public VersionUpgradeDialog setUpgradeButton(final View.OnClickListener onClickListener) {
        if (btn_upgrade == null) return this;
        btn_upgrade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                dismiss();
            }
        });
        return this;
    }

    public VersionUpgradeDialog setForce(boolean isforce) {
        if (isforce) {
            upgrade_close.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        } else {
            upgrade_close.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
        }
        return this;
    }
    
}