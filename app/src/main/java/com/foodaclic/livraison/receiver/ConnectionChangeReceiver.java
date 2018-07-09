

package com.foodaclic.livraison.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.utils.Constants;
import com.foodaclic.livraison.utils.event.AndroidBus;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;
import com.foodaclic.livraison.utils.event.NetworkOperationEvent;

public class ConnectionChangeReceiver extends BroadcastReceiver {

    private boolean mConnection = false;
    private AndroidBus mBus;

    @Override
    public void onReceive(Context context, Intent intent) {
        mBus = AndroidBusProvider.getInstance();
        if (mConnection && intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            Toast.makeText(context, "Mode hors-ligne", Toast.LENGTH_SHORT).show();
            mConnection = false;
        } else if (!mConnection && !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            mConnection = true;
            Toast.makeText(context, "Mode Internet", Toast.LENGTH_SHORT).show();
                   //context.startService(new Intent(context, SyncService.class));
        }
        MainApplication.getPrefs().putBoolean(Constants.CONNECTED, mConnection);
        mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_CHANGED));

    }
}