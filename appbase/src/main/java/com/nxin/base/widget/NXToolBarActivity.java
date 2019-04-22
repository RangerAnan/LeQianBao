package com.nxin.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import com.nxin.base.R;
import com.nxin.base.common.ScreenManager;
import com.nxin.base.utils.KeyboardUtils;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.utils.SystemUtil;
import com.nxin.base.view.dialog.LoadingDialog;
import com.nxin.base.view.loading.CommonEmptyView;
import com.nxin.base.view.swipeback.SwipeBackLayout;
import com.nxin.base.view.swipeback.app.SwipeBackActivity;
import com.nxin.base.widget.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * NXToolBarActivity
 *
 * @author fcl
 */
public class NXToolBarActivity extends SwipeBackActivity {

    protected Context mContext;
    protected LayoutInflater inflater;

    private ViewAnimator mViewAnimator;

    private String emptyDesc;                                //空状态
    private boolean isShowContent = false;                   //是否显示状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        inflater = LayoutInflater.from(mContext);
        if (addStackManager()) {
            ScreenManager.getInstance().pushActivity(this);
        }
        if (isOpenEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        setSwipeBackLayout();

        initLayout();
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(mContext, R.color.theme));
        initView();
        initViewData();
    }

    private void initLayout() {
        Toolbar mToolbar;
        View rootView;
        if (isOpenViewState()) {
            rootView = View.inflate(this, R.layout.toolbar_activity_view_state, null);
            mViewAnimator = rootView.findViewById(R.id.home);
            mToolbar = rootView.findViewById(R.id.toolbar);

            // 加载布局-初始化:将rootView附加到布局文件的根视图上
            inflater.inflate(loadingViewLayout(), mViewAnimator, true);
            // 内容布局-初始化
            inflater.inflate(getLayoutId(), mViewAnimator, true);
            // 空布局-初始化
            inflater.inflate(emptyViewLayout(), mViewAnimator, true);
            // 错误布局-初始化
            inflater.inflate(errorViewLayout(), mViewAnimator, true);
        } else {
            rootView = View.inflate(this, R.layout.toolbar_activity_view, null);
            mToolbar = rootView.findViewById(R.id.toolbar);
            ViewGroup content = rootView.findViewById(R.id.home);
            View.inflate(this, getLayoutId(), content);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0.0F);
        }

        ViewGroup actionbarContainer = mToolbar.findViewById(R.id.vg_toolbar);
        View.inflate(this, getToolBarLayoutId(), actionbarContainer);
        setSupportActionBar(mToolbar);

        setContentView(rootView);
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
        if (addStackManager()) {
            ScreenManager.getInstance().popActivity(this);
        }
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
     * 是否加入栈管理
     *
     * @return
     */
    protected boolean addStackManager() {
        return true;
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
     * ToolBar布局
     *
     * @return
     */
    public int getToolBarLayoutId() {
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
     * 屏幕点击事件 - 关闭键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (KeyboardUtils.isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(ProHelper.getScreenHelper().currentActivity());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    private LoadingDialog loadingDialog;

    /**
     * 展示加载进度条
     *
     * @param cancel 击外部是否可取消
     */
    public void showProgressBar(int content, boolean cancel) {
        loadingDialog = new LoadingDialog(mContext, cancel, getString(content));
        loadingDialog.show();
    }

    public void showProgressBar(boolean cancel) {
        showProgressBar(R.string.loading_public, cancel);
    }

    public void showProgressBar(){
        showProgressBar(R.string.loading_public, true);
    }

    /**
     * 关闭进度条
     */
    public void dismissProgressBar() {
        disDialog(loadingDialog);
    }

    /**
     * 关闭 dialog
     */
    public void disDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void openFragment(Bundle bundle, Fragment fragment) {
        openFragment(bundle, FragmentTransaction.TRANSIT_NONE, FragmentTransaction.TRANSIT_NONE, fragment);
    }

    public void openFragment(int enterAnimation, int exitAnimation, Fragment fragment) {
        openFragment(null, enterAnimation, exitAnimation, fragment);
    }

    public void openFragment(Bundle bundle, int enterAnimation, int exitAnimation, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.setCustomAnimations(enterAnimation, exitAnimation);
        transaction.add(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}