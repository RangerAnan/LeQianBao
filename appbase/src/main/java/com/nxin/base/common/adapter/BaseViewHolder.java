package com.nxin.base.common.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.nxin.base.utils.ProHelper;

/**
 * viewHolder类
 * Created by fcl on 2017/8/28
 */
public class BaseViewHolder {

    private View mConvertView;
    private SparseArray<View> mViews;

    private BaseViewHolder(int layoutId) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(ProHelper.getApplication()).inflate(layoutId, null);
        mConvertView.setTag(this);
    }

    public static BaseViewHolder get(View convertView, int layoutId) {
        if (convertView == null) {
            return new BaseViewHolder(layoutId);
        } else {
            return (BaseViewHolder) convertView.getTag();
        }
    }

    //获取控件
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
