package com.foodaclic.livraison.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class AppSharedPreferences {
  public static final String NOTIFICATIONS_ENABLED = "notifications.enabled";
  public static final String NOTIFICATIONS_RINGTONE = "notifications.ringtone";
  public static final String NOTIFICATIONS_VIBRATE = "notifications.vibrate";

  private Context context;

  private SharedPreferences prefs;

  public AppSharedPreferences(Context context) {
    this.context = context;
    this.prefs = this.context.getSharedPreferences(Constants.PREFS_NAME, Constants.PREFS_MODE);
  }

  public boolean getBoolean(String key, boolean defValue) {
    return prefs.getBoolean(key, defValue);
  }

  public void putBoolean(String key, boolean value) {
    SharedPreferences.Editor editor = prefs.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  public int getInt(String key, int defValue) {
    SharedPreferences settings = prefs;
    return settings.getInt(key, defValue);
  }

  public void putInt(String key, int value) {
    SharedPreferences.Editor editor = prefs.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  public long getLong(String key, long defValue) {
    SharedPreferences settings = prefs;
    return settings.getLong(key, defValue);
  }

  public void putLong(String key, long value) {
    SharedPreferences.Editor editor = prefs.edit();
    editor.putLong(key, value);
    editor.apply();
  }

  public String getString(String key, String defValue) {
    SharedPreferences settings = prefs;
    return settings.getString(key, defValue);
  }

  public void putString(String key, String value) {
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public void remove(String key) {
    SharedPreferences.Editor editor = prefs.edit();
    editor.remove(key);
    editor.apply();
  }

  public static void registerOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
    try {
      // prefs.registerOnSharedPreferenceChangeListener(listener);
    } catch (Exception ignored) { // Seems to be possible to have a NPE here... Why??
    }
  }

  public static void unregisterOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
    try {
      // prefs.unregisterOnSharedPreferenceChangeListener(listener);
    } catch (Exception ignored) { // Seems to be possible to have a NPE here... Why??
    }
  }
}