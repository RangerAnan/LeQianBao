package com.leqian.bao.view.activity.resource;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.nxin.base.widget.NXToolBarActivity;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class VideoListActivity extends BaseListToolBarActivity {

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public void initView() {
        super.initView();

        getRefreshLayout().setEnableLoadMore(false);
    }
}
