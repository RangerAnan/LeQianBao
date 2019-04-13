package com.leqian.bao.view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leqian.bao.R;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class MainFirstFragment extends ViewpagerFragment {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.bar_left)
    RelativeLayout bar_left;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_first;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("马上赚钱");
        bar_left.setVisibility(View.INVISIBLE);
    }
}
