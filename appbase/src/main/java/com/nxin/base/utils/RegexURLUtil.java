package com.nxin.base.utils;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuhua
 * mail:mail.nxin.com
 * date: 2016/7/11.
 */
public class RegexURLUtil {

    private static String tag = "RegexURLUtil";

    public static final String REGEX_URL4 = "(https?://)?((?:(\\w+-)*\\w+)\\.)+(?:com|org|net|edu|gov|biz|info|name|museum|[a-z]{2})(\\/?\\w?-?=?_?\\??&?)+[\\.]?[a-z0-9\\?=&_\\-%#]*";

    public static final Pattern IP_ADDRESS = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");
    private static final String URL_NXIN_COM = "nxin.com";
    private static final String URL_NXIN_CN = "nxin.cn";
    private static final String URL_DBN_COM_CN = "dbn.com.cn";
    private static final String URL_DBN_CN = "dbn.cn";
    private static final String URL_AWEB_COM_CN = "aweb.com.cn";
    private static final String URL_GJSZSC = "gjszsc.com";
    private static final String URL_WWW_GJSZSC = "www.gjszsc.com";
    private static final String URL_CP_GJSZSC = "cp.gjszsc.com";
    private static final String URL_HNSC_GJSZSC = "hnsc.gjszsc.com";

    private static final String HIDE_TITLEBAR = "hideTitleBar=true";


    /**
     * 设置webView cookie的domain数组
     */
    public static final String[] WEB_COOKIE_DOMAIN = {".dbn.cn", ".dbn.com.cn", ".nxin.com", ".nxin.cn", ".gjszsc.com", ".aweb.com.cn"};


    public static boolean IsIP(String str) {
        List<String> ip_list = new ArrayList<String>();
        Matcher matcher = IP_ADDRESS.matcher(str);
        while (matcher.find()) {
            ip_list.add(matcher.group());
        }
        return ip_list.size() > 0;
    }

    public static boolean IsHttp(String str) {
        return str.toLowerCase().contains("http");
    }

    public static boolean IsUrl(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 判断链接里面是否包含公司域名
     *
     * @param url
     * @return
     */
    public static boolean isCompanyURl(String url) {
        boolean flag = false;
        if (url == null) {
            return flag;
        } else {
            url = url.toLowerCase();
        }

        url = getHostPrefix(url);
        Log.i(tag, "========getHostPrefix=======" + url);
        if (url.length() == 8 && url.equals(URL_NXIN_COM)) {
            Log.i(tag, URL_NXIN_COM);
            flag = true;
        } else if (url.length() == 7 && url.equals(URL_NXIN_CN)) {
            Log.i(tag, URL_NXIN_CN);
            flag = true;
        } else if (url.length() == 10 && url.equals(URL_DBN_COM_CN)) {
            Log.i(tag, URL_DBN_COM_CN);
            flag = true;
        } else if (url.length() == 6 && url.equals(URL_DBN_CN)) {
            Log.i(tag, URL_DBN_CN);
            flag = true;
        } else if (url.length() == 11 && url.equals(URL_AWEB_COM_CN)) {
            Log.i(tag, URL_AWEB_COM_CN);
            flag = true;
        } else if (url.length() == 10 && url.equals(URL_GJSZSC)) {
            Log.i(tag, URL_GJSZSC);
            flag = true;
        } else if (url.length() == 14 && url.equals(URL_WWW_GJSZSC)) {
            Log.i(tag, URL_WWW_GJSZSC);
            flag = true;
        } else if (url.length() == 13 && url.equals(URL_CP_GJSZSC)) {
            Log.i(tag, URL_CP_GJSZSC);
            flag = true;
        } else if (url.length() == 15 && url.equals(URL_HNSC_GJSZSC)) {
            Log.i(tag, URL_HNSC_GJSZSC);
            flag = true;
        } else if (url.endsWith(URL_NXIN_COM) || url.endsWith(URL_DBN_COM_CN) || url.endsWith(URL_DBN_CN) || url.endsWith(URL_NXIN_CN) || url.endsWith(URL_AWEB_COM_CN) || url.endsWith(URL_GJSZSC)) {
            Log.i(tag, "endsWith");
            flag = true;
        }
        return flag;
    }

    // 截取第一个/，避免后面有误导
    public static String getHostPrefix(String urlString) {
        int index = urlString.indexOf("://");
        if (index != -1) {
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index);
        }
        return urlString;
    }


    /**
     * 检查：是否隐藏标题栏
     */
    public static boolean checkHideTitleBar(String url) {

        String urlStr = url.trim();
        if (urlStr.contains(HIDE_TITLEBAR)) {
            return true;
        }
        return false;
    }

}
