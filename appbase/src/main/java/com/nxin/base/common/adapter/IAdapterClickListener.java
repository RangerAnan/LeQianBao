package com.nxin.base.common.adapter;

/**
 * 适配器点击事件回调接口
 * Created by fcl on 2017/8/28
 */
public interface IAdapterClickListener<T> {

    void onAdapterClick(int position, T t);
}