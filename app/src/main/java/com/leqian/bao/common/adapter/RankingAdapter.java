package com.leqian.bao.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.utils.Logger;

import java.util.List;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class RankingAdapter extends BaseListAdapter<RankingUIModel> {


    public RankingAdapter(List<RankingUIModel> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_ranking;
    }

    @Override
    public void setData(BaseViewHolder holder, RankingUIModel rankingUIModel, int position) {
//        Logger.i(initTag() + "---setData--" + rankingUIModel.name);
        ((TextView) holder.getView(R.id.tvTitle)).setText(rankingUIModel.name);
    }

}
