package com.leqian.bao.view.activity.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.ClickDetailAdapter;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.network.resource.VidoeListResp;
import com.leqian.bao.model.network.statistics.ClickDetailResp;
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

    List<ClickDetailResp.DataBean> mListData = new ArrayList<>();
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

        listView.setOnItemClickListener(this);
        getRefreshLayout().autoRefresh();
    }

    @Override
    public void initViewData() {
        super.initViewData();

        mListData = getData();
        mAdapter = new ClickDetailAdapter(mListData, mContext);
        listView.setAdapter(mAdapter);
    }

    private List<ClickDetailResp.DataBean> getData() {
        for (int i = 0; i < 10; i++) {
            ClickDetailResp.DataBean dataBean = new ClickDetailResp.DataBean();
            dataBean.setDate("20190504-" + i);
            dataBean.setCount(i);
            mListData.add(dataBean);
        }
        return mListData;
    }

    @OnClick({R.id.bar_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
