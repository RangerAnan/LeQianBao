package com.nxin.base.model.network.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.nxin.base.R;
import com.nxin.base.model.network.glide.transformation.GlideCircleTransform;
import com.nxin.base.model.network.glide.transformation.GlideRoundTransform;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

import java.io.File;

/**
 * 图片加载工具类
 * Created by wzy on 2017/4/17
 * Notice：优先选择传入RequestManager，绑定Activity或Fragment生命周期
 */
public class GlideUtils {

    //全局默认图片,glide方法中不用传递
    private static int DEFAULT_IMAGE_NORMAL = R.drawable.image_defalut;

    public static void loadImageNoPlaceholder(String url, ImageView imageView) {
        loadImage(imageView.getContext(), url, imageView);
    }

    public static void loadImageNoPlaceholder(String url, int width, int height, ImageView imageView) {
        loadImage(imageView.getContext(), url, width, height, imageView);
    }

    public static void loadImage(String url, ImageView imageView) {
        loadImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, imageView);
    }

    public static void loadImage(String url, int placeholder, ImageView imageView) {
        loadImage(imageView.getContext(), url, placeholder, imageView);
    }

    public static void loadImage(String url, int placeholder, ImageView imageView, Activity activity) {
        loadImage(activity, url, placeholder, imageView);
    }

    public static void loadImage(String url, int width, int height, ImageView imageView) {
        loadImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, width, height, imageView);
    }

    public static void loadImage(String url, int placeholder, int width, int height, ImageView imageView) {
        loadImage(imageView.getContext(), url, placeholder, width, height, imageView);
    }

    public static void loadCircleImage(String url, ImageView imageView) {
        loadCircleImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, imageView);
    }

    public static void loadCircleImage(String url, int placeholder, ImageView imageView) {
        loadCircleImage(imageView.getContext(), url, placeholder, imageView);
    }

    public static void loadCircleImage(String url, int width, int height, ImageView imageView) {
        loadCircleImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, width, height, imageView);
    }

    public static void loadCircleImage(String url, int placeholder, int width, int height, ImageView imageView) {
        loadCircleImage(imageView.getContext(), url, placeholder, width, height, imageView);
    }

    public static void loadRoundImage(String url, int radiusDpValue, ImageView imageView) {
        loadRoundImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, radiusDpValue, imageView);
    }

    public static void loadRoundImage(int url, int radiusDpValue, int width, int height, ImageView imageView) {
        loadRoundImage(imageView.getContext(), url, radiusDpValue, width, height, imageView);
    }

    public static void loadRoundImage(String url, int placeholder, int radiusDpValue, ImageView imageView) {
        loadRoundImage(imageView.getContext(), url, placeholder, radiusDpValue, imageView);
    }

    public static void loadRoundImage(String url, int radiusDpValue, int width, int height, ImageView imageView) {
        loadRoundImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, radiusDpValue, width, height, imageView);
    }

    public static void loadRoundImage(String url, int placeholder, int radiusDpValue, int width, int height, ImageView imageView) {
        loadRoundImage(imageView.getContext(), url, placeholder, radiusDpValue, width, height, imageView);
    }

    public static void loadGifImage(String url, ImageView imageView) {
        loadGifImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, imageView);
    }

    public static void loadGifImage(String url, int placeholder, ImageView imageView) {
        loadGifImage(imageView.getContext(), url, placeholder, imageView);
    }

    public static void loadGifImage(int drawable, int placeholder, ImageView imageView) {
        loadGifImage(imageView.getContext(), drawable, placeholder, imageView, DiskCacheStrategy.SOURCE);
    }

    public static void loadGifImageNoCache(int drawable, int placeholder, ImageView imageView) {
        loadGifImage(imageView.getContext(), drawable, placeholder, imageView, DiskCacheStrategy.NONE);
    }

    public static void loadGifImage(String url, int width, int height, ImageView imageView) {
        loadGifImage(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, width, height, imageView);
    }

    public static void loadGifImage(String url, int placeholder, int width, int height, ImageView imageView) {
        loadGifImage(imageView.getContext(), url, placeholder, width, height, imageView);
    }

    public static void loadImageWithCenterCrop(String url, ImageView imageView) {
        loadImageWithCenterCrop(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, imageView);
    }

    public static void loadImageWithCenterCrop(String url, int placeholder, ImageView imageView) {
        loadImageWithCenterCrop(imageView.getContext(), url, placeholder, imageView);
    }

    public static void loadImageWithCenterCrop(String url, int width, int height, ImageView imageView) {
        loadImageWithCenterCrop(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, width, height, imageView);
    }

    public static void loadImageWithCenterCrop(String url, int placeholder, int width, int height, ImageView imageView) {
        loadImageWithCenterCrop(imageView.getContext(), url, placeholder, width, height, imageView);
    }

    public static void loadRoundImageWithCenterCrop(String url, int radiusDpValue, ImageView imageView) {
        loadRoundImageWithCenterCrop(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, radiusDpValue, imageView);
    }

    public static void loadRoundImageWithCenterCrop(String url, int placeholder, int radiusDpValue, ImageView imageView) {
        loadRoundImageWithCenterCrop(imageView.getContext(), url, placeholder, radiusDpValue, imageView);
    }

    public static void loadRoundImageWithCenterCrop(String url, int radiusDpValue, int width, int height, ImageView imageView) {
        loadRoundImageWithCenterCrop(imageView.getContext(), url, DEFAULT_IMAGE_NORMAL, radiusDpValue, width, height, imageView);
    }

    public static void loadRoundImageWithCenterCrop(String url, int placeholder, int radiusDpValue, int width, int height, ImageView imageView) {
        loadRoundImageWithCenterCrop(imageView.getContext(), url, placeholder, radiusDpValue, width, height, imageView);
    }

    public static void loadImageWithPath(String path, ImageView imageView) {
        loadImageWithPath(imageView.getContext(), path, DEFAULT_IMAGE_NORMAL, imageView);
    }

    public static void loadImageWithPath(String path, int placeholder, ImageView imageView) {
        loadImageWithPath(imageView.getContext(), path, placeholder, imageView);
    }

    public static void loadImageWithPath(String path, int width, int height, ImageView imageView) {
        loadImageWithPath(imageView.getContext(), path, DEFAULT_IMAGE_NORMAL, width, height, imageView);
    }

    public static void loadImageWithPath(String path, int placeholder, int width, int height, ImageView imageView) {
        loadImageWithPath(imageView.getContext(), path, placeholder, width, height, imageView);
    }

    public static void loadImageWithPathAndCenterCrop(String path, ImageView imageView) {
        loadImageWithPathAndCenterCrop(imageView.getContext(), path, DEFAULT_IMAGE_NORMAL, imageView);
    }

    public static void loadImageWithPathAndCenterCrop(String path, int placeholder, ImageView imageView) {
        loadImageWithPathAndCenterCrop(imageView.getContext(), path, placeholder, imageView);
    }

    public static void loadImageWithPathAndCenterCrop(String path, int width, int height, ImageView imageView) {
        loadImageWithPathAndCenterCrop(imageView.getContext(), path, DEFAULT_IMAGE_NORMAL, width, height, imageView);
    }

    public static void loadImageWithPathAndCenterCrop(String path, int placeholder, int width, int height, ImageView imageView) {
        loadImageWithPathAndCenterCrop(imageView.getContext(), path, placeholder, width, height, imageView);
    }

    /**
     * ========================= 设置DrawableRequestBuilder ===================
     */
    public static DrawableRequestBuilder setDrawableRequest(RequestManager requestManager, String url) {
        return requestManager.load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener);
    }

    public static DrawableRequestBuilder setDrawableRequest(RequestManager requestManager, String url, int placeholder) {
        return setDrawableRequest(requestManager, url).placeholder(placeholder).error(placeholder);
    }

    public static DrawableRequestBuilder setDrawableRequest(RequestManager requestManager, String url, int placeholder, int width, int height) {
        return setDrawableRequest(requestManager, url, placeholder).override(width, height);
    }

    public static DrawableRequestBuilder setCircleDrawableRequest(RequestManager requestManager, String url) {
        return setDrawableRequest(requestManager, url).transform(new GlideCircleTransform(ProHelper.getApplication()));
    }

    public static DrawableRequestBuilder setCircleDrawableRequest(RequestManager requestManager, String url, int placeholder) {
        return setCircleDrawableRequest(requestManager, url).placeholder(placeholder).error(placeholder);
    }

    public static DrawableRequestBuilder setCircleDrawableRequest(RequestManager requestManager, String url, int placeholder, int width, int height) {
        return setCircleDrawableRequest(requestManager, url, placeholder).override(width, height);
    }

    public static DrawableRequestBuilder setRoundDrawableRequest(RequestManager requestManager, String url, int radiusDpValue) {
        return setDrawableRequest(requestManager, url).transform(new GlideRoundTransform(ProHelper.getApplication(), radiusDpValue));
    }

    public static DrawableRequestBuilder setRoundDrawableRequest(RequestManager requestManager, String url, int placeholder, int radiusDpValue) {
        return setRoundDrawableRequest(requestManager, url, radiusDpValue).placeholder(placeholder).error(placeholder);
    }

    public static DrawableRequestBuilder setRoundDrawableRequest(RequestManager requestManager, String url, int placeholder, int radiusDpValue, int width, int height) {
        return setRoundDrawableRequest(requestManager, url, placeholder, radiusDpValue).override(width, height);
    }


    /**
     * ======================== 私有方法 =========================
     */
    private static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImage(Context context, String url, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImage(Context context, String url, int placeholder, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImage(Context context, String url, int placeholder, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImageWithCenterCrop(Context context, String url, int placeholder, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImageWithCenterCrop(Context context, String url, int placeholder, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .override(width, height)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadCircleImage(Context context, String url, int placeholder, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadCircleImage(Context context, String url, int placeholder, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new GlideCircleTransform(context))
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadRoundImage(Context context, String url, int placeholder, int radiusDpValue, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new GlideRoundTransform(context, radiusDpValue))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadRoundImage(Context context, String url, int placeholder, int radiusDpValue, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new GlideRoundTransform(context, radiusDpValue))
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadRoundImage(Context context, int url, int radiusDpValue, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .transform(new GlideRoundTransform(context, radiusDpValue))
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private static void loadRoundImageWithCenterCrop(Context context, String url, int placeholder, int radiusDpValue, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new GlideRoundTransform(context, radiusDpValue))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadRoundImageWithCenterCrop(Context context, String url, int placeholder, int radiusDpValue, int width, int height, ImageView imageView) {
        Glide.with(context).load(url)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .transform(new GlideRoundTransform(context, radiusDpValue))
                .override(width, height)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadGifImage(Context context, String url, int placeholder, ImageView imageView) {
        Glide.with(context).load(url).asGif()
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestGifImageListener)
                .into(imageView);
    }

    private static void loadGifImage(Context context, int drawable, int placeholder, ImageView imageView, DiskCacheStrategy cacheStrategy) {
        Glide.with(context).load(drawable).asGif()
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(cacheStrategy)
                .into(imageView);
    }

    private static void loadGifImage(Context context, String url, int placeholder, int width, int height, ImageView imageView) {
        Glide.with(context).load(url).asGif()
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .override(width, height)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestGifImageListener)
                .into(imageView);
    }

    private static void loadImageWithPath(Context context, String filePath, int placeholder, ImageView imageView) {
        Glide.with(context).load("file:///" + filePath)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImageWithPath(Context context, String filePath, int placeholder, int width, int height, ImageView imageView) {
        Glide.with(context).load("file:///" + filePath)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImageWithPathAndCenterCrop(Context context, String filePath, int placeholder, ImageView imageView) {
        Glide.with(context).load("file:///" + filePath)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(requestImageListener)
                .into(imageView);
    }

    private static void loadImageWithPathAndCenterCrop(Context context, String filePath, int placeholder, int width, int height, ImageView imageView) {
        Glide.with(context).load("file:///" + filePath)
                .dontAnimate()
                .placeholder(placeholder)
                .error(placeholder)
                .override(width, height)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(requestImageListener)
                .into(imageView);
    }

    public static void loadImageWithTarget(Context context, String url, SimpleTarget target) {
        Glide.with(context).load(url)
                .asBitmap()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(target);
    }

    public static void loadImageWithTarget(Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .asBitmap()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static void loadImageToBitmap(final Context context, final String url, final ImageLoaderListener<Bitmap> listener) {
        new AsyncTask<String, Integer, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context).load(url)
                            .asBitmap()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                listener.onLoadComplete(bitmap);
            }
        }.execute();
    }

    public static void loadImageToCacheFile(final Context context, final String url, final ImageLoaderListener<String> listener) {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                File cacheFile = null;
                try {
                    cacheFile = Glide.with(context).load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cacheFile == null || !cacheFile.exists()) {
                    return "";
                }
                return cacheFile.getAbsolutePath();
            }

            @Override
            protected void onPostExecute(String cachePath) {
                listener.onLoadComplete(cachePath);
            }
        }.execute();
    }

    private static RequestListener<String, GlideDrawable> requestImageListener = new RequestListener<String, GlideDrawable>() {

        @Override
        public boolean onException(Exception e, String url, Target<GlideDrawable> target, boolean isFirstResource) {
            Logger.e(GlideUtils.class.getSimpleName() + ":" + url + ";" + (e == null ? "" : e.toString()));
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String url, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    private static RequestListener<String, GifDrawable> requestGifImageListener = new RequestListener<String, GifDrawable>() {

        @Override
        public boolean onException(Exception e, String url, Target<GifDrawable> target, boolean isFirstResource) {
            Logger.e(GlideUtils.class.getSimpleName() + ":" + url + (e == null ? "" : e.toString()));
            return false;
        }

        @Override
        public boolean onResourceReady(GifDrawable resource, String url, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    public static void clearImageMemoryCache(Context context) { //只能在主线程执行
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearImageDiskCache(final Context context) { //只能在子线程执行
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
