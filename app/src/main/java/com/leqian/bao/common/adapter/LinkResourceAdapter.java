package com.leqian.bao.common.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.http.ResourceHttp;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.resource.LinkResourceResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;

import java.util.List;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class LinkResourceAdapter extends BaseListAdapter<LinkResourceResp> implements View.OnClickListener {


    public LinkResourceAdapter(List<LinkResourceResp> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout() {
        return R.layout.item_lv_resource_link;
    }

    @Override
    public void setData(BaseViewHolder holder, LinkResourceResp rankingUIModel, int position) {
        ImageButton iv_copy_link = holder.getView(R.id.iv_copy_link);
        iv_copy_link.setOnClickListener(this);

        ImageButton iv_add_link = holder.getView(R.id.iv_add_link);
        iv_add_link.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_copy_link:
                ToastUtil.showToastShort("复制资源");
                break;
            case R.id.iv_add_link:
                ToastUtil.showToastShort("新增资源");
                break;
            default:
                break;
        }
    }

    private void requestLinkResource() {
        ResourceHttp.getLink(new ModelCallBack<LinkResourceResp>() {
            @Override
            public void onResponse(LinkResourceResp response, int id) {

            }
        });
    }
}
