package com.foodaclic.livraison.service.fcm;

import android.content.Intent;
import android.util.Log;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.service.gcm.GcmRegistrationService;
import com.foodaclic.livraison.utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * @author  alexwilfriedo on 27/11/2016.
 */

public class CustomFirebaseInstanceIdService extends FirebaseInstanceIdService {

  private final String TAG = CustomFirebaseInstanceIdService.class.getName();

  @Override public void onTokenRefresh() {

    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.e(TAG, String.format("Refreshed Token: %s", refreshedToken));

    MainApplication.getPrefs().putString(Constants.PREFS_REGISTRATION_ID, refreshedToken);

    Intent intent = new Intent(this, GcmRegistrationService.class);
    startService(intent);
  }
}