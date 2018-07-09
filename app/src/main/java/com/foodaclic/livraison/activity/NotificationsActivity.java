package com.foodaclic.livraison.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.foodaclic.livraison.R;
import com.foodaclic.livraison.adapter.NotifAdapter;
import com.foodaclic.livraison.classes.Notif;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;
import com.foodaclic.livraison.utils.event.NetworkOperationEvent;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

  private ArrayList<Notif> mObjects;
  private RecyclerView recyclerView;
  private NotifAdapter mAdapter;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notifications);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if(getSupportActionBar() != null){
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    initViews();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == android.R.id.home) {
      finish();
    }

    return super.onOptionsItemSelected(item);
  }

  private void initViews() {

    recyclerView = findViewById(R.id.recycler_notif);

    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

    mObjects = Notif.findAllOrderedByTitle();

    mAdapter = new NotifAdapter(this, mObjects);
    recyclerView.setAdapter(mAdapter);
  }

  @Override public void onPause() {
    super.onPause();
    AndroidBusProvider.getInstance().unregister(this);
  }

  @Override protected void onResume() {
    super.onResume();

    AndroidBusProvider.getInstance().register(this);
  }

  @Subscribe public void onNetworkOperationEvent(NetworkOperationEvent event) {


    if (event.hasFinishedAll()) {
      mObjects.clear();
      mObjects.addAll(Notif.findAllOrderedByTitle());
      mAdapter.notifyDataSetChanged();
    }
  }
}
