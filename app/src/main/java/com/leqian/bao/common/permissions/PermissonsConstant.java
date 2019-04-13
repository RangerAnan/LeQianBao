package com.leqian.bao.common.permissions;

import android.Manifest;

/**
 * Created by Administrator on 2016/6/21.
 */
class PermissonsConstant {
    protected static final String[] camraString = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected static final String[] audioString = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected static final String[] contactsString = {Manifest.permission.READ_CONTACTS};
    protected static final String[] locationString = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
    protected static final String[] smsString = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS};
    protected static final String[] phoneString = {Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};
    protected static final String[] storageString = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected static final String[] videoString = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //访问确切位置信息； 访问大致位置信息
    protected static final String[] LOCATION_PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
}
