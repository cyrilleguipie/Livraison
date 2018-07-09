package com.foodaclic.livraison.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.classes.Notif;
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
 * Created by cyrilleguipie on 10/15/17.
 */

public class NotificationService extends IntentService {

  private static final String TAG = NotificationService.class.getName();

  private AndroidBus mBus;
  private Context mContext;

  @Override
  public void onCreate() {
    Log.d(TAG, "Created.");
    //BusProvider.getInstance().register(this);
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "Destroyed.");
    //BusProvider.getInstance().unregister(this);
    super.onDestroy();
  }

  public NotificationService() {
    super(TAG);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Log.d(TAG, "Started.");
    mContext = this;
    mBus = AndroidBusProvider.getInstance();

    if (intent != null) {
      Bundle bundle = intent.getExtras();
      if(bundle != null){
        //auth(bundle.getLong("notifId"));
      }
    }
  }

  //private void auth(long notifId) {
  //  mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_STARTED, ""));
  //
  //  Call<ResponseBody> result = RestClient.getRestService().notif(MainApplication.getPrefs().getLong(Constants.USER_ID, 0), notifId);
  //  result.enqueue(new Callback<ResponseBody>() {
  //    @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
  //      try{
  //        if(response.isSuccessful()){
  //          String result = response.body().string();
  //          if(!result.contains("<head>")){
  //            JsonNode object = Utils.toObject(result, JsonNode.class);
  //            if (object.get("error").asBoolean()) {
  //
  //              mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, object.get("message").toString()));
  //            } else {
  //              Notif notif = Utils.toObject(object.get("data").get("notif").toString(), Notif.class);
  //              Notif.save(notif);
  //              mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FINISHED_ALL, object.get("message").toString()));
  //
  //            }}else{
  //            mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, "Verifiez votre forfait internet"));
  //          }
  //        }else {
  //          mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_server)));
  //
  //        }
  //      }catch (IOException e){
  //        mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_server)));
  //        e.printStackTrace();
  //      }
  //
  //    }
  //
  //    @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
  //
  //      if(t instanceof IOException){
  //        mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_no_network)));
  //
  //      }else{
  //        mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_FAILED, getString(R.string.error_server)));
  //      }
  //
  //    }
  //  });
  //
  //}
}
