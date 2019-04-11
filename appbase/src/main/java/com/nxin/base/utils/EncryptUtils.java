package com.nxin.base.utils;

import android.util.Base64;

/**
 * Created by fcl on 18.10.11
 * desc: 加密工具类
 */

public class EncryptUtils {

    //根据android自带的base64编码
    public static String EncodeBase64String(String value) {
        String strreturn = "";
        try {
            if (value.equals(""))
                return "";
            strreturn = android.util.Base64.encodeToString(value.getBytes(), Base64.DEFAULT).replaceAll("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strreturn;
    }

    //根据android自带的base64解码
    public static String DeCodeBase64String(String value) {
        String strreturn = "";
        try {
            if ("".equals(value))
                return "";
            byte b[] = android.util.Base64.decode(value, Base64.DEFAULT);
            strreturn = new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strreturn;
    }

    /**
     * base64验证
     */
    public static boolean isBase64Data(String value) {
        String DeCodeBase64absolutePath = DeCodeBase64String(value);
//        log.i("isBase64Data.DeCodeBase64absolutePath = " + DeCodeBase64absolutePath);

        String EnCodeBase64absolutePath = EncodeBase64String(DeCodeBase64absolutePath);
//        MyLogUtil.i("isBase64Data.EnCodeBase64absolutePath = " + EnCodeBase64absolutePath);

        return value.equals(EnCodeBase64absolutePath);
    }
}
