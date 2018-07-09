package com.foodaclic.livraison.service.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.foodaclic.livraison.BuildConfig;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.service.rest.RestClient;
import com.foodaclic.livraison.utils.Constants;
import com.foodaclic.livraison.utils.Utils;
import com.foodaclic.livraison.utils.event.AndroidBus;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;
import com.foodaclic.livraison.utils.event.NetworkOperationEvent;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cyrilleguipie on 10/28/15.
 */
public class GcmRegistrationService extends IntentService {
  public static final String REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE";
  private static final String TAG = GcmRegistrationService.class.getName();
  private static final String[] TOPICS = { "global" };
  private Context mContext;
  private AndroidBus mBus;

  public GcmRegistrationService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {
    mContext = this;
    mBus = AndroidBusProvider.getInstance();
    try {
      String token = MainApplication.getPrefs().getString(Constants.PREFS_REGISTRATION_ID, "");
      final int userId = MainApplication.getPrefs().getInt(Constants.USER_ID, 0);
        if(token != null && !token.isEmpty()){
          Log.e("token", token);
          sendRegistrationTokenToServer(token, userId);
        }


      MainApplication.getPrefs().putBoolean(Constants.PREFS_SENT_TOKEN_TO_SERVER, true);
    } catch (Exception e) {
      MainApplication.getPrefs().putBoolean(Constants.PREFS_SENT_TOKEN_TO_SERVER, false);
    }

    Intent registrationComplete = new Intent(GcmRegistrationService.REGISTRATION_COMPLETE);
    LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
  }

  /**
   * Persist registration to third-party servers.
   *
   * Modify this method to associate the user's GCM registration token with any server-side account
   * maintained by your application.
   *
   * @param token The new token.
   * @param userId User ID.
   */
  private void sendRegistrationTokenToServer(String token, int userId) {


    Call<ResponseBody> result = RestClient.getRestService().registerDevice(token, userId);
    result.enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {

      }
    });

  }


}
