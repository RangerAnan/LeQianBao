package com.leqian.bao.view.activity.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.model.constant.Constants;
import com.nxin.base.widget.NXToolBarActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.20
 * desc:趋势图
 */
public class ClickedTrendActivity extends BaseToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.lineChart)
    LineChart lineChart;

    private String uid;

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_click_trend;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle extras = getIntent().getExtras();
        uid = extras.getString(Constants.INTENT_DATA_1);
        String name = extras.getString(Constants.INTENT_DATA_2);

        tv_title.setText(name + "点击明细趋势");
    }

    @Override
    public void initViewData() {
        super.initViewData();

        //模拟数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(i, new Random().nextInt(300)));
        }

        // add entries to dataset
        LineDataSet dataSet = new LineDataSet(entries, "");
        //线条设置
        //线条颜色
        dataSet.setColor(ContextCompat.getColor(mContext, R.color.theme));
        dataSet.setCircleColor(ContextCompat.getColor(mContext, R.color.theme));
        dataSet.setLineWidth(1f);

        //设置xy轴
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawLabels(false);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setGridColor(ContextCompat.getColor(mContext, R.color.white));
        axisLeft.setGridLineWidth(2f);
        axisLeft.setAxisLineColor(ContextCompat.getColor(mContext, R.color.white));
        axisLeft.setAxisLineWidth(2f);

        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setEnabled(false);
        axisRight.setGridColor(ContextCompat.getColor(mContext, R.color.white));
        axisRight.setGridLineWidth(2f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(true);
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(ContextCompat.getColor(mContext, R.color.white));
        xAxis.setGridLineWidth(2f);
        xAxis.setAxisLineColor(ContextCompat.getColor(mContext, R.color.white));
        xAxis.setAxisLineWidth(2f);
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
        lineChart.setVisibleXRangeMaximum(6);
        // 不可以双击缩放
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setScaleEnabled(false);
        //隐藏触摸高亮
        lineChart.setHighlightPerTapEnabled(false);

        lineChart.invalidate(); // refresh
    }

    @OnClick({R.id.bar_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            default:
                break;
        }
    }
}
