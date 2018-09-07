package jp.android_group.asj.enpit_sample11;

public class Constants {
    // 非同期用のパラメタ用途
    public static final String ACCESS_TYPE_GET_USER = "1";
    public static final String ACCESS_TYPE_ADD_USER = "2";
    public static final String ACCESS_TYPE_MODIFY_USER = "3";
    public static final String ACCESS_TYPE_DELETE_USER = "4";
    public static final String ACCESS_TYPE_GET_ENTRY = "5";
    public static final String ACCESS_TYPE_ADD_ENTRY = "6";
    public static final String ACCESS_TYPE_MODITY_ENTRY = "7";
    public static final String ACCESS_TYPE_DELETE_ENTRY = "8";

    // StartActivityResult用のrequest_code
    public static final int REQUEST_GET_USER = 1;
    public static final int REQUEST_ADD_USER = REQUEST_GET_USER + 1;
    public static final int REQUEST_MODIFY_USER = REQUEST_ADD_USER + 1;
    public static final int REQUEST_DELETE_USER = REQUEST_MODIFY_USER + 1;
    public static final int REQUEST_GET_ENTRY = REQUEST_DELETE_USER + 1;
    public static final int REQUEST_ADD_ENTRY = REQUEST_GET_ENTRY + 1;
    public static final int REQUEST_MODITY_ENTRY = REQUEST_ADD_ENTRY + 1;
    public static final int REQUEST_DELETE_ENTRY = REQUEST_MODITY_ENTRY + 1;

    // putExtra用
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    public static final String EXTRA_SERVER_IP = "EXTRA_SERVER_IP";
}
