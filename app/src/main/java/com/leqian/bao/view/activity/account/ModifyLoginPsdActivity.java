package com.leqian.bao.view.activity.account;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.account.LoginResp;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.widget.NXActivity;
import com.nxin.base.widget.NXToolBarActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class ModifyLoginPsdActivity extends NXToolBarActivity {


    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_input_psd)
    EditText et_input_psd;

    @BindView(R.id.et_sms_code)
    EditText et_sms_code;

    @BindView(R.id.et_input_psd_again)
    EditText et_input_psd_again;

    @BindView(R.id.iv_psd_look)
    ImageView iv_psd_look;

    @BindView(R.id.iv_psd_look_again)
    ImageView iv_psd_look_again;

    @BindView(R.id.bar_left)
    RelativeLayout bar_left;

    @BindView(R.id.tv_title)
    TextView tv_title;

    private boolean isShowPwd = false;
    private boolean isShowPwdAgain = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_login_psd;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public void initViewData() {
        super.initViewData();
        tv_title.setText("修改密码");

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(s.toString(), et_input_psd.getText().toString().trim(),
                        et_sms_code.getText().toString().trim(), et_input_psd_again.getText().toString().trim());
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
                setLoginButtonState(et_phone.getText().toString().trim(), s.toString(),
                        et_sms_code.getText().toString().trim(), et_input_psd_again.getText().toString().trim());
            }
        });

        et_sms_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(et_phone.getText().toString().trim(), et_input_psd.getText().toString().trim(),
                        s.toString(), et_input_psd_again.getText().toString().trim());
            }
        });

        et_input_psd_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(et_phone.getText().toString().trim(), et_input_psd.getText().toString().trim(),
                        et_sms_code.getText().toString().trim(), s.toString());
            }
        });
    }


    private void setLoginButtonState(String phone, String psd, String zfbName, String realName) {
        btn_ok.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(psd) && !TextUtils.isEmpty(zfbName) && !TextUtils.isEmpty(realName));
    }


    @OnClick({R.id.btn_ok, R.id.iv_psd_look, R.id.bar_left, R.id.iv_psd_look_again})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String inputPhone = et_phone.getText().toString().trim();
                String inputPsd = et_input_psd.getText().toString().trim();
                String smsCode = et_sms_code.getText().toString().trim();
                String inputPsdAgain = et_input_psd_again.getText().toString().trim();
                if (TextUtils.isEmpty(inputPhone)) {
                    ToastUtil.showToastShort("手机号不能为空");
                    return;
                }
                if (inputPhone.length() != 11) {
                    ToastUtil.showToastShort("手机号格式不对");
                    return;
                }
                if (TextUtils.isEmpty(inputPsd) || TextUtils.isEmpty(inputPsdAgain)) {
                    ToastUtil.showToastShort("密码不能为空");
                    return;
                }
                if (inputPsd.length() < 6 || inputPsd.length() > 12 || inputPsdAgain.length() < 6 || inputPsdAgain.length() > 12) {
                    ToastUtil.showToastShort("密码长度在6-12位之间");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    ToastUtil.showToastShort("验证码不能为空");
                    return;
                }

                loginAccount(inputPhone, inputPsd, smsCode, inputPsdAgain);
                break;
            case R.id.iv_psd_look:
                showPwd(et_input_psd, iv_psd_look, isShowPwd);
                isShowPwd = !isShowPwd;
                break;
            case R.id.iv_psd_look_again:
                showPwd(et_input_psd_again, iv_psd_look_again, isShowPwdAgain);
                isShowPwdAgain = !isShowPwdAgain;
                break;
            case R.id.bar_left:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void showPwd(EditText et, ImageView iv, boolean isShow) {
        iv.setBackgroundResource(isShow ? R.mipmap.register_password_hide_ic : R.mipmap.register_password_show_ic);
        et.setInputType(isShow ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        et.setSelection(et.getText().toString().length());
    }

    private void loginAccount(String inputPhone, String inputPsd, String zfbName, String realName) {
        ToastUtil.showToastShort("完成");
      /*  AccountHttp.userRegister(inputPhone, inputPsd, zfbName, realName, new ModelCallBack<LoginResp>() {
            @Override
            public void onResponse(LoginResp response, int id) {
                ToastUtil.showToastShort(response.getMsg());
                if (response.getCode() == 1) {
                    finish();
                }
            }
        });*/
    }

}
