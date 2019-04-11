package com.nxin.base.model.network.glide;

/**
 * 图片加载回调
 * Created by wzy on 2017/4/18
 */
public interface ImageLoaderListener<T> {

    void onLoadComplete(T data);

}
