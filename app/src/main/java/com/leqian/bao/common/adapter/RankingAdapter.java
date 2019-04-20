package com.leqian.bao.common.adapter;

import android.support.v4.content.ContextCompat;
import android.test.mock.MockContext;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

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
        TextView tv_ranking = holder.getView(R.id.tv_ranking);
        ImageView iv_image = holder.getView(R.id.iv_image);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_desc = holder.getView(R.id.tv_desc);
        TextView tv_count = holder.getView(R.id.tv_count);

        showRanking(tv_ranking, position);
        tv_title.setText(rankingUIModel.name);
        showCount(tv_count, rankingUIModel, position);
        tv_desc.setText(rankingUIModel.getTeam());

    }

    private void showCount(TextView tv_count, RankingUIModel rankingUIModel, int position) {
        tv_count.setText(String.valueOf(rankingUIModel.getCount()));
        if (position == 0) {
            tv_count.setTextColor(ContextCompat.getColor(ProHelper.getApplication(), R.color.theme));
        } else if (position == 1) {
            tv_count.setTextColor(ContextCompat.getColor(ProHelper.getApplication(), R.color.btn_orange4));
        } else if (position == 2) {
            tv_count.setTextColor(ContextCompat.getColor(ProHelper.getApplication(), R.color.btn_orange5));
        } else {
            tv_count.setTextColor(ContextCompat.getColor(ProHelper.getApplication(), R.color.color_gray));
        }
    }

    private void showRanking(TextView tv_ranking, int position) {
        if (position == 0) {
            tv_ranking.setText("");
            tv_ranking.setBackgroundResource(R.mipmap.no_one_crown);
        } else if (position == 1) {
            tv_ranking.setText("");
            tv_ranking.setBackgroundResource(R.mipmap.no_two_crown);
        } else if (position == 2) {
            tv_ranking.setText("");
            tv_ranking.setBackgroundResource(R.mipmap.no_three_crown);
        } else {
            tv_ranking.setText(String.valueOf(position + 1));
            tv_ranking.setBackgroundColor(ContextCompat.getColor(ProHelper.getApplication(), R.color.transparent));
        }
    }

}
