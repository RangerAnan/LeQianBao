package com.leqian.bao.common.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.leqian.bao.R;
import com.leqian.bao.common.http.ResourceHttp;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.bll.LoginBLL;
import com.leqian.bao.model.network.resource.LinkResourceResp;
import com.leqian.bao.view.activity.resource.VideoListActivity;
import com.nxin.base.common.adapter.BaseListAdapter;
import com.nxin.base.common.adapter.BaseViewHolder;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.utils.system.ClipboardUtils;

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
                requestLinkResource();
                break;
            case R.id.iv_add_link:
                FragmentActivity currentActivity = ProHelper.getScreenHelper().currentActivity();
                currentActivity.startActivity(new Intent(currentActivity, VideoListActivity.class));
                break;
            default:
                break;
        }
    }

    private void requestLinkResource() {
        ResourceHttp.getLink(false, new ModelCallBack<LinkResourceResp>() {
            @Override
            public void onResponse(LinkResourceResp response, int id) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("视频（").append(LoginBLL.getInstance().getUserInfo().getData().getName()).append(")：");

                for (String link : response.getData()) {
                    stringBuilder.append("\n" + link);
                }

                ClipboardUtils.setTextToClipboard(ProHelper.getScreenHelper().currentActivity(), stringBuilder.toString(), stringBuilder.toString());
                ToastUtil.showToastShort("复制成功");

            }
        });

    }

}
