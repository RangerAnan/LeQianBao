package com.leqian.bao.model.code;

/**
 * RequestCode 不得超过65535
 */
public class RequestCode {

    //地区
    public static final int addreRequestCode = 10101;
    //性别
    public static final int sexRequestCode = 10102;
    //昵称
    public static final int nicknameRequestCode = 10103;
    // 群成员详情
    public static final int ChatRoomUserListRequestCode = 10201;
    //群选择
    public static final int chatGroupMsgSendRequestCode = 10202;
    //群选择2.0
    public static final int chatGroupMsgSendV2RequestCode = 10203;
    //群名称修改操作
    public static final int chatGroupEditNameRequestCode = 10204;
    // 我的群
    public static final int chatListMsgRequestCode = 10205;
    //批量删除群成员
    public static final int chatGroupDeleteMenberRequestCode = 10206;
    //新朋友页
    public static final int NEWFRIEND_ADDFRIEND_REQUESTCODE = 10207;
    //群详情修改
    public static final int chatRoomIntroduction_change = 10207;
    // 通讯录详情
    public static final int ContactsInfoRequestCode = 10301;
    //标签保存回调
    public static final int cantactsLableSaveCode = 10302;
    //公共帐号详情回调，取消关注成功
    public static final int cancelAttentionSuccess = 10303;
    //群通知页面
    public final static int group_notice_page = 10304;

    public final static int SELECT_GROUP_MEMBER = 10305;

    public static final int CHOICE_CMARE = 20101;
    public static final int CHOICE_PHOTO = 20102;
    public static final int CHOICE_MEDIA = 20103;
    public static final int CHOICE_CMARE_Batch = 20104;
    public static final int CHOICE_CMARE_Choose_Batch = 20105;
    public static final int CHOICE_CMARE_QINIU_Batch = 20106;
    public static final int CHOICE_PHOTO_QINIU_Batch = 20107;
    public static final int JS_CHOICE_PHOTO_BATCH = 20108;

    public static final int COMMAND = 50101;
    public final static int MediaPlayerStop = 50102;
    public final static int FILE = 50103;
    public final static int FILERequestCode = 50104;
    public final static int FILEUploadCode = 50105;
    //地图大头针选择地区
    public static final int MAP_SELECT_POSITION = 50106;

    //组织
    public static final int organize_industry = 3000;
    public static final int organize_select_member = 3001;
    public static final int organize_invited_member = 3002;
    //人脸识别
    public static final int JS_FACE_RECOGNITION = 3004;
    //智农B超
    public static final int JS_SMART_VUSKIT = 3005;

    /**
     * v3.5.0 背膘仪连接后跳转
     */
    public static final int BLUETOOTH_BACKFACT_CODE = 3006;

    /*************************************1011消息列表*****************************************************/
    /**
     * 消息列表回调
     **/
    public static final int error_login_userinfo = -100;

    public static final int note_add_record_code = -110;

    /**
     * v4.0.0 发帖进入选择圈子
     */
    public static final int SELECT_CIRCLE_REQUEST_CODE = 3007;
}
