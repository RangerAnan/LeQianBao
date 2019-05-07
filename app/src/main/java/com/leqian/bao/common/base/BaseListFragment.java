package com.leqian.bao.common.base;

import android.support.annotation.NonNull;
import android.widget.ListView;

import com.leqian.bao.R;
import com.nxin.base.widget.NXFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class BaseListFragment extends NXFragment implements OnRefreshListener, OnRefreshLoadMoreListener {


    @BindView(R.id.refreshView)
    protected SmartRefreshLayout refreshView;

    @Override
    public void initView() {
        super.initView();
        setRefreshView();
    }

    private void setRefreshView() {
        if (refreshView == null) {
            return;
        }
        refreshView.setOnRefreshListener(this);
        refreshView.setOnLoadMoreListener(this);

        refreshView.setDisableContentWhenRefresh(true);
        refreshView.setDisableContentWhenLoading(true);
        refreshView.setEnableLoadMoreWhenContentNotFull(true);


    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getRefreshLayout().finishLoadMore(300/*,false*/);//传入false表示刷新失败
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getRefreshLayout().finishRefresh(300/*,false*/);//传入false表示加载失败
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshView;
    }

    public void setRefreshEnable(boolean refresh, boolean loadMore) {
        getRefreshLayout().setEnableRefresh(refresh);
        getRefreshLayout().setEnableRefresh(loadMore);
    }
}
