package com.leqian.bao.model;

import com.leqian.bao.common.util.NetworkUtils;
import com.leqian.bao.model.bll.LoginBLL;
import com.nxin.base.model.domain.BaseModel;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:46
 * @Description http请求体基类，因为必须要获取userId，所以在构造该类时执行获取userId的方法，该方法是耗时操作，
 * 为了不阻塞UI线程，初始化该类时必须在子线程中执行
 */

public class BaseModelReq extends BaseModel {


    /**
     * todo 以下是公共参数，字段名后期需要统一
     */
//    public String userId;//用户id
//    public int pageNumber;
//    public int pageSize = 10;
//    public String m = AppConstants.DEVICE_FACTORY + "/" + AppConstants.DEVICE_MODEL;// 设备厂商及型号
//    public String sv = AppConstants.DEVICE_VERSION;// 操作系统版本号
//    public String app = AppConstants.APP_CODE;//客户端代号(fonts)
//    public String di = AppConstants.IMEI;// 设备唯一标识符
//    public String n = NetworkUtils.getNetworkType();// 网络状态（1：数据网络，2：wifi网络）
//    public String p = NetworkUtils.getCellularType();// 蜂窝数据提供商（1：中国移动，2：中国联通，3：中国电信，4：其它）
//    public String v = AppConstants.CLIENT_VERSION;//ApplicationUtil.getAppClientVersion();//客户端版本
//    public String os = AppConstants.APP_OS;//客户端系统(android/ios)
//    public String type = "0";//APP(“0”,"移动终端")WEB(“1”,"PC终端")

    public String token = "a84d81aa54523bcab9d88c32d9e751ac";

    /**
     * 子类赋值
     */
    public String method = "";

    public int uid = LoginBLL.getInstance().getUserInfo().getUid();

}
