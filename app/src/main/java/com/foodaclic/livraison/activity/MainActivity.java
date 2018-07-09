package com.foodaclic.livraison.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.R;
import com.foodaclic.livraison.adapter.AllAdapter;
import com.foodaclic.livraison.classes.Commande;
import com.foodaclic.livraison.classes.User;
import com.foodaclic.livraison.service.DataFetchService;
import com.foodaclic.livraison.service.LocationTrack;
import com.foodaclic.livraison.service.PayService;
import com.foodaclic.livraison.service.gcm.GcmRegistrationService;
import com.foodaclic.livraison.utils.Constants;
import com.foodaclic.livraison.utils.CustomDialog;
import com.foodaclic.livraison.utils.CustomTypefaceSpan;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;
import com.foodaclic.livraison.utils.event.NetworkOperationEvent;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements  SwipeRefreshLayout.OnRefreshListener{
 CustomDialog dialog;
    private TextView label_total, label_pourboire, label_username;
    private User user;

    private RecyclerView recyclerAll;

    private AllAdapter mAdapter;
    List<Commande> expeditions = new ArrayList<>();
    private SwipeRefreshLayout swipeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = User.findById(MainApplication.getPrefs().getInt(Constants.USER_ID, 0));
        initViews();
        handleViewEvents();
        updateHeader();
        startService(new Intent(this, GcmRegistrationService.class));
        //startService(new Intent(MainActivity.this, DataFetchService.class));
        LocationTrack locationTrack = new LocationTrack(this);
        //initViews();
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Commande.deleteAll();
            User.deleteAll();
            MainApplication.getPrefs().putInt(Constants.USER_ID, 0);
            MainApplication.getPrefs().putString("userliv", "");
            MainApplication.getPrefs().putString("livcouverture", "");
            MainApplication.getPrefs().putString("total_pb", "");
            MainApplication.getPrefs().putString("liv", "");
            MainApplication.getPrefs().putString("todo", "");
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void updateHeader(){
        if(user != null){
            label_username.setText(user.userliv);
            label_total.setText("Liv :"+MainApplication.getPrefs().getString("liv", ""));
            label_pourboire.setText("Pb :"+MainApplication.getPrefs().getString("total_pb", ""));
        }

    }



    private void initViews() {

        recyclerAll =  findViewById(R.id.recycler_all);
        recyclerAll.setLayoutManager(new LinearLayoutManager(recyclerAll.getContext()));
        expeditions = Commande.findAllOrderedByTitle();

        mAdapter = new AllAdapter(this, expeditions);
        swipeLayout =  findViewById(R.id.swipe_all);
        label_pourboire = findViewById(R.id.label_pourboire);
        label_total = findViewById(R.id.label_total);
        label_username = findViewById(R.id.label_username);

        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeResources(R.color.redLogo, R.color.black, R.color.redLogo, R.color.black);

        recyclerAll.setAdapter(mAdapter);
        //updateHeader();
        Log.e(MainApplication.getPrefs().getString("userliv","")
            , MainApplication.getPrefs().getString("livcouverture", ""));
    }

    private void handleViewEvents() {

    }

    @Override public void onRefresh() {
        startService(new Intent(MainActivity.this, DataFetchService.class));

    }
    @Override public void onPause() {
        super.onPause();
        AndroidBusProvider.getInstance().unregister(this);
    }

    @Override protected void onResume() {

        //sendOrder();
        AndroidBusProvider.getInstance().register(this);
        //startService(new Intent(MainActivity.this, DataFetchService.class));

        super.onResume();
    }

    @Subscribe public void onNetworkOperationEvent(NetworkOperationEvent event) {

        // Log.i(LOG_TAG, "I received an event : " + event.getClass().getName() + " : " + event.getMessage());
        if (event.hasStarted()) {
            Toast.makeText(this, "Chargement des donnees ...", Toast.LENGTH_SHORT).show();
        } else if (event.hasFinishedOne()) {
          updateHeader();
            takePay((int)event.getId());
        } else if(event.hasChanged()){
          updateHeader();
            startService(new Intent(MainActivity.this, DataFetchService.class));
        } else if (event.hasFinishedAll()) {
            swipeLayout.setRefreshing(false);
            expeditions.clear();
            expeditions.addAll(Commande.findAllOrderedByTitle());
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Donnees chargees", Toast.LENGTH_SHORT).show();
            updateHeader();
        } else if (event.hasFailed()) {
            // hideProgressBar();
            swipeLayout.setRefreshing(false);
            updateHeader();
            Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    public void takePay(final int id){

        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_pay, null);
        dialog = new CustomDialog(MainActivity.this, view);

        ImageView imgClose =  dialog.findViewById(R.id.img_close);

        Button btn_ok =  dialog.findViewById(R.id.btn_ok);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startService(new Intent(MainActivity.this, PayService.class)
                    .putExtra("id", id));
                dialog.dismiss();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }




}
