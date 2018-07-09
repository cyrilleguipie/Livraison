package com.foodaclic.livraison.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.foodaclic.livraison.MainApplication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL") public class Utils {



    public static double latitude(){
        String latString = MainApplication.getPrefs().getString(Constants.LATITUDE, "");
        double lat = 0.0;
        if(latString != null && !latString.isEmpty()){
            lat = Double.valueOf(latString);
        }
        return lat;
    }
    public static double longitude(){
        String latString = MainApplication.getPrefs().getString(Constants.LONGITUDE, "");
        double lat = 0.0;
        if(latString != null && !latString.isEmpty()){
            lat = Double.valueOf(latString);
        }
        return lat;
    }

    public static String encodeToBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String base64Encode(File file){
        String data = null;
        try {
            // Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            // Converting Image byte array into Base64 String
            data = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(imageData);
            imageInFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static boolean internetError(String s){
        return s.contains("ENETUNREACH");
    }

    public static void logout(Context context) {
        MainApplication.getPrefs().putLong(Constants.USER_ID, 0);
       
    }
    protected static ObjectMapper jsonObjectMapper = null;
    public static <T> T toObject(String jsonString, Class<T> type){
        T obj = null;
        if (jsonObjectMapper == null) {
            jsonObjectMapper = new ObjectMapper();
            //jsonObjectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            // jsonObjectMapper.setSerializationInclusion(Include.NON_NULL);
        }
        try{
            obj = jsonObjectMapper.readValue(jsonString, type);
        }catch (IOException e){
            e.printStackTrace();
        }

        return obj;
    }

    public static <T> ArrayList<T> toObjects(String jsonString, Class<T> type){
        ArrayList<T> obj = null;
        if (jsonObjectMapper == null) {
            jsonObjectMapper = new ObjectMapper();
            //jsonObjectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            //// jsonObjectMapper.setSerializationInclusion(Include.NON_NULL);

        }
        try{
            Class<?> clz = Class.forName(type.getName());
            JavaType jtype = jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, clz);
            //obj = jsonObjectMapper.readValue(jsonString, new TypeReference<List<T>>(){});
            obj = jsonObjectMapper.readValue(jsonString, jtype);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return obj;
    }

    public static String toJson(Object object){
        String jsonString = "";
        if (object != null) {
            if (jsonObjectMapper == null) {
                jsonObjectMapper = new ObjectMapper();
                //jsonObjectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
                //// jsonObjectMapper.setSerializationInclusion(Include.NON_NULL);
            }

            //if (Play.mode.isDev() || Play.runingInTestMode()) {
            //    jsonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            //    Logger.setUp("DEBUG");
            //}

            try {
                jsonString = jsonObjectMapper.writeValueAsString(object);

            } catch (JsonGenerationException e) {
                throw new RuntimeException(e);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

        }
            return jsonString;
    }

    public static String getBillDetailsUrl(long agentId, long billId){
        return Constants.getBaseApiDev()+"/api/1/bills/"+agentId+"/details/"+billId;
    }

    //public static String generateInvoiceNumber() {
    //    long number = MainApplication.getPrefs().getLong(Constants.USER_ID, 0) + new Date().getTime();
    //    return String.valueOf(number);
    //}

    public static void hideKeyBoard(EditText input){
        //input.setInputType(0);
        InputMethodManager
            imm = (InputMethodManager) MainApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    public static final String DISABLE_PICTURES = "pictures.disable";

    public static final String FETCH_PICTURES = "pictures.fetch";

    public static final String FIRST_OPEN = "FIRST_OPEN";

    public static final String FONT_SIZE = "fontsize";

    public static final String IS_REFRESHING = "IS_REFRESHING";

    public static final String KEEP_TIME = "keeptime";

    public static final String LAST_SCHEDULED_REFRESH = "lastscheduledrefresh";

    public static final String LIGHT_THEME = "lighttheme";

    public static final String NOTIFICATIONS_ENABLED = "notifications.enabled";

    public static final String NOTIFICATIONS_RINGTONE = "notifications.ringtone";

    public static final String NOTIFICATIONS_VIBRATE = "notifications.vibrate";

    public static final String REFRESH_ENABLED = "refresh.enabled";

    public static final String REFRESH_INTERVAL = "refresh.interval";

    public static final String REFRESH_ON_OPEN_ENABLED = "refreshonopen.enabled";

    public static final String REFRESH_WIFI_ONLY = "refreshwifionly.enabled";
    public static final String SHOW_READ = "show_read";

    /**
     * This method converts dp unit to equivalent pixels, depending on device
     * density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need
     *                to convert into pixels
     * @param context Context to get resources and device specific display
     *                metrics
     * @return A float value to represent px equivalent to dp depending on
     * device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent
     * pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display
     *                metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static ArrayList<String> extractSourceTags(String input) {
        final ArrayList<String> result = new ArrayList<>();

        final Pattern pattern = Pattern
                .compile("<source[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getContext());
        return settings.getBoolean(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getContext());
        return settings.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getContext());
        return settings.getLong(key, defValue);
    }

    public static String getString(String key, String defValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getContext());
        return settings.getString(key, defValue);
    }

    public static boolean isNull(String s) {
        return (s == null) || s.equalsIgnoreCase("null") || (s.trim().length() == 0);
    }

    public static void launchPlayStoreAppProfile(String appPackageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                    + appPackageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainApplication.getContext().startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + appPackageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainApplication.getContext().startActivity(intent);
        }
    }

    public static void putBoolean(String key, boolean value) {
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                MainApplication.getContext()).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putInt(String key, int value) {
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                MainApplication.getContext()).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putLong(String key, long value) {
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                MainApplication.getContext()).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void putString(String key, String value) {
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                MainApplication.getContext()).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void registerOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext())
                    .registerOnSharedPreferenceChangeListener(listener);
        } catch (final Exception ignored) { // Seems to be possible to have a
            // NPE here... Why??
        }
    }

    public static void remove(String key) {
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                MainApplication.getContext()).edit();
        editor.remove(key);
        editor.commit();
    }

    public static void unregisterOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext())
                    .unregisterOnSharedPreferenceChangeListener(listener);
        } catch (final Exception ignored) { // Seems to be possible to have a
            // NPE here... Why??
        }
    }



    public static int getCellID(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
                .getCellLocation();

        int cellID = cellLocation.getCid();
        return cellID;
    }

    public static int getCellLac(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
                .getCellLocation();
        int lac = cellLocation.getLac();
        return lac;
    }

    public static int getMCC(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String operator = telephonyManager.getNetworkOperator();
        int mcc = Integer.parseInt(operator.substring(0, 3));
        return mcc;
    }

    public static int getMNC(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String operator = telephonyManager.getNetworkOperator();
        int mnc = Integer.parseInt(operator.substring(3));
        return mnc;
    }

    /**
     * get date to string
     *
     * @param date
     * @return
     */
    public static Date getDateFromString(String date) {
        Date newdate = null;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            newdate = dateformat.parse(date);
            System.out.println(newdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newdate;
    }

    /**
     * upperCase the first char in string
     *
     * @param value
     * @return
     */
    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    /**
     * formated amount to string
     *
     * @param amount
     * @return
     */
    public static String amountToStr(long amount) {
        String amountStr = Long.toString(amount);
        int taille, reste, three;
        String montantStr = "", montant = amountStr + "";
        taille = montant.length();
        three = taille - 3;

        while (taille > 3) {
            reste = taille - 3;
            montantStr = montant.substring(reste, taille) + " " + montantStr;
            montant = montant.substring(0, reste);
            taille = montant.length();
        }
        montantStr = montant.substring(0, taille) + " " + montantStr;
        return montantStr;
    }

    /**
     * formated date  to string (12/09/2015)
     *
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static String FormatDate(int day, int month, int year) {
        String jour = Integer.toString(day);
        String mois = Integer.toString(month + 1);
        if (jour.length() == 1) {
            jour = "0" + jour;
        }

        if (mois.length() == 1) {
            mois = "0" + mois;
        }
        return jour + "/" + mois + "/" + Integer.toString(year);
    }

    public static String formatLongDate(Long dateLong) {

        SimpleDateFormat dF = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        String date = null;
        try {
            date = dF.format(dateLong);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        /*
        String longV = "1343805819061";
        long millisecond = Long.parseLong(longV);
        String dateString= DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString()
        */
        return date;
    }

    /**
     * formated date  to string (12/09/2015)
     *
     * @param dateString
     * @return
     */
    public static Date FormatStringToDate(String dateString) {
        SimpleDateFormat dF = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = dF.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * string date formated (12/09/2015) to epoch
     *
     * @param dateFormated
     * @return
     */
    public static long dateFormatedToEpoch(String dateFormated) {
        String dateDeb = String.valueOf(dateFormated);
        // The mask
        DateFormat dF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = dF.parse(dateDeb);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return epoch;
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String formatDateWithHour(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
    }

    public static String formatAmount(Double amount) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(amount);

    }

    public static String formatAmount(int amount) {

        String amountStr = Integer.toString(amount);

        String[] parts = amountStr.split("\\.");
        String intPart = parts[0];
        //String fracPart = parts[1];

        int taille, reste, three;
        String montantStr = "", montant = intPart + "";
        taille = montant.length();
        three = taille - 3;

        while (taille > 3) {
            reste = taille - 3;
            montantStr = montant.substring(reste, taille) + " " + montantStr;
            montant = montant.substring(0, reste);
            taille = montant.length();
        }
        montantStr = montant.substring(0, taille) + " " + montantStr;

        //return montantStr + "." + fracPart;
        return montantStr;

    }

    //Compare distance
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math
            .cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344 * 1000;

        return (dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    public  static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //check in the market
    public static boolean inTheMarket(double marketLat, double marketLng, double marketRadius){

        boolean isMarket = false;

        try{
            double positionLat = Double.parseDouble(MainApplication.getPrefs().getString(Constants.LATITUDE, ""));
            double positionLng = Double.parseDouble(MainApplication.getPrefs().getString(Constants.LONGITUDE, ""));

            isMarket =
                (positionLat != 0) && (positionLng != 0) && (marketRadius >= distance(positionLat,
                    positionLng, marketLat, marketLng));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isMarket;
    }


    public static String generateSellOrRecoveryHeader(long agentId, String numBill,
        long orderlinesSize, double amount, String type, double amountPaid, long customerId,
        String numPayment){

        String sellHeader = "";
        sellHeader = new StringBuilder(sellHeader)
            .append(agentId)
            .append(";")
            .append(numBill)
            .append(";")
            .append(orderlinesSize)
            .append(";")
            .append(amount)
            .append(";")
            .append(type)
            .append(";")
            .append(amountPaid)
            .append(";")
            .append(customerId)
            .append(";")
            .append(numPayment)
            .toString();

        return sellHeader;
    }

    public static String generatePaymentHeader(long agentId, String numPayment,
        long billOperationsSize, double amount){

        String sellHeader = "";
        sellHeader = new StringBuilder(sellHeader)
            .append(agentId)
            .append(";")
            .append(numPayment)
            .append(";")
            .append(billOperationsSize)
            .append(";")
            .append(amount)
            .toString();

        return sellHeader;
    }

    public static String generateSyncHeader(long billsSize, long orderlinesSize, String numPayment){

        String sellHeader = "";
        sellHeader = new StringBuilder(sellHeader)
            .append(billsSize)
            .append(";")
            .append(orderlinesSize)
            .append(";")
            .append(numPayment)
            .toString();

        return sellHeader;
    }

    public static String generateInvoiceNumber() {
        long number = MainApplication.getPrefs().getLong(Constants.USER_ID, 0) + new Date().getTime();
        return String.valueOf(number);
    }

    public static long generateIdOffline() {
        return MainApplication.getPrefs().getLong(Constants.USER_ID, 0) + System.currentTimeMillis();
    }

    public static void vibrate(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        v.vibrate(1000);
    }

    public static void deconnect(Context mContext) {

        MainApplication.getPrefs().putString(Constants.SERIAL, "");
        MainApplication.getPrefs().putString(Constants.CHIEF_CODE, "");
        MainApplication.getPrefs().putString(Constants.LOGIN, "");
        MainApplication.getPrefs().putString(Constants.EMAIL, "");
        MainApplication.getPrefs().putLong(Constants.USER_ID, 0);
        MainApplication.getPrefs().putString(Constants.ERROR, "true");

    }



    public static boolean checkInternet(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED){
            return true;
        }else{
            return false;
        }
    }

    public static boolean checkGpsStatus(Context context){

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void setMobileDataEnabled(Context context, boolean enabled) {
        final ConnectivityManager conman =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(
                iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void gpsDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Geomy");
        builder.setMessage("Veuillez activer le GPS");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        Dialog alertDialog = builder.create();
        alertDialog.show();
    }


}
