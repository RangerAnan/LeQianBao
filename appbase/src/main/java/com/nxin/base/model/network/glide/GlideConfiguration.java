package com.nxin.base.model.network.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.nxin.base.R;
import com.nxin.base.model.BaseConstant;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Glide图片框架的配置
 * Created by wzy on 2017/4/24
 */
public class GlideConfiguration implements GlideModule {

    int diskCacheSize = 300 * 1024 * 1024; //最多可以缓存多少字节的数据

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(BaseConstant.SAVE_GLIDE_IMAGE, "glidecache", diskCacheSize));
        //全局设置tagId
        ViewTarget.setTagId(R.id.glide_tag_id);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // 设置长时间读取和断线重连
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.MINUTES).readTimeout(15, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true).build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
