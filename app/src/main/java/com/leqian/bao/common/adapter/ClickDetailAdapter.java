package com.leqian.bao.common.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.model.network.resource.VidoeListResp;
import com.leqian.bao.model.network.statistics.ClickDetailResp;
import com.leqian.bao.model.network.statistics.PersonClickDetailResp;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.utils.Logger;

import java.util.List;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class ClickDetailAdapter extends BaseListAdapter<PersonClickDetailResp.DataBean.DayBean> {

    Context context;

    public ClickDetailAdapter(List<PersonClickDetailResp.DataBean.DayBean> dataList, Context context) {
        super(dataList);
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_click_list;
    }

    @Override
    public void setData(BaseViewHolder holder, PersonClickDetailResp.DataBean.DayBean model, int position) {

        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_detail_count = holder.getView(R.id.tv_detail_count);

        tv_title.setText(model.getDate());
        tv_detail_count.setText("总计费" + String.valueOf(model.getCount()) + "次");

    }
}
