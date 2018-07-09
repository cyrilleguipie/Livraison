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
import io.realm.Realm;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayService extends IntentService {
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

  public PayService() {
    super("PayService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    mContext = this;
    mBus = AndroidBusProvider.getInstance();
    if (intent != null) {
      Bundle bundle = intent.getExtras();
      if(bundle != null){
        int id = bundle.getInt("id");


        pay(id);
      }
    }
  }

  private void pay(final int id) {
    mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_STARTED, ""));

    Call<ResponseBody> result = RestClient.getRestService().pay(id, MainApplication.getPrefs().getString("userliv", ""));
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
                final JsonNode data = Utils.toObject(object.get("data").toString(), JsonNode.class);
                Realm realm = MainApplication.getRealm();
                Commande commande = realm.where(Commande.class)
                    .equalTo("id", id)
                    .findFirst();
                realm.beginTransaction();
                commande.statut = data.get("statut").toString();
                commande.statut2 = data.get("statut2").toString();
                commande.statut3 = data.get("statut3").toString();
                realm.commitTransaction();



                mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_CHANGED));

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
