package com.leqian.bao.view.linechart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by fcl on 19.5.6
 * desc:
 */
public class XAxisValueFormatter extends ValueFormatter {

    private final String[] mLabels;

    public XAxisValueFormatter(String[] labels) {
        mLabels = labels;
    }


    @Override
    public String getFormattedValue(float value) {
        try {
            return mLabels[(int) value];
        } catch (Exception e) {
            e.printStackTrace();
            return mLabels[0];
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        try {
            return mLabels[(int) value];
        } catch (Exception e) {
            e.printStackTrace();
            return mLabels[0];
        }
    }
}
