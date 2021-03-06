package com.leqian.bao.view.activity.resource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.VideoListAdapter;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.leqian.bao.common.http.ResourceHttp;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.model.bll.ShareBLL;
import com.leqian.bao.model.network.resource.LinkResourceResp;
import com.leqian.bao.model.network.resource.VidoeListResp;
import com.leqian.bao.view.dialog.share.ShareMenuDialog;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class VideoListActivity extends BaseListToolBarActivity implements RadioGroup.OnCheckedChangeListener, OnItemClickListener {

    @BindView(R.id.bar_left)
    RelativeLayout bar_left;

    @BindView(R.id.bar_right)
    RelativeLayout bar_right;

    @BindView(R.id.bar_right_tv)
    TextView bar_right_tv;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.radio_button1)
    RadioButton radio_button1;

    @BindView(R.id.radio_button2)
    RadioButton radio_button2;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.listView)
    ListView listView;

    List<VidoeListResp.DataBean> mListData;
    VideoListAdapter mAdapter;

    boolean publicCover;

    int selectPosition = 0;


    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_main_ranking;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public boolean isOpenViewState() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        showContentView();
        bar_left.setVisibility(View.VISIBLE);
        if (ShareUtilUser.getString(ShareUtilUser.ORG_TYPE, "").equals("1")) {
            bar_right_tv.setText("新增");
            bar_right.setVisibility(View.VISIBLE);
        } else {
            bar_right.setVisibility(View.GONE);
        }
        getRefreshLayout().setEnableLoadMore(false);

        radio_button1.setText("团队标题");
        radio_button2.setText("公有标题");
        radioGroup.setOnCheckedChangeListener(this);

        listView.setOnItemClickListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        getRefreshLayout().autoRefresh();
        mListData = new ArrayList<>();

        mAdapter = new VideoListAdapter(mListData, mContext);
        listView.setAdapter(mAdapter);
    }


    private void requestCoverList() {
        ResourceHttp.getCover(publicCover, new ModelCallBack<VidoeListResp>() {
            @Override
            public void onResponse(VidoeListResp response, int id) {
                if (response.getCode() != 1) {
                    showErrorView();
                    return;
                }
                if (response.getData() == null || response.getData().size() == 0) {
                    showEmptyView("暂无资源");
                    return;
                }
                showContentView();
                getRefreshLayout().finishRefresh();
                mListData.clear();
                mListData = response.getData();
                mAdapter.setListData(mListData);
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        requestCoverList();
    }

    @OnClick({R.id.bar_left, R.id.bar_right, R.id.btn_ok})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.bar_right:
                startActivity(new Intent(mContext, MakeCoverActivity.class));
                break;
            case R.id.btn_ok:
                requestLink();
                break;
            default:
                break;
        }
    }

    private void requestLink() {
        ResourceHttp.getLink(true, new ModelCallBack<LinkResourceResp>("正在加载...") {
            @Override
            public void onResponse(LinkResourceResp response, int id) {
                List<String> data = response.getData();
                if (data.size() == 0) {
                    Logger.i(initTag() + "--- data is null");
                    return;
                }
                showShareDialog(data.get(0));

            }
        });
    }

    private void showShareDialog(final String link) {
        ShareMenuDialog shareMenuDialog = new ShareMenuDialog(mContext, 0, new ShareMenuDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(int type, int id) {
                VidoeListResp.DataBean dataBean = mListData.get(selectPosition);
                ShareBLL.getInstance().shareToWX(id, ProHelper.getScreenHelper().currentActivity(), link,
                        dataBean.getTitle(), BaseHttp.IMAGE_HOST + dataBean.getPic(), dataBean.getDesc());
            }
        });
        shareMenuDialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button1:
                //团队标题
                publicCover = false;
                requestCoverList();
                changeRadioButtonTextColor(true);
                getRefreshLayout().autoRefresh();
                break;
            case R.id.radio_button2:
                //公有标题
                publicCover = true;
                requestCoverList();
                changeRadioButtonTextColor(false);
                getRefreshLayout().autoRefresh();
                break;
            default:
                break;
        }
    }

    private void changeRadioButtonTextColor(boolean button1Check) {
        radio_button1.setTextColor(button1Check ? ContextCompat.getColor(mContext, R.color.theme) : ContextCompat.getColor(mContext, R.color.color_gray));
        radio_button2.setTextColor(!button1Check ? ContextCompat.getColor(mContext, R.color.theme) : ContextCompat.getColor(mContext, R.color.color_gray));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateCheckBoxState(position);
    }

    private void updateCheckBoxState(int position) {
        VidoeListResp.DataBean dataBean = mListData.get(position);
        if (dataBean.getState() != 1) {
            ToastUtil.showToastShort("该资源不可推送");
            return;
        }
        for (int i = 0; i < mListData.size(); i++) {
            mListData.get(i).setCheck(i == position);
        }
        mAdapter.setListData(mListData);
        selectPosition = position;
        btn_ok.setEnabled(true);
    }
}
