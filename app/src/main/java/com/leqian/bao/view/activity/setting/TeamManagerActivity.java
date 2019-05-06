package com.leqian.bao.view.activity.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.network.account.UpdateTeamManageResp;
import com.leqian.bao.model.network.statistics.TeamClickDetailResp;
import com.leqian.bao.model.network.team.TeamInfoResp;
import com.leqian.bao.view.activity.resource.MakeCoverActivity;
import com.leqian.bao.view.linechart.XAxisValueFormatter;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.utils.system.ClipboardUtils;
import com.nxin.base.widget.NXToolBarActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class TeamManagerActivity extends BaseListToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_team)
    TextView tv_team;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_invite_code)
    TextView tv_invite_code;

    @BindView(R.id.iv_image)
    ImageView iv_image;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.lineChart)
    LineChart lineChart;

    @BindView(R.id.team_click_count)
    TextView team_click_count;

    @BindView(R.id.click_week_count)
    TextView click_week_count;

    @BindView(R.id.lineChart_week)
    LineChart lineChart_week;

    @BindView(R.id.cardView_week)
    CardView cardView_week;

    @BindView(R.id.cardView_today)
    CardView cardView_today;

    TeamInfoResp model;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_manager;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("团队管理");

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonState(et_content.getText().toString().trim());
            }
        });

        getRefreshLayout().setEnableLoadMore(false);
    }

    @Override
    public void initViewData() {
        super.initViewData();
        getRefreshLayout().autoRefresh();

        model = new TeamInfoResp();

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        requestTeamInfo();
        requestTeamClickDetail();
    }

    private void requestTeamInfo() {
        AccountHttp.getTeamManage(new ModelCallBack<TeamInfoResp>() {
            @Override
            public void onResponse(TeamInfoResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                model = response;
                showTeamInfo(response.getData());
            }
        });
    }

    private void showTeamInfo(TeamInfoResp.DataBean model) {
        SpannableString teamSpan = new SpannableString(model.getName() + getString(R.string.user_team) + "（" + model.getPopulation() + "人）");
        teamSpan.setSpan(new AbsoluteSizeSpan((int) DeviceUtil.sp2px(14)), (model.getName() + getString(R.string.user_team)).length(),
                teamSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        teamSpan.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), (model.getName() + getString(R.string.user_team)).length(),
                teamSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_team.setText(teamSpan);


        SpannableString codeSpan = new SpannableString(model.getCodeX() + getString(R.string.team_copy));
        codeSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_0000ff)), model.getCodeX().length(),
                codeSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_invite_code.setText(codeSpan);

        tv_name.setText(model.getName());
        et_content.setText(model.getAnnouncement());
        et_content.setSelection(model.getAnnouncement().length());
        GlideUtils.setCircleDrawableRequest(Glide.with(this), BaseHttp.IMAGE_HOST + model.getHeadpic(), R.mipmap.me_default_icon).into(iv_image);
    }

    private void setButtonState(String content) {
        btn_ok.setEnabled(!TextUtils.isEmpty(content));
    }

    @OnClick({R.id.bar_left, R.id.btn_ok, R.id.tv_invite_code})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.btn_ok:
                String teamDesc = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(teamDesc)) {
                    ToastUtil.showToastShort(R.string.input_not_empty);
                    return;
                }
                releaseAnnouncement(teamDesc);
                break;
            case R.id.tv_invite_code:
                ClipboardUtils.setTextToClipboard(mContext, model.getData().getCodeX(), model.getData().getCodeX());
                ToastUtil.showToastShort("复制成功");
                break;
            default:
                break;
        }
    }

    private void releaseAnnouncement(String teamDesc) {
        AccountHttp.updateTeamManage(teamDesc, new ModelCallBack<UpdateTeamManageResp>("正在发布...") {
            @Override
            public void onResponse(UpdateTeamManageResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                ToastUtil.showToastShort("发布成功");
            }
        });
    }


    private void requestTeamClickDetail() {
        StatisticsHttp.getTeamClickDetail(new ModelCallBack<TeamClickDetailResp>() {
            @Override
            public void onResponse(TeamClickDetailResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }

                //1.今日统计
                if (response.getData().getToday().size() > 0) {
                    cardView_today.setVisibility(View.VISIBLE);
                }
                List<Entry> entries = new ArrayList<>();
                for (int i = 0; i < 24; i++) {
                    entries.add(new Entry(i, response.getData().getToday().get(i)));
                }
                setBrokenLine(entries, lineChart, false, null);
                team_click_count.setText(team_click_count.getText() + "：" + response.getData().getTotalToday() + "次");

                //2.本周统计
                List<TeamClickDetailResp.DataBean.DayBean> dayBeanList = response.getData().getDay();
                if (dayBeanList.size() > 0) {
                    cardView_week.setVisibility(View.VISIBLE);
                }

                List<Entry> weekEntries = new ArrayList<>();
                String[] str = new String[dayBeanList.size()];
                for (int i = 0; i < dayBeanList.size(); i++) {
                    weekEntries.add(new Entry(i, dayBeanList.get(i).getCount()));
                    str[i] = dayBeanList.get(i).getDate();
                }
                XAxisValueFormatter xAxisValueFormatter = new XAxisValueFormatter(str);
                setBrokenLine(weekEntries, lineChart_week, true, xAxisValueFormatter);
                click_week_count.setText(click_week_count.getText() + "：" + response.getData().getTotalToday() + "次");

            }
        });
    }

    private void setBrokenLine(List<Entry> entries, LineChart lineChart, boolean isFormat, ValueFormatter formatter) {
        // add entries to dataset
        LineDataSet dataSet = new LineDataSet(entries, "");
        //线条设置
        dataSet.setColor(ContextCompat.getColor(mContext, R.color.yellow_ffd500));
        dataSet.setCircleColor(ContextCompat.getColor(mContext, R.color.yellow_ffd500));
        dataSet.setLineWidth(1.4f);
        dataSet.setValueTextSize(10);
        dataSet.setValueTextColor(ContextCompat.getColor(mContext, R.color.yellow_ffd500));
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(ContextCompat.getColor(mContext, R.color.yellow_ffd500));
        dataSet.setFillAlpha(20);

        //设置y轴
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawLabels(false);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setGridColor(ContextCompat.getColor(mContext, R.color.white));
        axisLeft.setGridLineWidth(2f);
        axisLeft.setAxisLineColor(ContextCompat.getColor(mContext, R.color.white));
        axisLeft.setAxisLineWidth(2f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setEnabled(false);
        axisRight.setGridColor(ContextCompat.getColor(mContext, R.color.white));
        axisRight.setGridLineWidth(2f);

        //设置x轴的显示位置
        XAxis xAxis = lineChart.getXAxis();
        if (isFormat && formatter != null) {
            xAxis.setValueFormatter(formatter);
        }

        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(ContextCompat.getColor(mContext, R.color.white));
        xAxis.setGridLineWidth(2f);
        xAxis.setAxisLineColor(ContextCompat.getColor(mContext, R.color.white));
        xAxis.setAxisLineWidth(2f);
        //避免第一次最后剪裁
        xAxis.setAvoidFirstLastClipping(true);


        //透明化图例
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);


        //chart设置数据
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        //设置x轴最多显示数据条数
        lineChart.setVisibleXRangeMaximum(7.5f);
        // 不可以双击缩放
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setScaleEnabled(false);
        //隐藏触摸高亮
        lineChart.setHighlightPerTapEnabled(false);

        lineChart.invalidate(); // refresh
    }
}
