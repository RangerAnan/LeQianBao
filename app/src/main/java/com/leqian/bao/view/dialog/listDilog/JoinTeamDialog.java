package com.leqian.bao.view.dialog.listDilog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.leqian.bao.R;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.BaseModelResp;
import com.leqian.bao.model.account.LoginResp;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.view.dialog.BaseDialog;

import java.util.List;


/**
 * 邀请框
 */
public class JoinTeamDialog extends BaseDialog implements View.OnClickListener {

    private IJoinTeamListener listener;

    private EditText et_invite_code;
    private Button btn_ok;

    private LoginResp loginResp;

    public JoinTeamDialog(Context context, LoginResp loginResp) {
        super(context, R.style.PhotoSelectDialog);
        this.loginResp = loginResp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_join_team, null);
        et_invite_code = view.findViewById(R.id.et_invite_code);
        btn_ok = view.findViewById(R.id.btn_ok);

        setContentView(view);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = DeviceUtil.getScreenWidth();
        getWindow().setAttributes(p);

        getWindow().getAttributes().gravity = Gravity.CENTER;

        et_invite_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_ok.setEnabled(!TextUtils.isEmpty(et_invite_code.getText().toString().trim()));
            }
        });

        btn_ok.setOnClickListener(this);
    }

    public void setOnJoinTeamListener(IJoinTeamListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        String trim = et_invite_code.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            ToastUtil.showToastShort("邀请码不能为空");
            return;
        }
        joinTeam(loginResp, trim);
    }

    public interface IJoinTeamListener {
        void onJoinTeamListener(int position);
    }

    private void joinTeam(LoginResp userInfo, String trim) {
        //a1bdkc
        AccountHttp.joinTeam(trim, userInfo, new ModelCallBack<LoginResp>() {
            @Override
            public void onResponse(LoginResp response, int id) {
                if (response.getCode() == 0) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                ShareUtilUser.setString(ShareUtilUser.TEAMID, response.getUid());
                ToastUtil.showToastShort(response.getMsg());
                dismiss();
            }
        });
    }
}
