package com.leqian.bao.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.ui.CommonUIModel;
import com.leqian.bao.view.activity.account.ModifyLoginPsdActivity;
import com.leqian.bao.view.imageview.CircleImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.12
 * desc:我的模块
 */
public class MainFourFragment extends ViewpagerFragment {

    @BindView(R.id.me_user_photo)
    CircleImageView me_user_photo;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_zfb)
    TextView tv_zfb;

    @BindView(R.id.tv_today_charge)
    TextView tv_today_charge;

    @BindView(R.id.tv_yesterday_charge)
    TextView tv_yesterday_charge;

    @BindView(R.id.tv_month_charge)
    TextView tv_month_charge;

    @BindView(R.id.content)
    LinearLayout content;


    ArrayList<CommonUIModel> uiModels = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_four;
    }

    @Override
    public void initView() {
        super.initView();
        //set user info

        //set content
        content.removeAllViews();
        uiModels.clear();
        String[] stringArray = getResources().getStringArray(R.array.mine_ui_item);
        for (int i = 0; i < stringArray.length; i++) {
            CommonUIModel commonUIModel = new CommonUIModel();
            commonUIModel.name = stringArray[i];
            commonUIModel.code = i;
            uiModels.add(commonUIModel);
        }

        for (int i = 0; i < uiModels.size(); i++) {
            View inflate = View.inflate(mContext, R.layout.item_fragment_me_text, null);
            LinearLayout layout1 = inflate.findViewById(R.id.layout1);
            TextView tvTitle = inflate.findViewById(R.id.tv_title);

            CommonUIModel commonUIModel = uiModels.get(i);
            tvTitle.setText(commonUIModel.name);
            layout1.setOnClickListener(new ItemClickListener(commonUIModel));
            content.addView(inflate);
        }

    }

    @OnClick({R.id.me_user_photo, R.id.tv_zfb})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.me_user_photo:
                ToastUtil.showToastShort("头像");
                break;
            case R.id.tv_zfb:
                ToastUtil.showToastShort("支付宝");
                break;
            default:
                break;
        }
    }

    private class ItemClickListener implements View.OnClickListener {
        CommonUIModel commonUIModel;

        public ItemClickListener(CommonUIModel commonUIModel) {
            this.commonUIModel = commonUIModel;
        }

        @Override
        public void onClick(View v) {
            switch (commonUIModel.code) {
                case 0:
                    ToastUtil.showToastShort("团队管理");
                    break;
                case 1:
                    ToastUtil.showToastShort("点击明细");
                    break;
                case 2:
                    startActivity(new Intent(mContext, ModifyLoginPsdActivity.class));
                    break;
                case 3:
                    ToastUtil.showToastShort("联系客服");
                    break;
                case 4:
                    ToastUtil.showToastShort("关于我们");
                    break;
                case 5:
                    ToastUtil.showToastShort("退出登录");
                    break;
                default:
                    break;
            }
        }
    }
}
