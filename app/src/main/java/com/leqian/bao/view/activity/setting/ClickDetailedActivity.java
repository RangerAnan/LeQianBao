package com.leqian.bao.view.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.ClickDetailAdapter;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.bll.LoginBLL;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.network.resource.VidoeListResp;
import com.leqian.bao.model.network.statistics.ClickDetailResp;
import com.leqian.bao.model.network.statistics.PersonClickDetailResp;
import com.leqian.bao.view.activity.statistics.ClickedTrendActivity;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.system.ClipboardUtils;
import com.nxin.base.widget.NXToolBarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class ClickDetailedActivity extends BaseListToolBarActivity implements OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.tv_bar_right)
    TextView tv_bar_right;

    @BindView(R.id.bar_right)
    RelativeLayout bar_right;

    List<PersonClickDetailResp.DataBean.DayBean> mListData = new ArrayList<>();
    ClickDetailAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_click_detailed;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("点击明细");
        getRefreshLayout().setEnableLoadMore(false);
        bar_right.setVisibility(View.VISIBLE);
        tv_bar_right.setText("");
        tv_bar_right.setBackgroundResource(R.mipmap.click_detail_data);

        listView.setOnItemClickListener(this);
        getRefreshLayout().autoRefresh();
    }

    @Override
    public void initViewData() {
        super.initViewData();

        mAdapter = new ClickDetailAdapter(mListData, mContext);
        listView.setAdapter(mAdapter);

        requestPersonClickDetail();
    }

    private void requestPersonClickDetail() {
        String uid = ShareUtilUser.getString(ShareUtilUser.UID, "");
        StatisticsHttp.getPersonClickDetail(uid, new ModelCallBack<PersonClickDetailResp>() {
            @Override
            public void onResponse(PersonClickDetailResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                mListData = response.getData().getDay();
                mAdapter.setListData(mListData);
            }
        });
    }


    @OnClick({R.id.bar_left, R.id.bar_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.bar_right:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_DATA_1, ShareUtilUser.getString(ShareUtilUser.UID, ""));
                bundle.putString(Constants.INTENT_DATA_2, LoginBLL.getInstance().getUserInfo().getData().getName());
                Intent intent = new Intent(mContext, ClickedTrendActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
