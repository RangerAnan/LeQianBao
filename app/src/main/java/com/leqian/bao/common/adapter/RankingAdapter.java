package com.leqian.bao.common.adapter;

import android.support.v4.content.ContextCompat;
import android.test.mock.MockContext;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.model.network.statistics.UserRankingResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.leqian.bao.view.fragment.MainFourFragment;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.widget.NXFragment;

import java.util.List;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class RankingAdapter extends BaseListAdapter<UserRankingResp.DataBean> {

    NXFragment fragment;

    public RankingAdapter(List<UserRankingResp.DataBean> dataList, NXFragment fragment) {
        super(dataList);
        this.fragment = fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_ranking;
    }

    @Override
    public void setData(BaseViewHolder holder, UserRankingResp.DataBean rankingUIModel, int position) {
        TextView tv_ranking = holder.getView(R.id.tv_ranking);
        ImageView iv_image = holder.getView(R.id.iv_image);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_desc = holder.getView(R.id.tv_desc);
        TextView tv_count = holder.getView(R.id.tv_count);

        showRanking(tv_ranking, position);
        tv_title.setText(rankingUIModel.getName());
        showCount(tv_count, rankingUIModel, position);
        tv_desc.setText(rankingUIModel.getName());

        String headIcon = BaseHttp.IMAGE_HOST + rankingUIModel.getHeadpic();
        GlideUtils.setCircleDrawableRequest(Glide.with(fragment), headIcon, R.mipmap.team_director).into(iv_image);

    }

    private void showCount(TextView tv_count, UserRankingResp.DataBean rankingUIModel, int position) {
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
