package com.leqian.bao.common.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.leqian.bao.model.Constants;
import com.leqian.bao.view.fragment.MainFirstFragment;
import com.leqian.bao.view.fragment.MainFourFragment;
import com.leqian.bao.view.fragment.MainThreeFragment;
import com.leqian.bao.view.fragment.MainTwoFragment;
import com.leqian.bao.view.fragment.ViewpagerFragment;


public class FragmentAdapter extends FragmentPagerAdapter {

    SparseArray<ViewpagerFragment> fragments = new SparseArray<>();

    ViewpagerFragment vpFragment;

    //模块的个数
    public int TAB_COUNT = 4;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * ****fragment白屏问题所在
     *
     * @param position
     * @return
     */
    @Override
    public ViewpagerFragment getItem(int position) {
        vpFragment = fragments.get(position);

        if (vpFragment == null) {

            switch (position) {
                case Constants.TAB_FIRST:

                    vpFragment = new MainFirstFragment();

                    break;
                case Constants.TAB_MONEY:

                    vpFragment = new MainTwoFragment();
                    break;
                case Constants.TAB_FIND:

                    vpFragment = new MainThreeFragment();
                    break;

                case Constants.TAB_MINE:

                    vpFragment = new MainFourFragment();
                    break;
                default:
                    break;
            }
            //将数据放到集合中
            fragments.put(position, vpFragment);

        }
        return vpFragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }


    /**
     * 销毁
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(fragments.get(position).getView());
    }


}
