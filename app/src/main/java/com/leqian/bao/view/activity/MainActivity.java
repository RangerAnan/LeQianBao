package com.leqian.bao.view.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.FragmentAdapter;
import com.leqian.bao.model.Constants;
import com.leqian.bao.view.viewpager.TabContentViewPager;
import com.nxin.base.widget.NXActivity;
import com.nxin.base.widget.NXToolBarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends NXActivity {

    @BindView(R.id.iv_tab1)
    ImageView iv_tab1;
    @BindView(R.id.iv_tab2)
    ImageView iv_tab2;
    @BindView(R.id.iv_tab3)
    ImageView iv_tab3;
    @BindView(R.id.iv_tab4)
    ImageView iv_tab4;

    @BindView(R.id.tv_tab1)
    TextView tv_tab1;
    @BindView(R.id.tv_tab2)
    TextView tv_tab2;
    @BindView(R.id.tv_tab3)
    TextView tv_tab3;
    @BindView(R.id.tv_tab4)
    TextView tv_tab4;

    @BindView(R.id.viewpager)
    TabContentViewPager viewpager;
    FragmentAdapter vpAdapter;

    ArrayList<ImageView> tabImgList = new ArrayList<>();
    ArrayList<TextView> tabTextList = new ArrayList<>();
    List<Integer> unSelectImgList = new ArrayList<>();
    List<Integer> selectImgList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();

        tabImgList.clear();
        tabImgList.add(iv_tab1);
        tabImgList.add(iv_tab2);
        tabImgList.add(iv_tab3);
        tabImgList.add(iv_tab4);

        tabTextList.clear();
        tabTextList.add(tv_tab1);
        tabTextList.add(tv_tab2);
        tabTextList.add(tv_tab3);
        tabTextList.add(tv_tab4);

        unSelectImgList.add(R.drawable.tab_msg1);
        unSelectImgList.add(R.drawable.tab_circle1);
        unSelectImgList.add(R.drawable.tab_service1);
        unSelectImgList.add(R.drawable.tab_me1);

        selectImgList.add(R.drawable.tab_msg2);
        selectImgList.add(R.drawable.tab_circle2);
        selectImgList.add(R.drawable.tab_service2);
        selectImgList.add(R.drawable.tab_me2);

        setTabStyleChange(0);

        //set viewpager
        viewpager.setOffscreenPageLimit(3);
        vpAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewpager.setAdapter(vpAdapter);
        viewpager.setPageTransformer(false, null);

        //设置邀请码

    }

    public void setTabStyleChange(int position) {
        for (int i = 0; i < tabImgList.size(); i++) {
            int drawable = position == i ? selectImgList.get(i) : unSelectImgList.get(i);
            tabImgList.get(i).setImageResource(drawable);
        }

        for (int i = 0; i < tabTextList.size(); i++) {
            int color = position == i ? ContextCompat.getColor(mContext, R.color.theme) : ContextCompat.getColor(mContext, R.color.black);
            tabTextList.get(i).setTextColor(color);
        }
    }


    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                setTabStyleChange(0);
                viewpager.setCurrentItem(Constants.TAB_FIRST, false);
                break;
            case R.id.rl2:
                setTabStyleChange(1);
                viewpager.setCurrentItem(Constants.TAB_MONEY, false);
                break;
            case R.id.rl3:
                setTabStyleChange(2);
                viewpager.setCurrentItem(Constants.TAB_FIND, false);
                break;
            case R.id.rl4:
                setTabStyleChange(3);
                viewpager.setCurrentItem(Constants.TAB_MINE, false);
                break;
            default:
                break;
        }
    }
}
