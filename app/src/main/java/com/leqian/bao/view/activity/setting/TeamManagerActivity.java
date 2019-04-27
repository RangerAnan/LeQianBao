package com.leqian.bao.view.activity.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.network.team.TeamInfoResp;
import com.leqian.bao.view.activity.resource.MakeCoverActivity;
import com.nxin.base.utils.system.ClipboardUtils;
import com.nxin.base.widget.NXToolBarActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class TeamManagerActivity extends BaseListToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_team)
    TextView tv_team;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_invite_code)
    TextView tv_invite_code;

    @BindView(R.id.iv_image)
    ImageView iv_image;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    TeamInfoResp model;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_manager;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("团队管理");

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonState(et_content.getText().toString().trim());
            }
        });
    }

    @Override
    public void initViewData() {
        super.initViewData();
        getRefreshLayout().autoRefresh();

        model = new TeamInfoResp();
        showTeamInfo(model);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        requestTeamInfo();
    }

    private void requestTeamInfo() {
        //TODO
    }

    private void showTeamInfo(TeamInfoResp model) {
        SpannableString teamSpan = new SpannableString(model.name + getString(R.string.user_team) + "（" + model.person + "）");
        teamSpan.setSpan(new AbsoluteSizeSpan((int) DeviceUtil.sp2px(14)), (model.name + getString(R.string.user_team)).length(),
                teamSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        teamSpan.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), (model.name + getString(R.string.user_team)).length(),
                teamSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_team.setText(teamSpan);


        SpannableString codeSpan = new SpannableString(model.inviteCode + getString(R.string.team_copy));
        codeSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_0000ff)), getString(R.string.team_copy).length(),
                codeSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_invite_code.setText(codeSpan);

        tv_name.setText(model.name);
        et_content.setText(model.des);
        et_content.setSelection(model.des.length());
        iv_image.setImageResource(R.mipmap.app_icon);
    }

    private void setButtonState(String content) {
        btn_ok.setEnabled(!TextUtils.isEmpty(content));
    }

    @OnClick({R.id.bar_left, R.id.btn_ok, R.id.tv_invite_code})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.btn_ok:
                String teamDesc = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(teamDesc)) {
                    ToastUtil.showToastShort(R.string.input_not_empty);
                    return;
                }
                //TODO
                break;
            case R.id.tv_invite_code:
                ClipboardUtils.setTextToClipboard(mContext, model.inviteCode, model.inviteCode);
                ToastUtil.showToastShort("复制成功");
                break;
            default:
                break;
        }
    }
}
