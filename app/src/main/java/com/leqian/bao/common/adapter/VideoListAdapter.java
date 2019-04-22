package com.leqian.bao.common.adapter;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.resource.VidoeListResp;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.model.network.glide.GlideUtils;

import java.util.List;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class VideoListAdapter extends BaseListAdapter<VidoeListResp.DataBean> {

    Context context;

    public VideoListAdapter(List<VidoeListResp.DataBean> dataList, Context context) {
        super(dataList);
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_video_list;
    }

    @Override
    public void setData(BaseViewHolder holder, VidoeListResp.DataBean model, int position) {
        ImageView check_box = holder.getView(R.id.check_box);
        ImageView iv_image = holder.getView(R.id.iv_image);
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_desc = holder.getView(R.id.tv_desc);
        TextView tv_check_state = holder.getView(R.id.tv_check_state);

        GlideUtils.setDrawableRequest(Glide.with(context), BaseHttp.IMAGE_HOST + model.getPic(), R.mipmap.image_defalut).into(iv_image);
        tv_title.setText(model.getTitle());
        tv_desc.setText(model.getDesc());
        String checkState = "";
        if (model.getState() == 1) {
            checkState = "审核通过";
        } else if (model.getState() == 2) {
            checkState = "审核失败";
        } else if (model.getState() == 0) {
            checkState = "等待审核";
        }
        tv_check_state.setText(checkState);

        check_box.setImageResource(model.isCheck() ? R.drawable.industry_cb_selected : R.drawable.industry_cb_normal);
    }
}
