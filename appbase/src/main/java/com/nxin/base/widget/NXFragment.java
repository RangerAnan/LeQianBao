package com.nxin.base.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import com.nxin.base.R;
import com.nxin.base.view.dialog.LoadingDialog;
import com.nxin.base.view.loading.CommonEmptyView;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by fcl on 18.4.24
 * desc:
 */

public abstract class NXFragment extends Fragment {

    protected View view;
    protected FragmentActivity mContext;
    protected LayoutInflater inflater;

    private ViewAnimator mViewAnimator;
    private LinearLayout headContainer;                      //头部视图容器

    private String emptyDesc;                                //空状态


    /**
     * 是否执行了initData方法
     */
    protected boolean isLoaded;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        inflater = LayoutInflater.from(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            initLayout(container);
            ButterKnife.bind(this, view);
            initView();
            onVisible();
        }
        breakParent();
        return view;
    }

    private void initLayout(ViewGroup container) {
        if (isOpenViewState()) {
            view = inflater.inflate(R.layout.activity_view, container, false);
            mViewAnimator = view.findViewById(R.id.home);
            headContainer = view.findViewById(R.id.head_container);
            addHeaderView(headContainer);
            // 加载布局-初始化:将rootView附加到布局文件的根视图上
            inflater.inflate(loadingViewLayout(), mViewAnimator, true);
            // 内容布局-初始化
            inflater.inflate(getLayoutId(), mViewAnimator, true);
            // 空布局-初始化
            inflater.inflate(emptyViewLayout(), mViewAnimator, true);
            // 错误布局-初始化
            inflater.inflate(errorViewLayout(), mViewAnimator, true);
        } else {
            view = inflater.inflate(getLayoutId(), container, false);
        }
    }


    /**
     * 初始化view
     */
    public void initView() {

    }

    /**
     * 初始化Data
     */
    public void initData() {

    }

    protected <T extends View> T findViewById(int resId) {
        T v = null;
        if (view != null) {
            v = view.findViewById(resId);
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        breakParent();
    }

    protected void breakParent() {
        if (view == null) {
            return;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String FRAGMENTS_TAG = "Android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onActivityCreated(savedInstanceState);
        if (isOpenEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(initTag(), "---onDestroy--");
        if (isOpenEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public String initTag() {
        return getClass().getSimpleName();
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
            CommonEmptyView commonEmptyView = (CommonEmptyView) findViewById(R.id.common_error_view);
            commonEmptyView.dealRequestDataFail();
            commonEmptyView.setOnClickListener(new CommonEmptyView.OnEmptyViewClickListener() {
                @Override
                public void onClickCallback() {
                    showLoadingView();
                    initData();
                }
            });
//            mViewAnimator.getCurrentView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    initData();
//                }
//            });
        } else if (showState == 2) {
            //空页面描述
            CommonEmptyView commonEmptyView = (CommonEmptyView) findViewById(R.id.common_empty_view);
            commonEmptyView.dealRequestDataEmpty(emptyDesc);
        }
    }

    public void showEmptyView(String desc) {
        emptyDesc = desc;
        show(2);
    }

    public void showErrorView() {
        show(3);
    }

    public void showLoadingView() {
        show(0);
    }

    public void showContentView() {
        show(1);
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
     * 此方法在viewpager嵌套fragment时会回调
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        }
    }

    protected void onVisible() {
        if (isLoaded || view == null) {
            return;
        }
        if (!isDelayLoad()) {
            isLoaded = true;
            initData();
            return;
        }
        if (getUserVisibleHint()) {
            isLoaded = true;
            initData();
        }
    }

    /**
     * 是否延迟加载
     *
     * @return
     */
    protected boolean isDelayLoad() {
        return false;
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

}
