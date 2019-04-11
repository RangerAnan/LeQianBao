package com.nxin.base.view.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxin.base.R;


/**
 * Created by wzy on 2016/12/7
 */
public class CommonEmptyView extends LinearLayout {

    private LayoutInflater inflater;

    private RelativeLayout mEmptyView;
    private ProgressBar mLoadingProgress;
    private ImageView mEmptyImage;
    private TextView mEmptyContent;

    private OnEmptyViewClickListener onClickListener;

    public CommonEmptyView(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        createView();
    }

    public CommonEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        createView();
    }

    public void setOnClickListener(OnEmptyViewClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void createView() {
        View rootView = inflater.inflate(R.layout.include_empty_view, this);
        initViews(rootView);
        setListener();
    }

    private void initViews(View rootView) {
        mEmptyView = rootView.findViewById(R.id.empty_view);
        mLoadingProgress = rootView.findViewById(R.id.loading_progress);
        mEmptyImage = rootView.findViewById(R.id.empty_image);
        mEmptyContent = rootView.findViewById(R.id.empty_content);
    }

    private void setListener() {
        mEmptyView.setEnabled(false);
        mEmptyView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickCallback();
                }
            }
        });
    }

    public void dealRequestData() {
        mEmptyView.setEnabled(false);
        if (mLoadingProgress.getVisibility() == View.VISIBLE) {
            return;
        }
        mLoadingProgress.setVisibility(View.VISIBLE);
        mEmptyImage.setVisibility(View.GONE);
        mEmptyContent.setText(R.string.loading);
    }

    public void dealRequestDataEmpty(String emptyContent) {
        mLoadingProgress.setVisibility(View.GONE);
        mEmptyImage.setImageResource(R.drawable.image_data_empty);
        mEmptyImage.setVisibility(View.VISIBLE);
        mEmptyContent.setText(emptyContent);
        mEmptyView.setEnabled(false);
    }

    public void dealRequestDataFail() {
        mLoadingProgress.setVisibility(View.GONE);
        mEmptyImage.setImageResource(R.drawable.image_loading_fail);
        mEmptyImage.setVisibility(View.VISIBLE);
        mEmptyContent.setText(R.string.click_to_refresh);
        mEmptyView.setEnabled(true);
    }

    public void dealRequestDataFail(String desc) {
        mLoadingProgress.setVisibility(View.GONE);
        mEmptyImage.setImageResource(R.drawable.image_loading_fail);
        mEmptyImage.setVisibility(View.VISIBLE);
        mEmptyContent.setText(desc);
        mEmptyView.setEnabled(true);
    }

    public void setContentViewCenter() {
        mEmptyView.setPadding(0, 0, 0, 0);
    }

    public interface OnEmptyViewClickListener {
        void onClickCallback();
    }

}
