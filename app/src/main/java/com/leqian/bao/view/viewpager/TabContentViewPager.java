package com.leqian.bao.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止viewpager滑动
 *
 * @author fcl
 */

public class TabContentViewPager extends ViewPager {

    private boolean HAS_TOUCH_MODE = false;


    public TabContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TabContentViewPager(Context context) {
        super(context);
    }

    //向内部控件传递:不拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    //不响应事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return false;

    }

}
