package com.foodaclic.livraison.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.foodaclic.livraison.BuildConfig;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.activity.MainActivity;
import com.foodaclic.livraison.classes.User;
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
 * Created by cyrilleguipie on 10/27/16.
 */

public class AuthService extends IntentService {
  private static final String TAG = AuthService.class.getName();
  private AndroidBus mBus;
  private Context mContext;


  @Override
  public void onCreate() {
    super.onCreate();

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  public AuthService() {
    super(TAG);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    mContext = this;
    mBus = AndroidBusProvider.getInstance();

    if(intent != null){
      Bundle bundle = intent.getExtras();
      if(bundle != null){
        String password = bundle.getString("password");
        String login = bundle.getString("login");

        auth(login, password);
      }
    }
  }



  private void auth(String login, String password) {
    mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_STARTED, ""));

      Call<ResponseBody> result = RestClient.getRestService().authenticate(login, password);
      result.enqueue(new Callback<ResponseBody>() {
        @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
          try{
            if(response.isSuccessful()){
            String result = response.body().string();
              Log.e("RESULT", result);
              if(!result.contains("<head>")){
            JsonNode object = Utils.toObject(result, JsonNode.class);
            if (object.get("error").asBoolean()) {

              mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, object.get("message").toString()));
            } else {
              User user = Utils.toObject(result, User.class);
              User.save(user);
              MainApplication.getPrefs().putInt(Constants.USER_ID, user.id);
              MainApplication.getPrefs().putString("userliv", user.userliv);
              MainApplication.getPrefs().putString("livcouverture", user.livcouverture);

              mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FINISHED_ALL, object.get("message").toString()));

            }}else{
                mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, "Verifiez votre forfait internet"));
              }
            }else {
              mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_server)));

            }
          }catch (IOException e){
            mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_server)));
            e.printStackTrace();
          }

        }

        @Override public void onFailure(Call<ResponseBody> call, Throwable t) {

          if(t instanceof IOException){
            mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_no_network)));

          }else{
            mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_server)));
          }

        }
      });

  }
}
