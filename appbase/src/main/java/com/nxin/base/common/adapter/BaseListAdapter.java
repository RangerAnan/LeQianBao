package com.nxin.base.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * adapter基类
 * Created by fcl on 2017/8/28
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    public List<T> dataList;
    public IAdapterClickListener listener;

    public BaseListAdapter(List<T> dataList) {
        this.dataList = dataList;
    }

    //更新数据源
    public void setListData(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.get(convertView, getLayout());
        T model = dataList.get(position);
        if (model != null) {
            setData(holder, model, position);
        }
        return holder.getConvertView();
    }

    //获取布局
    public abstract int getLayout();

    //展示数据
    public abstract void setData(BaseViewHolder holder, T t, int position);


    public void setOnAdapterClickListener(IAdapterClickListener listener) {
        this.listener = listener;
    }

    public String initTag() {
        return this.getClass().getSimpleName();
    }
}