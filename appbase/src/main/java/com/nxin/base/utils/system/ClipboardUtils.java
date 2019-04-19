package com.nxin.base.utils.system;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by fangchaolai on 2017/3/11.
 * description:剪切板工具类
 */

public class ClipboardUtils {

    /**
     * 获取剪切板的文本信息
     *
     * @param context
     * @return 获取不到返回空字符串
     */
    public static String getTextFromClipboard(Context context) {

        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

        if (!cm.hasPrimaryClip()) {
            return "";
        }

        ClipData clipData = cm.getPrimaryClip();
        if (clipData == null) {
            return "";
        }

        if (clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            if (itemAt.getText() != null) {
                return itemAt.getText().toString().trim();
            }

        }
        return "";
    }

    /**
     * 设置剪切板内容
     *
     * @param context
     * @param label
     * @param content
     */
    public static void setTextToClipboard(Context context, String label, String content) {

        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText(label, content);
        cm.setPrimaryClip(clipData);


    }


}
