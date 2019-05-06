package com.leqian.bao.view.linechart;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by fcl on 19.5.6
 * desc:
 */
public class LineValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        int result = (int) value;
        return String.valueOf(result);
    }
}
