package com.leqian.bao.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.code.RequestCode;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by fcl on 19.4.13
 * desc:
 */
public class ImageUtil {


    //放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static File saveBitmapFile(Bitmap bitmap, String fileName) throws Exception {
        if (!TextUtils.isEmpty(fileName)) {
            throw new Exception("文件名称不能为空");
        }
        File file = new File(fileName);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

            com.leqian.bao.common.util.FileUtil.noticeMediaScanner(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取网落图片资源
     */
    public static Bitmap getHttpSyncBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(4000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getCutImageUploadPath() {
        String pictureDir = "";
        String filename = "";
        float randomNumber = (float) (Math.random() * 100);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        filename = dateFormat.format(date) + "_" + System.currentTimeMillis() + "_" + randomNumber + "_talk" + ".jpg";
        String saveDir = Constants.TALK_IMAGE_PATH;
        pictureDir = saveDir + filename;
        return pictureDir;
    }

    /**
     * 创建图片路径:sd/nxin/znt/image/temp_xx.jpg
     */
    public static String getCutImageTempPath() {
        String pictureDir = "";
        String filename = "";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'temp'_yyyyMMdd_HHmmss");
        filename = dateFormat.format(date) + "_" + System.currentTimeMillis() + "" + ".jpg";
        String saveDir = Constants.TALK_IMAGE_PATH;
        pictureDir = saveDir + filename;
        return pictureDir;
    }

    //保存封面图片的路径
    public static String getCoverImagePath() {
        String saveDir = Constants.TALK_IMAGE_PATH;
        String filename = "cover_image.png";
        return saveDir + filename;
    }

    //保存语音合成的路径
    public static String getVoicePath() {
        String saveDir = Constants.SAVE_VOICE_PATH;
        String filename = "tts.wav";
        return saveDir + filename;
    }

    /**
     * ------------------------------------------------start-----------------------------------------
     **/
    //获取拍照或从图库选择图片后图片的真实路径(适用所有机型,解决Android4.4以上版本Uri转换)
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static synchronized String getImageAbsolutePath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * 适用于view已经显示在界面上
     */
    public static Bitmap getViewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
//        view.setDrawingCacheBackgroundColor(Color.WHITE);//设置绘制缓存背景颜色
        view.buildDrawingCache();//启用DrawingCache并创建位图
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }


    /**
     * 修改bitmap的颜色值
     */
    public static Bitmap changeBitmapColor(float[] matrix, Bitmap resource) {

        Bitmap bitmap = Bitmap.createBitmap(resource.getWidth(), resource.getHeight(), resource.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        //定义ColorMatrix，并指定RGBA矩阵
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(matrix);
        //设置画笔颜色
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(resource, new Matrix(), paint);

        return bitmap;
    }

    /**
     * 修改drawable的颜色值
     */
    public static Bitmap changeBitmapColor(float[] matrix, int drawable) {

        Bitmap resource = BitmapFactory.decodeResource(ProHelper.getApplication().getResources(), drawable);
        return changeBitmapColor(matrix, resource);
    }

    public static void saveBase64ImageToFile(String base64Code, String savePath) {
        try {
            byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
            FileOutputStream out = new FileOutputStream(new File(savePath));
            BufferedOutputStream outputStream = new BufferedOutputStream(out);
            outputStream.write(buffer);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String CROP_TEMP_IMAGE_NAME = "crop_image.jpg";
    public static void cropImage(Context context, Uri uri) {
        try {
            if (uri != null) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
                    intent.setDataAndType(uri, "image/*");
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
                    String path = ImageUtil.getImageAbsolutePath(context, uri);
                    if (!TextUtils.isEmpty(path)) {
                        File file = new File(path);
                        if (file.isFile() && file.exists()) {
                            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
                        }
                    }
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    String path = ImageUtil.getImageAbsolutePath(context, uri);
                    intent.setDataAndType(DeviceUtil.getUriForFile(context, new File(path)), "image/*");
                }
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 600);
                intent.putExtra("outputY", 600);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH + CROP_TEMP_IMAGE_NAME)));
                intent.putExtra("return-data", false);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true); // no face detection
                ((Activity) context).startActivityForResult(intent, RequestCode.CHOICE_MEDIA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //以屏幕宽高适配图片
    public static Bitmap scaleImage(String imagePath) {
        BitmapFactory.Options factory = new BitmapFactory.Options();
        factory.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, factory);

        float wRatio = (float) factory.outWidth / DeviceUtil.getScreenWidth();
        float hRatio = (float) factory.outHeight / DeviceUtil.getScreenHeight();
        if (wRatio > 1.0000f || hRatio > 1.0000f) {
            if (hRatio > wRatio) {
                factory.inSampleSize = (int) hRatio;
            } else {
                factory.inSampleSize = (int) wRatio;
            }
        }

        factory.inJustDecodeBounds = false;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, factory);
            return rotateBitmap(readPictureDegree(imagePath), bitmap);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            Runtime.getRuntime().gc();
        }
        return rotateBitmap(readPictureDegree(imagePath), BitmapFactory.decodeFile(imagePath));
    }

    /**
     * 将图片纠正到正确方向
     *
     * @param degree ： 图片被系统旋转的角度
     * @param bitmap ： 需纠正方向的图片
     * @return 纠向后的图片
     */
    public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        if (bitmap == null || degree == 0) return bitmap;
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bm != bitmap && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    /**
     * degree
     */
    public static int readPictureDegree(String imgPath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(imgPath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.i("ImageCutUtil---readPictureDegree--degree:" + degree);
        return degree;
    }


}
