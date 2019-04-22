package com.leqian.bao.view.fragment;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.LinkResourceAdapter;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.common.http.ResourceHttp;
import com.leqian.bao.model.resource.LinkResourceResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class MainFirstFragment extends BaseListFragment {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.bar_left)
    RelativeLayout bar_left;

    ArrayList<LinkResourceResp> mListData = new ArrayList<>();

    LinkResourceAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_first;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("裂变");
        bar_left.setVisibility(View.INVISIBLE);

        getRefreshLayout().setEnableLoadMore(false);

        LinkResourceResp model = new LinkResourceResp();
        mListData.add(model);

        mAdapter = new LinkResourceAdapter(mListData);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        super.initData();

    }



    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
    }




}
