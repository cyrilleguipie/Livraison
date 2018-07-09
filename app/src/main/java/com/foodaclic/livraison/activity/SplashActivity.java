package com.foodaclic.livraison.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.foodaclic.livraison.BuildConfig;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.service.rest.RestClient;
import com.foodaclic.livraison.utils.Constants;

import com.foodaclic.livraison.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

  private Thread mSplashThread;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    mSplashThread = new Thread() {
      @Override
      public void run() {
        try {
          synchronized (this) {
            this.wait(3000);
          }
        } catch (InterruptedException ex) {
          // Ne rien faire
        }
        try{
          launch();

        }catch(Exception e){
          e.printStackTrace();
        }
      }
    };
    mSplashThread.start();

  }

  private void launch() {
    int userId = MainApplication.getPrefs().getInt(Constants.USER_ID, 0);
    if (userId > 0) {
      Log.e("spla", "main");
      startActivity(new Intent(SplashActivity.this, MainActivity.class));
    } else {
      Log.e("spla", "auth");
      startActivity(new Intent(SplashActivity.this, AuthActivity.class));
    }
    SplashActivity.this.finish();
  }

}
