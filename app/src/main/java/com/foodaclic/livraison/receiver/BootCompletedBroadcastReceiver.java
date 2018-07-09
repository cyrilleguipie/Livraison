
package com.foodaclic.livraison.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.foodaclic.livraison.MainApplication;
import com.foodaclic.livraison.utils.Constants;

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(MainApplication.getPrefs().getLong(Constants.USER_ID, 0) > 0){
           // context.startService(new Intent(context, SyncService.class));
        }
    }

}
