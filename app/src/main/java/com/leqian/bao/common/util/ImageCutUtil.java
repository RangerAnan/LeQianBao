package com.leqian.bao.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;


import com.nxin.base.utils.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ronnie on 2016/5/17
 */
public class ImageCutUtil {
    /**
     * @param srcPath 原图路径
     * @param resPath 结果路径
     */
    public static boolean cut(String srcPath, String resPath) {
        int minWidth = 750; // SCREEN_WIDTH
        int minHeight = 1334; // SCREEN_HEIGHT
        return cut(srcPath, resPath, minWidth, minHeight);
    }

    /**
     * @param srcPath 原图路径
     * @param resPath 结果路径
     */
    public static boolean cut(String srcPath, String resPath, int minWidth, int minHeight) {
        boolean flag = false;
        if (TextUtils.isEmpty(srcPath) || !new File(srcPath).exists()) {
            return flag;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
//        MyLogUtil.i("ImageCutUtil--options.outHeight:" + options.outHeight + ";options.outWidth:" + options.outWidth + ";minHeight:" + minHeight + ";minWidth:" + minWidth);
        // 是否需要压缩
        if (options.outHeight > options.outWidth) {
            // 竖图
            if (options.outHeight <= minHeight && options.outWidth <= minWidth) {
                return copyFile(srcPath, resPath);
            }
        } else {
            // 横图
            if (options.outHeight <= minWidth && options.outWidth <= minHeight) {
                return copyFile(srcPath, resPath);
            }
        }

        if (options.outHeight > options.outWidth) {
            // 竖图
            options.inSampleSize = calculateInSampleSize(options, minWidth, minHeight);
        } else {
            // 横图
            options.inSampleSize = calculateInSampleSize(options, minHeight, minWidth);
        }

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = false;

        Bitmap srcBitmap = null;
        try {
            srcBitmap = BitmapFactory.decodeFile(srcPath, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (srcBitmap != null) {
                srcBitmap.recycle();
            }
            return flag;
        }
        if (srcBitmap == null) {
            return flag;
        }

        // 计算压缩比例和旋转
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth;
        float scaleHeight;
        if (options.outHeight > options.outWidth) {
            // 竖图
            scaleWidth = ((float) minWidth / srcWidth);
            scaleHeight = ((float) minHeight / srcHeight);
        } else {
            // 横图
            scaleWidth = ((float) minWidth / srcHeight);
            scaleHeight = ((float) minHeight / srcWidth);
        }

        // 等比取大的
        float scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
        matrix.postScale(scale, scale);
        int degree = readPictureDegree(srcPath);
        if (degree != 0) {
            matrix.postRotate(degree);
        }

        Bitmap resBitmap = null;
        try {
            resBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth, srcHeight, matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (resBitmap != null) {
                resBitmap.recycle();
            }
            return flag;
        }
        boolean isSave = saveImage(resBitmap, resPath);
        srcBitmap.recycle();
        return isSave;
    }

    /**
     * @param bitmap
     * @param toPath
     * @return
     */
    private static boolean saveImage(Bitmap bitmap, String toPath) {
        boolean flag = false;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Logger.i("toPath============" + toPath);
        try {
            File tofile = new File(toPath);
            File parent = tofile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!tofile.exists()) {
                tofile.createNewFile();
            }
            fos = new FileOutputStream(toPath);
            bos = new BufferedOutputStream(fos);
            flag = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        return flag;
    }

    public static boolean saveCropImage(Bitmap bitmap, String toPath) {
        boolean flag = false;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Logger.i("toPath============" + toPath);
        try {
            File tofile = new File(toPath);
            File parent = tofile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!tofile.exists()) {
                tofile.createNewFile();
            }
            fos = new FileOutputStream(toPath);
            bos = new BufferedOutputStream(fos);
            flag = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap = null;
            }
        }
        return flag;
    }

    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
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
     * 将图片纠正到正确方向
     */
    public static String rotateImage(String imagePath) {

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        int degree = readPictureDegree(imagePath);

        if (bitmap == null || degree == 0) {
            return imagePath;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bm != bitmap && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            String rightPath = ImageUtil.getCutImageTempPath();
            boolean result = saveImage(bm, rightPath);
            return result ? rightPath : imagePath;
        } catch (Exception e) {
            e.printStackTrace();
            return imagePath;
        }
    }

    /**
     * 文件复制
     */
    public static boolean copyFile(String fromPath, String toPath) {
        boolean flag = false;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            File fromfile = new File(fromPath);
            if (fromfile.exists()) {
                inStream = new FileInputStream(fromPath);
                File tofile = new File(toPath);
                File parent = tofile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                if (!tofile.exists()) {
                    tofile.createNewFile();
                }
                outStream = new FileOutputStream(toPath);
                byte[] buffer = new byte[1024];
                int byteread = 0;
                while ((byteread = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, byteread);
                }
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 拍照后旋转图片
     * <p>
     * rate宽高比
     */
    public static boolean rotateImageFile(double rate, String srcPath, String resPath) {
        int minWidth = 750; // SCREEN_WIDTH
        int minHeight = 1134; // SCREEN_HEIGHT

        boolean flag = false;
        if (srcPath == null || "".equals(srcPath) || !new File(srcPath).exists()) {
            return flag;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        Logger.i("rotateImageFile--options.outHeight:" + options.outHeight + "---options.outWidth:" + options.outWidth);
        // 是否需要压缩
        if (options.outHeight > options.outWidth) {
            // 竖图
            if (options.outHeight <= minHeight && options.outWidth <= minWidth) {
                return copyFile(srcPath, resPath);
            }
        } else {
            // 横图
            if (options.outHeight <= minWidth && options.outWidth <= minHeight) {
                return copyFile(srcPath, resPath);
            }
        }

        if (options.outHeight > options.outWidth) {
            // 竖图
            options.inSampleSize = calculateInSampleSize(options, minWidth, minHeight);
        } else {
            // 横图
            options.inSampleSize = calculateInSampleSize(options, minHeight, minWidth);
        }

        Logger.i("rotateImageFile--inSampleSize:" + options.inSampleSize + "---rate:" + rate);

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = false;

        Bitmap srcBitmap = null;
        try {
            srcBitmap = BitmapFactory.decodeFile(srcPath, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (srcBitmap != null) {
                srcBitmap.recycle();
            }
            return flag;
        }
        if (srcBitmap == null) {
            return flag;
        }

        // 计算压缩比例和旋转
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth;
        float scaleHeight;
        if (options.outHeight > options.outWidth) {
            // 竖图
            scaleWidth = ((float) minWidth / srcWidth);
            scaleHeight = ((float) minHeight / srcHeight);
        } else {
            // 横图
            scaleWidth = ((float) minWidth / srcHeight);
            scaleHeight = ((float) minHeight / srcWidth);
        }

        // 等比取大的
        float scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
        matrix.postScale(scale, scale);
        int degree = 0;

        //设置旋转角度
        if (rate > 1) {     //横着展示
            if (options.outHeight > options.outWidth) {
                // 竖图
                degree = 90;
            }
        } else {    //竖着展示
            if (options.outHeight < options.outWidth) {
                // 横图
                degree = 90;
            }
        }
        if (degree != 0) {
            matrix.postRotate(degree);
        }

        Bitmap resBitmap = null;
        try {
            resBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth, srcHeight, matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (resBitmap != null) {
                resBitmap.recycle();
            }
            return flag;
        }
        boolean isSave = saveImage(resBitmap, resPath);
        srcBitmap.recycle();
        return isSave;
    }

}
