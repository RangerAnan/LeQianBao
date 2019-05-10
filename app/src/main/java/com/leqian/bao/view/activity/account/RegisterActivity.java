package com.leqian.bao.view.activity.account;

import android.content.Intent;
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
import com.leqian.bao.common.base.BaseActivity;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.network.account.LoginResp;
import com.nxin.base.model.http.callback.ModelCallBack;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.et_input_psd)
    EditText et_input_psd;

    @BindView(R.id.et_zfb)
    EditText et_zfb;

    @BindView(R.id.et_realName)
    EditText et_realName;

    @BindView(R.id.iv_psd_look)
    ImageView iv_psd_look;

    @BindView(R.id.bar_left)
    RelativeLayout bar_left;

    private boolean isShowPwd = false;
    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    public void initViewData() {
        super.initViewData();

        Intent intent = getIntent();
        phone = intent.getStringExtra(Constants.INTENT_DATA_1);
        tv_phone.setText(phone);

        et_input_psd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(tv_phone.getText().toString().trim(), s.toString(),
                        et_zfb.getText().toString().trim(), et_realName.getText().toString().trim());
            }
        });

        et_zfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(tv_phone.getText().toString().trim(), et_input_psd.getText().toString().trim(),
                        s.toString(), et_realName.getText().toString().trim());
            }
        });

        et_realName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(tv_phone.getText().toString().trim(), et_input_psd.getText().toString().trim(),
                        et_zfb.getText().toString().trim(), s.toString());
            }
        });
    }


    private void setLoginButtonState(String phone, String psd, String zfbName, String realName) {
        btn_register.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(psd) && !TextUtils.isEmpty(zfbName) && !TextUtils.isEmpty(realName));
    }


    @OnClick({R.id.btn_register, R.id.iv_psd_look, R.id.bar_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String inputPhone = tv_phone.getText().toString().trim();
                String inputPsd = et_input_psd.getText().toString().trim();
                String zfbName = et_zfb.getText().toString().trim();
                String realName = et_realName.getText().toString().trim();
                if (TextUtils.isEmpty(inputPhone)) {
                    ToastUtil.showToastShort("手机号不能为空");
                    return;
                }
                if (inputPhone.length() != 11) {
                    ToastUtil.showToastShort("手机号格式不对");
                    return;
                }
                if (TextUtils.isEmpty(inputPsd)) {
                    ToastUtil.showToastShort("密码不能为空");
                    return;
                }
                if (inputPsd.length() < 6 || inputPsd.length() > 12) {
                    ToastUtil.showToastShort("密码长度在6-12位之间");
                    return;
                }
                if (TextUtils.isEmpty(zfbName)) {
                    ToastUtil.showToastShort("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(realName) || realName.length() == 1 || realName.length() > 10) {
                    ToastUtil.showToastShort("姓名填写不对");
                    return;
                }
                loginAccount(inputPhone, inputPsd, zfbName, realName);
                break;
            case R.id.iv_psd_look:
                showPwd();
                break;
            case R.id.bar_left:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void showPwd() {
        if (!isShowPwd) {
            iv_psd_look.setBackgroundResource(R.mipmap.register_password_show_ic);
            et_input_psd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            et_input_psd.setSelection(et_input_psd.getText().toString().length());
            isShowPwd = true;
        } else {
            iv_psd_look.setBackgroundResource(R.mipmap.register_password_hide_ic);
            et_input_psd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            et_input_psd.setSelection(et_input_psd.getText().toString().length());
            isShowPwd = false;
        }
    }

    private void loginAccount(String inputPhone, String inputPsd, String zfbName, String realName) {

        AccountHttp.userRegister(inputPhone, inputPsd, zfbName, realName, new ModelCallBack<LoginResp>("正在加载...") {
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
