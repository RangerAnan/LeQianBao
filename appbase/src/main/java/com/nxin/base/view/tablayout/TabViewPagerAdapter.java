package com.nxin.base.view.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nxin.base.widget.NXFragment;

import java.util.List;

/**
 * Created by fcl on 18.2.8.
 * desc：tabLayout适配器
 */

public class TabViewPagerAdapter<T extends NXFragment> extends FragmentPagerAdapter {

    public String[] mTitle;
    List<T> list;

    public TabViewPagerAdapter(FragmentManager fm, List<T> list, String[] mTitle) {
        super(fm);
        this.list = list;
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
