package com.leqian.bao.view.activity.account;

import android.os.CountDownTimer;
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
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.network.account.LoginResp;
import com.leqian.bao.model.network.base.BaseModelResp;
import com.nxin.base.model.http.callback.ModelCallBack;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class ModifyLoginPsdActivity extends BaseToolBarActivity {


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

    @BindView(R.id.btn_send_msg)
    Button btn_send_msg;

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

                btn_send_msg.setEnabled(!TextUtils.isEmpty(s));
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


    @OnClick({R.id.btn_ok, R.id.iv_psd_look, R.id.bar_left, R.id.iv_psd_look_again, R.id.btn_send_msg})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
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
                if (!inputPsd.equals(inputPsdAgain)) {
                    ToastUtil.showToastShort("两次密码输入不一致");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    ToastUtil.showToastShort("验证码不能为空");
                    return;
                }

                changePsd(inputPhone, inputPsd, smsCode);
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
            case R.id.btn_send_msg:
                String trim = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showToastShort("手机号不能为空");
                    return;
                }
                if (trim.length() != 11) {
                    ToastUtil.showToastShort("手机号格式不对");
                    return;
                }
                requestSmsCode(trim);
                break;
            default:
                break;
        }
    }

    private void requestSmsCode(String trim) {
        AccountHttp.getMsg(trim, "changePass", new ModelCallBack<BaseModelResp>("获取验证码...") {
            @Override
            public void onResponse(BaseModelResp response, int id) {
                ToastUtil.showToastShort(response.getMsg());
                if (response.getCode() != 1) {
                    return;
                }
                btn_send_msg.setEnabled(false);
                startTimer();
            }
        });
    }

    private void startTimer() {
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_send_msg.setText(millisUntilFinished / 1000 + " 秒");
            }

            @Override
            public void onFinish() {
                btn_send_msg.setText("获取验证码");
                btn_send_msg.setEnabled(!TextUtils.isEmpty(et_phone.getText().toString().trim()));
            }
        }.start();

    }

    private void showPwd(EditText et, ImageView iv, boolean isShow) {
        iv.setBackgroundResource(isShow ? R.mipmap.register_password_hide_ic : R.mipmap.register_password_show_ic);
        et.setInputType(isShow ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        et.setSelection(et.getText().toString().length());
    }

    private void changePsd(String inputPhone, String inputPsd, String code) {

        AccountHttp.changePass(inputPhone, inputPsd, code, new ModelCallBack<LoginResp>("正在加载...") {
            @Override
            public void onResponse(LoginResp response, int id) {
                ToastUtil.showToastShort(response.getMsg());
                if (response.getCode() == 1) {
                    finish();
                }
            }
        });
    }

}
