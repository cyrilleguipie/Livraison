package com.foodaclic.livraison.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.foodaclic.livraison.utils.event.AndroidBus;
import com.foodaclic.livraison.utils.event.AndroidBusProvider;

/**
 * Created by cyrilleguipie on 2/12/18.
 */

public class BluetoothReceiver extends BroadcastReceiver {
  private AndroidBus mBus;

  @Override
  public void onReceive(Context context, Intent intent) {
    mBus = AndroidBusProvider.getInstance();
    final String action = intent.getAction();
    //
    //if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
    //  final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
    //  switch(state) {
    //    case BluetoothAdapter.STATE_OFF:
    //      MainApplication.getPrefs().putBoolean(Constants.PREFS_BT, false);
    //      break;
    //    case BluetoothAdapter.STATE_TURNING_OFF:
    //      MainApplication.getPrefs().putBoolean(Constants.PREFS_BT, false);
    //      break;
    //    case BluetoothAdapter.STATE_ON:
    //      MainApplication.getPrefs().putBoolean(Constants.PREFS_BT, true);
    //      break;
    //    case BluetoothAdapter.STATE_TURNING_ON:
    //      MainApplication.getPrefs().putBoolean(Constants.PREFS_BT, false);
    //      break;
    //  }
    //  mBus.post(new NetworkOperationEvent(NetworkOperationEvent.HAS_CHANGED));
    //}
  }
}
