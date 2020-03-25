package com.pblibs.utility;

import com.pblibs.base.PBApplication;

public class PBConstants {

    public static final String EMPTY = "";
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String UNKNOWN_ERROR = "Unknown Error";
    public static final String BACK_SLASH = "/";
    public static final String UNDER_SCORE = "_";
    public static final String SYMBOL_EQUALS = " = ";
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String PRIMARY = "primary";
    public static final String TYPE_JPG = ".jpg";
    public static final String TYPE_JSON = ".json";
    public static final String PROVIDER = ".provider";
    public static final String PUB_DOWNLOADS = "content://downloads/public_downloads";
    public static final long SPLASH_INTERVAL = 1500;
    public static final String PREF_NAME = PBApplication.getInstance().getContext().getPackageName();
    public static final int ZERO = 0;
    public static final int PRIVATE_MODE = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int EIGHTEEN = 18;
    public static final String TEXT_PATTERN = "^[a-zA-Z ]*";
    public static final String NUM_PATTERN = "^[0-9]*";
    public static final String PSWD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{%d,20})";
    public static final String DATE_FORMAT_1 = "dd-MMM-yyyy hh:mm:ss";
    public static final String DATE_FORMAT_2 = "yyyyMMdd";
    public static final String IMEI = "IMEI";
    public static final String TIME = "Time";
    public static final String MESSAGE = "Message";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String URL_ENCODED_TYPE = "application/x-www-form-urlencoded";
    public static final String IS_FIRST_TIME_LAUNCH = "is_first_time_launch";
    public static final String USER_ID = "user_id";

    private PBConstants() {
        //do nothing
    }
}
