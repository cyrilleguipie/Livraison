package com.foodaclic.livraison;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;
import com.foodaclic.livraison.receiver.ConnectionChangeReceiver;
import com.foodaclic.livraison.service.gcm.GcmRegistrationService;
import com.foodaclic.livraison.utils.AppSharedPreferences;
import com.foodaclic.livraison.utils.Constants;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;
import com.foodaclic.livraison.utils.event.NetworkOperationEvent;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.Locale;

public class MainApplication extends Application {

  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_WRITE = 3;
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_TOAST = 5;
  public static final int MESSAGE_CONNECTION_LOST = 6;
  public static final int MESSAGE_UNABLE_CONNECT = 7;


  public static final String DEVICE_NAME = "device_name";
  public static final String TOAST = "toast";

  private static final Bus BUS = new Bus();

  private static Context context;

  private static AppSharedPreferences mPrefs;

  private static final String TAG = MainApplication.class.getName();


  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  public MainApplication() {
  }

  @Override public void onCreate() {
    super.onCreate();
    AndroidBusProvider.getInstance().register(this);
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration
        .Builder()
        .deleteRealmIfMigrationNeeded()
        .build();
    Realm.setDefaultConfiguration(config);
    Fresco.initialize(this);
    context = getApplicationContext();

    mPrefs = new AppSharedPreferences(context);
    if(MainApplication.getPrefs().getInt(Constants.USER_ID, 0) > 0){
      startService(new Intent(this, GcmRegistrationService.class));
    }



    //if (!mBluetoothAdapter.isEnabled()) {
    //  Intent enableIntent = new Intent(
    //      BluetoothAdapter.ACTION_REQUEST_ENABLE);
    //  startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    //  // Otherwise, setup the session
    //} else {
    //  if (mService == null)
    //    KeyListenerInit();//监听
    //}

  }


  public static  Realm getRealm(){
    return Realm.getDefaultInstance();
  }

  public static Locale getCurrentLocale() {
    Locale current = context.getResources().getConfiguration().locale;
    return current;
  }

  @Override public void onTerminate() {
    Log.d(TAG, "I have terminated");
    AndroidBusProvider.getInstance().unregister(this);
    super.onTerminate();

  }



  public static Bus getBus() {
    return BUS;
  }

  public static Context getContext() {
    return context;
  }

  public static AppSharedPreferences getPrefs() {
    return getPrefs(MainApplication.getContext());
  }

  public static AppSharedPreferences getPrefs(Context context) {
    if (mPrefs == null) {
      mPrefs = new AppSharedPreferences(context);
    }
    return mPrefs;
  }



}
