package com.foodaclic.livraison.utils;

import android.app.NotificationManager;
import android.content.Context;
import com.foodaclic.livraison.MainApplication;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

    //private static final String _BASE_API_DEV_TEST = "http://137.74.45.168/";
    private static final String _BASE_API_DEV = "http://192.168.8.113:9000";
    private static final String _BASE_API_PROD = "http://213.136.102.206:9000";

    private static final boolean _PROD_MODE = false;

    public static final DateFormat DATE_FORMAT = android.text.format.DateFormat.getDateFormat(
        MainApplication.getContext());

    //public static final String EMPTY_STRING = "";
    public static final String ENCLOSURE_SEPARATOR = "[@]"; // exactly three
                                                            // characters!

    public static final String FALSE = "false";

    public static final String FIELD_ARTICLE_LINK = "link";

    public static final String FIELD_NODE_ID = "node_id";
    public static final String FIELD_SECTION_ID = "section_id";

    public static final String FILE_URL = "file://";

    public static final String FROM_AUTO_REFRESH = "from_auto_refresh";

    public static final String HTTP = "http://";

    public static final String HTTPS = "https://";
    public static final String INTENT_FROM_WIDGET = "fromWidget";


    private static SimpleDateFormat mDateFormat;

    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static NotificationManager
        NOTIF_MGR = (NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String PREFS_APP_VERSION = "appVersion";
    public static final String PREFS_IMEI = "imei";

    public static final int PREFS_MODE = Context.MODE_PRIVATE;

    public static final String PREFS_NAME = "foodaclick";
    public static final String PREFS_VERSION = "updated";
    public static final String PREFS_BT = "bluetooth";
    public static final String PREFS_BT_NAME = "bt_name";
    public static final String PREFS_BT_MAC = "bt_mac";

    public static final String PREFS_PHONE_NUMBER = "phoneNumber";

    public static final String PREFS_PHONE_NUMBER_CODE = "phoneNumberCode";

    public static final String PREFS_PHONE_NUMBER_VALID = "phoneNumberIsValid";
    public static final String PREFS_REGISTRATION_ID = "registrationId";
    public static final String PREFS_SENT_TOKEN_TO_SERVER = "SENT_TOKEN_TO_SERVER";
    public static final String SENDER_ID = "365710624105";

    public static final DateFormat TIME_FORMAT = android.text.format.DateFormat.getTimeFormat(MainApplication.getContext());
    public static final String TRUE = "true";
    public static final int UPDATE_THROTTLE_DELAY = 1000;
    public static final String UTF8 = "UTF-8";

    public static final String FETCH_ROUTE = "route";
    public static final String FETCH_CATALOG = "catalog";
    public static final String FETCH_COMMAND = "command";
    public static final String FETCH_STOCK = "stock";
    public static final String FETCH_KPI = "kpi";
    public static final String FETCH_SYNC = "sync";
    public static final String CONNECTED = "connected";

    public static String getBaseApiDev() {
        return _BASE_API_DEV;
    }

    public static String getBaseApiProd() {
        return _BASE_API_PROD;
    }

    public static String getApiBase() {
        return (_PROD_MODE ? _BASE_API_PROD : _BASE_API_DEV);
    }

    public static SimpleDateFormat getDateFormat() {
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
        }
        return mDateFormat;
    }

    public static boolean isProd() {
        return _PROD_MODE;
    }


    // Constants for api url

    public static final String API_PATH_AUTH = "api/1/auth";
    public static final String API_PATH_PASSWORD_FORGOT = "api/1/auth/password/forgot";
    public static final String API_PATH_PASSWORD_RESET = "api/1/auth/password/reset";
    public static final String API_PATH_BILL = "api/1/buy/find";
    public static final String API_PATH_GET_BILL = "api/1/bills/";
    public static final String API_PATH_SEND_MESSAGE = "api/1/sendsms";
    public static final String API_PATH_CATALOG = "api/1/bootservices/catalog/";
    public static final String API_PATH_STARTING = "api/1/starting/";
    public static final String API_PATH_PERF = "api/1/bootservices/perform/";
    public static final String API_PATH_UPDATE_BUY = "api/1/updatebuy";

    public static final String API_PATH_PROCURE = "api/1/procure";
    public static final String API_PATH_SELL = "api/1/sell";
    public static final String API_PATH_RECOVERY = "api/1/recovery";

    public static final String API_AGENT_STOCK = "api/1/agentStock/";
    public static final String API_PATH_INIT = "api/1/bootservices/init/";


    public static final String API_PATH_RESET_TOKEN = "api/1/reset/token";
    public static final String API_PATH_SUBMIT_TOKEN = "api/1/submit/token";
    public static final String API_PATH_SENDER = "api/1/senders";
    public static final String API_PATH_CHECK_CLIENT = "api/1/check/client";
    public static final String API_PATH_FORGET_PASSWORD = "api/1/forget/password";

    public static final String API_MILEAGE         = "api/1/mileage";
    public static final String API_PAYMENT              = "api/1/payment";
    public static final String API_PATH_CHIEF_STOCK           = "chiefStock/";
    public static final String API_PATH_TRAFFIC               = "api/1/traffic";
    public static final String API_PATH_COMPETITIVE           = "competitive";
    public static final String API_PATH_ARTICLES              = "api/1/articles";
    public static final String API_PATH_UPDATE_POS            = "updatePos";
    public static final String API_PATH_POS                   = "pos";
    public static final String API_PATH_DEVICE = "api/1/device";
    public static final String API_PATH_GOAL                  = "api/1/goals/";
    public static final String API_PATH_AGENT_COMMAND        = "api/1/agentCommand/";
    public static final String API_PATH_COMMAND              = "api/1/command";
    public static final String API_PATH_SYNC              = "api/1/sync";


    // Constants for use in app

    public static String SIGN_ERROR = "Error: ===>>>>>";

    public static final String PSEUDO     = "pseudo";
    public static final String LOGIN      = "login";
    public static final String EMAIL      = "email";
    public static final String ERROR      = "error";
    public static final String RECOVERY    = "recovery";
    public static final int RECOVERY_ROUTE    = 1;
    public static final int RECOVERY_NOTHING    = 0;
    public static final int RECOVERY_NORMAL    = 2;

    public static final String USER_ID    = "user_id";
    public static final String SERIAL     = "serial";
    public static final String CHIEF_CODE = "chief_code";
    public static final String USER_C   = "C";
    public static final String USER_M   = "M";
    public static final String USER_G   = "G";
    public static final String USER_TYPE  = "M";
    public static final String AMOUNT     = "amount";
    public static final String ORDER              = "facture du : ";
    public static final String DISPLAY_QTE        = "D";
    public static final String CARDBOARD_QTE      = "C";
    public static final String SALE_INVOICE       = "sale";
    public static final String PROCURE_INVOICE    = "procure";
    public static final String PROVIDER_DMG       = "DMG";
    public static final String PROVIDER_G         = "G";
    public static final String CURRENCY           = "currency";
    public static final String LATITUDE           = "latitude";
    public static final String LONGITUDE          = "longitude";
    public static final String CODE_RETAILER      = "M";
    public static final int COEF_DISPLAY          = 24;
    public static final int COEF_CARDBOARD        = 1;
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String MOBILE_SYSTEM      = "ANDROID";
    public static final String GOAL_ID            = "goal_id";

    public static final int CODE_BUY_SELLER = 0;
    public static final int CODE_BUY_BROADCAST = 1;
    public static final int CODE_BUY_MOBILE_MONEY = 2;

    public static final String BUY_MODE = "buy_mode";

    public static final String READ_CONTACT_PERMISSION  = "read_contact_permission";
    public static final String SEND_SMS_PERMISSION  = "send_sms_permission";

    private static final int CAMERA_PERMISSIONS_REQUEST = 1000;
}