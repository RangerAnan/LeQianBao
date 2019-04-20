package com.leqian.bao.common.adapter;

import com.leqian.bao.R;
import com.leqian.bao.model.resource.VidoeListResp;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class VideoListAdapter extends BaseListAdapter<VidoeListResp> {

    public VideoListAdapter(List<VidoeListResp> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_video_list;
    }

    @Override
    public void setData(BaseViewHolder holder, VidoeListResp o, int position) {

    }
}
