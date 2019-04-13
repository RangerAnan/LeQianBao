package com.leqian.bao.common.permissions;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.leqian.bao.GlobalApplication;
import com.leqian.bao.R;
import com.leqian.bao.common.util.ToastUtil;
import com.nxin.base.utils.ProHelper;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


/**
 * Created by zhuhua
 * mail:mail.nxin.com
 * date: 2016/6/21.
 * <p>
 * Desc:
 * //1.RxPermission升级0.10.2，Fragment中创建RxPermissions，需要传入Fragment，使用Activity会抛出异常。
 */
public class PermissonsUtil {


    /***
     * 存储权限申请
     **/
    public static void getStoragePermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(PermissonsConstant.storageString).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    resultAction.onGranted();
                } else {
                    resultAction.onDenied("");
                }
            }
        });
    }


    /***
     * Fragment 定位权限申请
     **/
    public static void getLocationFragmentPermisson(Fragment fragment, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(fragment.getActivity());
        requestLocationPermission(rxPermissions, resultAction);
    }

    /**
     * Activity定位权限申请
     **/
    public static void getLocationPermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        requestLocationPermission(rxPermissions, resultAction);
    }

    /**
     * 请求定位权限
     */
    private static void requestLocationPermission(RxPermissions rxPermissions, final PermissionsResultAction resultAction) {
        final boolean[] isLOCATIONPerm = {false};
        final boolean[] isSTORAGEPerm = {false};
        final boolean[] isREAD_PHONE_STATEPerm = {false};
        final int[] i = {0};
        rxPermissions.requestEach(PermissonsConstant.locationString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (permission.granted) {
                        isLOCATIONPerm[0] = true;
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (permission.granted) {
                        isSTORAGEPerm[0] = true;
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                    if (permission.granted) {
                        isREAD_PHONE_STATEPerm[0] = true;
                    }
                    i[0] += 1;
                }
                if (i[0] == 3) {
                    if (isLOCATIONPerm[0] && isSTORAGEPerm[0] && isREAD_PHONE_STATEPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        int error_code = 0;
                        if (!isLOCATIONPerm[0]) {
                            error_code = 1;
                        } else if (!isSTORAGEPerm[0]) {
                            error_code = 2;
                        } else if (!isREAD_PHONE_STATEPerm[0]) {
                            error_code = 3;
                        }
                        if (error_code == 1) {
                            ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Location));
                        } else if (error_code == 2) {
                            ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Location_Storage));
                        } else {
                            ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Location_Phone));
                        }
                        resultAction.onDenied(error_code + "");
                    }
                }
            }
        });
    }

    /***
     * 定位权限申请
     **/
    public static void getLocationPermissions(FragmentActivity activity, final PermissionsResultAction resultAction) {
        final RxPermissions rxPermissions = new RxPermissions(activity);
        final boolean[] isLOCATIONPerm = {false};
        final boolean[] isSTORAGEPerm = {false};
        final int[] i = {0};
        rxPermissions.requestEach(PermissonsConstant.LOCATION_PERMISSIONS).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (permission.granted) {
                        isLOCATIONPerm[0] = true;
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (permission.granted) {
                        isSTORAGEPerm[0] = true;
                    }
                    i[0] += 1;
                }
                if (i[0] == 2) {
                    if (isLOCATIONPerm[0] && isSTORAGEPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Location));
                        resultAction.onDenied("");
                    }
                }
            }
        });
    }


    /***
     * 拍照获取图片权限申请
     **/
    public static void getCamraWebPermisson(Activity activity, final PermissionsResultAction resultAction) {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, PermissonsConstant.camraString, resultAction);
    }

    /***
     * Activity拍照获取图片权限申请
     **/
    public static void getCamraPermisson(FragmentActivity activity, PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        requestCameraPermission(rxPermissions, resultAction);
    }


    /***
     * Fragment拍照获取图片权限申请
     **/
    public static void getFragmentCameraPermission(Fragment fragment, PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(fragment.getActivity());
        requestCameraPermission(rxPermissions, resultAction);
    }

    /**
     * 请求拍照权限
     */
    private static void requestCameraPermission(RxPermissions rxPermissions, final PermissionsResultAction resultAction) {
        final boolean[] isCAMERA_Perm = {false};
        final boolean[] isSTORAGEPerm = {false};
        final int[] i = {0};
        rxPermissions.requestEach(PermissonsConstant.camraString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {

                if (permission.name.equals(Manifest.permission.CAMERA)) {
                    if (permission.granted) {
                        isCAMERA_Perm[0] = true;
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (permission.granted) {
                        isSTORAGEPerm[0] = true;
                    }
                    i[0] += 1;
                }
                if (i[0] == 2) {
                    if (isCAMERA_Perm[0] && isSTORAGEPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        resultAction.onDenied("");
                    }
                }

            }
        });
    }

    /***
     * 音频权限申请
     **/
    public static void getAudioPermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        final boolean[] isAUDIO_Perm = {false};
        final boolean[] isSTORAGEPerm = {false};
        final int[] i = {0};
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(PermissonsConstant.audioString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.name.equals(Manifest.permission.RECORD_AUDIO)) {
                    if (permission.granted) {
                        isAUDIO_Perm[0] = true;
                    } else {
                        //ToastUtil.showToastShort(R.string.Permissons_Not_Audio);
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (permission.granted) {
                        isSTORAGEPerm[0] = true;
                    } else {
                        //ToastUtil.showToastShort(R.string.Permissons_Not_Storage);
                    }
                    i[0] += 1;

                }
                if (i[0] == 2) {
                    if (isAUDIO_Perm[0] && isSTORAGEPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        resultAction.onDenied("");
                    }
                }
            }
        });
    }

    /***
     * 通讯录权限申请
     **/
    public static void getContactsPermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(PermissonsConstant.contactsString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.name.equals(Manifest.permission.READ_CONTACTS)) {
                    if (permission.granted) {
                        resultAction.onGranted();
                    } else {
                        resultAction.onDenied("");
                        //ToastUtil.showToastShort(R.string.Permissons_Not_Contacts);
                    }
                }
            }
        });
    }

    /***
     * Activity短信权限申请
     **/
    public static void getSmsPermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        requestSMSPermission(rxPermissions, resultAction);
    }

    /***
     * Fragment 短信权限申请
     **/
    public static void getSmsPermission(Fragment fragment, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(fragment.getActivity());
        requestSMSPermission(rxPermissions, resultAction);
    }

    private static void requestSMSPermission(RxPermissions rxPermissions, final PermissionsResultAction resultAction) {
        final boolean[] isRECEIVE_SMSPerm = {false};
        final boolean[] isSEND_SMSPerm = {false};
        final int[] i = {0};
        rxPermissions.requestEach(PermissonsConstant.smsString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {

                if (permission.name.equals(Manifest.permission.SEND_SMS)) {
                    if (permission.granted) {
                        isRECEIVE_SMSPerm[0] = true;
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.RECEIVE_SMS)) {
                    if (permission.granted) {
                        isSEND_SMSPerm[0] = true;
                    }
                    i[0] += 1;
                }
                if (i[0] == 2) {
                    if (isRECEIVE_SMSPerm[0] && isSEND_SMSPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        resultAction.onDenied("");
                        //ToastUtil.showToastShort(R.string.Permissons_Not_Sms);
                    }
                }


            }
        });
    }

    /***
     * 电话权限申请
     **/
    public static void getPhonePermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        final boolean[] isCALL_PHONEPerm = {false};
        final boolean[] isREAD_PHONE_STATEPerm = {false};
        final int[] i = {0};
        rxPermissions.requestEach(PermissonsConstant.phoneString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {

                if (permission.name.equals(Manifest.permission.CALL_PHONE)) {
                    if (permission.granted) {
                        isCALL_PHONEPerm[0] = true;
                    }
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                    if (permission.granted) {
                        isREAD_PHONE_STATEPerm[0] = true;
                    }
                    i[0] += 1;
                }
                if (i[0] == 2) {
                    if (isCALL_PHONEPerm[0] && isREAD_PHONE_STATEPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        resultAction.onDenied("");
                        //ToastUtil.showToastShort(R.string.Permissons_Not_Phone);
                    }
                }

            }
        });
    }

    /***
     * 定位权限申请--必须在主线程执行
     **/
    public static void getVideoPermisson(FragmentActivity activity, final PermissionsResultAction resultAction) {
        final RxPermissions rxPermissions = new RxPermissions(activity);
        final boolean[] isCAMERAPerm = {false};
        final boolean[] isRECORD_AUDIOPerm = {false};
        final boolean[] isWRITE_EXTERNAL_STORAGEPerm = {false};
        final int[] i = {0};
        rxPermissions.requestEach(PermissonsConstant.videoString).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.name.equals(Manifest.permission.CAMERA)) {
                    isCAMERAPerm[0] = permission.granted;
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.RECORD_AUDIO)) {
                    isRECORD_AUDIOPerm[0] = permission.granted;
                    i[0] += 1;
                } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    isWRITE_EXTERNAL_STORAGEPerm[0] = permission.granted;
                    i[0] += 1;
                }
                if (i[0] == 3) {
                    if (isCAMERAPerm[0] && isRECORD_AUDIOPerm[0] && isWRITE_EXTERNAL_STORAGEPerm[0]) {
                        resultAction.onGranted();
                    } else {
                        int error_code = 0;
                        if (isCAMERAPerm[0]) {
                            error_code = 1;
                        } else if (isRECORD_AUDIOPerm[0]) {
                            error_code = 2;
                        } else if (isWRITE_EXTERNAL_STORAGEPerm[0]) {
                            error_code = 3;
                        }
                        if (error_code == 2) {
                            ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Audio));
                        } else if (error_code == 3) {
                            ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Storage));
                        } else {
                            ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Video));
                        }
                        resultAction.onDenied(error_code + "");
                    }
                }


            }
        });
    }

    /****
     * 权限回调
     **/
    public static void resultPermisson(String[] permissions, int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
