package com.leqian.bao.view.activity.account;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseActivity;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.network.base.BaseModelReq;
import com.leqian.bao.model.network.base.BaseModelResp;
import com.nxin.base.model.http.callback.ModelCallBack;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Anan on 2019/5/9.
 */
public class SendSMSActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_sms_code)
    EditText et_sms_code;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.btn_send_msg)
    Button btn_send_msg;


    @Override
    public int getLayoutId() {
        return R.layout.activity_send_sms;
    }

    @Override
    public void initView() {
        super.initView();

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setLoginButtonState(s.toString(), et_sms_code.getText().toString().trim());
                btn_send_msg.setEnabled(!TextUtils.isEmpty(s.toString()));
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
                setLoginButtonState(s.toString(), et_phone.getText().toString().trim());
            }
        });
    }

    private void setLoginButtonState(String phone, String code) {
        btn_ok.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code));
    }


    @OnClick({R.id.bar_left, R.id.btn_ok, R.id.btn_send_msg})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.btn_ok:
                ToastUtil.showToastShort("111");
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
        AccountHttp.getMsg(trim, "reg", new ModelCallBack<BaseModelResp>("获取验证码...") {
            @Override
            public void onResponse(BaseModelResp response, int id) {
                ToastUtil.showToastShort(response.getMsg());
                if (response.getCode() != 1) {
                    return;
                }
                //TODO 倒计时
                btn_send_msg.setEnabled(false);
            }
        });
    }
}
