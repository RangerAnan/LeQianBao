package com.leqian.bao.view.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.account.LoginResp;
import com.leqian.bao.view.activity.account.AccountHttp;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.model.http.utils.L;
import com.nxin.base.utils.Logger;
import com.nxin.base.widget.NXActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class LoginActivity extends NXActivity {


    @BindView(R.id.tv_register)
    TextView tv_register;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.tv_forget_psd)
    TextView tv_forget_psd;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_input_psd)
    EditText et_input_psd;

    @BindView(R.id.iv_psd_look)
    ImageView iv_psd_look;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViewData() {
        super.initViewData();
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(s.toString(), et_input_psd.getText().toString().trim());
            }
        });

        et_input_psd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(et_phone.getText().toString().trim(), s.toString());
            }
        });
    }


    private void setLoginButtonState(String phone, String psd) {
        btn_login.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(psd));
    }


    @OnClick({R.id.btn_login, R.id.tv_forget_psd, R.id.tv_register})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String inputPhone = et_phone.getText().toString().trim();
                String inputPsd = et_input_psd.getText().toString().trim();
                if (TextUtils.isEmpty(inputPhone)) {
                    ToastUtil.showToastShort("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(inputPsd)) {
                    ToastUtil.showToastShort("密码不能为空");
                    return;
                }
                loginAccount(inputPhone, inputPsd);
                break;
            case R.id.tv_forget_psd:
                Logger.i(initTag() + "--onViewClick");
                ToastUtil.showToastShort("忘记密码");
                break;
            case R.id.tv_register:
                ToastUtil.showToastShort("注册");
                break;
            default:
                break;
        }
    }

    private void loginAccount(String inputPhone, String inputPsd) {
        OkHttpUtils.get().url(AccountHttp.testJson)
                .addParams("", "")
                .build().execute(new ModelCallBack<LoginResp>() {
            @Override
            public void onResponse(LoginResp response, int id) {

            }
        });
    }

}
