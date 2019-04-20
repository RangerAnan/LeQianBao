package com.leqian.bao.common.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.leqian.bao.R;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.widget.NXFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class RankingFragmentAdapter extends FragmentPagerAdapter {

    List<NXFragment> list;
    String[] mTitle;

    public RankingFragmentAdapter(FragmentManager fm, List<NXFragment> list) {
        super(fm);
        this.list = list;
        mTitle = ProHelper.getApplication().getResources().getStringArray(R.array.ranking_vp_item);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    public void setList(ArrayList<NXFragment> mFragmentList) {
        this.list = mFragmentList;
        notifyDataSetChanged();
    }
}
