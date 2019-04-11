package com.nxin.base.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;


import com.nxin.base.R;
import com.nxin.base.common.ScreenManager;
import com.nxin.base.utils.KeyboardUtils;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.utils.SystemUtil;
import com.nxin.base.view.loading.CommonEmptyView;
import com.nxin.base.view.swipeback.SwipeBackLayout;
import com.nxin.base.view.swipeback.app.SwipeBackActivity;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * NXActivity
 *
 * @author fcl
 */
public class NXActivity extends SwipeBackActivity {

    protected Context mContext;
    protected LayoutInflater inflater;

    private ViewAnimator mViewAnimator;
    private LinearLayout headContainer;                      //头部视图容器

    private String emptyDesc;                                //空状态
    private boolean isShowContent = false;                   //是否显示状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        inflater = LayoutInflater.from(mContext);
        ScreenManager.getInstance().pushActivity(this);
        if (isOpenEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setSwipeBackLayout();

        initLayout();
        ButterKnife.bind(this);
        initView();
        initViewData();
    }

    private void initLayout() {
        if (isOpenViewState()) {
            View mContentView = inflater.inflate(R.layout.activity_view, null, false);
            mViewAnimator = mContentView.findViewById(R.id.home);
            headContainer = mContentView.findViewById(R.id.head_container);
            addHeaderView(headContainer);
            // 加载布局-初始化:将rootView附加到布局文件的根视图上
            inflater.inflate(loadingViewLayout(), mViewAnimator, true);
            // 内容布局-初始化
            inflater.inflate(getLayoutId(), mViewAnimator, true);
            // 空布局-初始化
            inflater.inflate(emptyViewLayout(), mViewAnimator, true);
            // 错误布局-初始化
            inflater.inflate(errorViewLayout(), mViewAnimator, true);
            setContentView(mContentView);
        } else {
            setContentView(getLayoutId());
        }
    }

    public void initViewData() {

    }

    public void initView() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {//非默认值
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 右滑退出功能设置
     */
    private void setSwipeBackLayout() {
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        swipeBackLayout.setEdgeSize(SystemUtil.dp2px(30));
        swipeBackLayout.setEnableGesture(!swipeBackCancel());
        swipeBackLayout.setOnFinishListener(new SwipeBackLayout.IOnFinishListener() {
            @Override
            public void onSwipeFinish() {
                swipeBackFinish();
            }
        });
    }


    /**
     * 右滑完成回调
     * notice：页面退出需要发送通知的，请重写该方法，禁止再调用finish方法
     */
    protected void swipeBackFinish() {
        Log.i(initTag(), "---swipeBackFinish--");
    }


    @Override
    protected void onDestroy() {
        hideSoftKeyboard();
        super.onDestroy();
        ScreenManager.getInstance().popActivity(this);
        if (isOpenEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onBackPressed() {
        //Can not perform this action after onSaveInstanceState
        if (!isFinishing()) {
            hideSoftKeyboard();
            super.onBackPressed();
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 类标记
     */
    public String initTag() {
        return getClass().getSimpleName();
    }

    /**
     * 是否开启eventBus
     */
    public boolean isOpenEventBus() {
        return false;
    }

    /**
     * 是否开启页面状态
     *
     * @return 默认关闭
     */
    public boolean isOpenViewState() {
        return false;
    }

    /**
     * 是否取消右滑退出，默认使用该功能
     *
     * @return 取消时子类重写该方法，并返回true
     */
    protected boolean swipeBackCancel() {
        return false;
    }


    /**
     * 初始化状态布局 - 加载
     *
     * @return
     */
    public int loadingViewLayout() {
        return R.layout.view_loading;
    }

    /**
     * 默认布局
     *
     * @return
     */
    public int getLayoutId() {
        return R.layout.view_frame;
    }

    /**
     * 初始化状态布局 - 空
     *
     * @return
     */
    public int emptyViewLayout() {
        return R.layout.view_empty;
    }

    /**
     * 初始化状态布局 - 错误
     *
     * @return
     */
    public int errorViewLayout() {
        return R.layout.view_error;
    }

    /**
     * 显示 * @param showState 0，1，2，3
     */
    private void show(int showState) {
        if (!isOpenViewState()) {
            return;
        }
        if (mViewAnimator == null) {
            return;
        }
        int displayedChild = mViewAnimator.getDisplayedChild();
        Log.i(initTag(), "show()---displayedChild:" + displayedChild + "--showState:" + showState);
        if (displayedChild == showState) {
            return;
        }
        mViewAnimator.setDisplayedChild(showState);
        // 如果是错误页面可以点击
        if (showState == 3) {
            CommonEmptyView commonEmptyView = findViewById(R.id.common_error_view);
            commonEmptyView.dealRequestDataFail();
            commonEmptyView.setOnClickListener(new CommonEmptyView.OnEmptyViewClickListener() {
                @Override
                public void onClickCallback() {
                    showLoadingView();
                    initViewData();
                }
            });
//            mViewAnimator.getCurrentView().setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showLoadingView();
//                    initViewData();
//                }
//            });
        } else if (showState == 2) {
            //空页面描述
            CommonEmptyView commonEmptyView = findViewById(R.id.common_empty_view);
            commonEmptyView.dealRequestDataEmpty(emptyDesc);
        }
    }

    public void showEmptyView(String desc) {
        emptyDesc = desc;
        show(2);
        isShowContent = false;
    }

    public void showErrorView() {
        show(3);
        isShowContent = false;
    }

    public void showLoadingView() {
        show(0);
        isShowContent = false;
    }

    public void showContentView() {
        show(1);
        isShowContent = true;
    }

    /**
     * 添加头部视图
     */
    private void addHeaderView(LinearLayout containerView) {
        View view = inflater.inflate(getTopView(), containerView, false);
        containerView.addView(view);
    }

    /**
     * 获取头部view
     *
     * @return
     */
    public int getTopView() {
        return R.layout.top_view;
    }

    /**
     * 触摸时是否收起键盘
     */
    public boolean hideSoftInputOnTouch() {
        return true;
    }

    /**
     * 屏幕点击事件 - 关闭键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && hideSoftInputOnTouch()) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (KeyboardUtils.isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(ProHelper.getScreenHelper().currentActivity());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void intent2Activity(Class clazz) {
        this.intent2Activity(clazz, (Bundle) null, 0, (ActivityOptionsCompat) null);
    }

    public void intent2Activity(Class clazz, int requestCode) {
        this.intent2Activity(clazz, (Bundle) null, requestCode, (ActivityOptionsCompat) null);
    }

    public void intent2Activity(Class clazz, Bundle bundle) {
        this.intent2Activity(clazz, bundle, 0, (ActivityOptionsCompat) null);
    }

    public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {
        this.intent2Activity(clazz, bundle, 0, optionsCompat);
    }

    public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {
        if (clazz != null) {
            Intent intent = new Intent();
            intent.setClass(this, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }

            if (optionsCompat == null) {
                if (requestCode > 0) {
                    this.startActivityForResult(intent, requestCode);
                } else {
                    this.startActivity(intent);
                }
            } else if (requestCode > 0) {
                ActivityCompat.startActivityForResult(this, intent, requestCode, optionsCompat.toBundle());
            } else {
                ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
            }
        }

    }

}