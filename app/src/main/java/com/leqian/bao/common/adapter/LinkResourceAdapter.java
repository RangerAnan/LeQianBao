package com.leqian.bao.common.adapter;

import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.model.resource.LinkResourceResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.utils.Logger;

import java.util.List;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class LinkResourceAdapter extends BaseListAdapter<LinkResourceResp> {


    public LinkResourceAdapter(List<LinkResourceResp> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_resource_link;
    }

    @Override
    public void setData(BaseViewHolder holder, LinkResourceResp rankingUIModel, int position) {
        Logger.i(initTag() + "---setData--" + rankingUIModel.title);
        ((TextView) holder.getView(R.id.tvTitle)).setText(rankingUIModel.title);
    }

}
