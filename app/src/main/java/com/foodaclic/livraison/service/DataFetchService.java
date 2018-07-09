package com.foodaclic.livraison.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.classes.Commande;
import com.foodaclic.livraison.classes.User;
import com.foodaclic.livraison.service.rest.RestClient;
import com.foodaclic.livraison.utils.Constants;
import com.foodaclic.livraison.utils.Utils;
import com.foodaclic.livraison.utils.event.AndroidBus;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;

import com.foodaclic.livraison.utils.event.NetworkOperationEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by cyrilleguipie on 10/24/16.
 */

public class DataFetchService extends IntentService {
  private static final String TAG = DataFetchService.class.getName();
  private AndroidBus mBus;
  private Context mContext;

  @Override public void onCreate() {
    super.onCreate();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  public DataFetchService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {
    mContext = this;
    mBus = AndroidBusProvider.getInstance();

    if (intent != null) {

        fetch();

    }
  }




  private void fetch() {
    mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_STARTED, ""));

    Call<ResponseBody> result = RestClient.getRestService().fetch(MainApplication.getPrefs().getString("userliv","")
        , MainApplication.getPrefs().getString("livcouverture", ""));
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
                if (object.has("data")) {
                  if (object.get("data").toString() != null && !object.get("data")
                      .toString()
                      .isEmpty()) {
                    Commande.deleteAll();
                    List<Commande> commandes =
                        Utils.toObjects(object.get("data").toString(), Commande.class);
                    Commande.saveAll(commandes);
                    MainApplication.getPrefs().putString("total_pb", object.get("total_pb").toString());
                    MainApplication.getPrefs().putString("liv", object.get("liv").toString());
                    MainApplication.getPrefs().putString("todo", object.get("todo").toString());
                  }}

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
