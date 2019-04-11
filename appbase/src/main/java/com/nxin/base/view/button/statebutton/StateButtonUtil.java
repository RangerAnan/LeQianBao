package com.nxin.base.view.button.statebutton;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fcl on 19.3.4
 * desc:
 */

public class StateButtonUtil {

    public static StateButtonUtil instance;

    public static StateButtonUtil getInstance() {
        if (instance == null) {
            instance = new StateButtonUtil();
        }
        return instance;
    }


    public void setStateConfig(View view, StateConfig config) {

        int[][] states = {{android.R.attr.state_enabled, android.R.attr.state_pressed},
                {android.R.attr.state_enabled, android.R.attr.state_focused},
                {-android.R.attr.state_enabled}, {android.R.attr.state_enabled}};

        GradientDrawable mNormalBackground = new GradientDrawable();
        GradientDrawable mPressedBackground = new GradientDrawable();
        GradientDrawable mUnableBackground = new GradientDrawable();

        Drawable background = view.getBackground();
        StateListDrawable mStateBackground = (background != null && background instanceof StateListDrawable) ? (StateListDrawable) background : new StateListDrawable();

        setDrawable(mNormalBackground, config.getNormalBackgroundColor(), config.getRadius(),
                config.getNormalStrokeWidth(), config.getNormalStrokeColor(),
                config.getStrokeDashWidth(), config.getStrokeDashGap());

        setDrawable(mPressedBackground, config.getPressedBackgroundColor(), config.getRadius(),
                config.getPressedStrokeWidth(), config.getPressedStrokeColor(),
                config.getStrokeDashWidth(), config.getStrokeDashGap());

        setDrawable(mUnableBackground, config.getUnableBackgroundColor(), config.getRadius(),
                config.getUnableStrokeWidth(), config.getUnableStrokeColor(),
                config.getStrokeDashWidth(), config.getStrokeDashGap());


        if (view instanceof TextView) {
            int[] colors = {config.getPressedTextColor(), config.getPressedTextColor(), config.getUnableTextColor(), config.getNormalTextColor()};
            ((TextView) view).setTextColor(new ColorStateList(states, colors));
        }

        //pressed
        mStateBackground.addState(states[0], mPressedBackground);
        //focus
        mStateBackground.addState(states[1], mPressedBackground);
        //unable
        mStateBackground.addState(states[2], mUnableBackground);
        //normal
        mStateBackground.addState(states[3], mNormalBackground);

        mStateBackground.setEnterFadeDuration(config.getDuration());

        ViewCompat.setBackground(view, mStateBackground);

    }

    private void setDrawable(GradientDrawable drawable, int backgroundColor, float radius, int strokeWidth, int strokeColor, float strokeDashWidth, float strokeDashGap) {
        drawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap);
        drawable.setColor(backgroundColor);
        drawable.setCornerRadius(radius);
    }
}
