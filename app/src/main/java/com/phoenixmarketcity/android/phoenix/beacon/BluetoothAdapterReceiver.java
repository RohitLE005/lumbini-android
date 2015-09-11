package com.phoenixmarketcity.android.phoenix.beacon;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by neeraj.nayan on 29/07/15.
 */
public class BluetoothAdapterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

            if (btState == BluetoothAdapter.STATE_ON) {
                // Start the beacon service
                Intent serviceIntent = new Intent(context, BeaconService.class);
                context.startService(serviceIntent);
            } else if (btState == BluetoothAdapter.STATE_OFF) {
                Intent serviceIntent = new Intent(context, BeaconService.class);
                context.stopService(serviceIntent);
            }
        }
    }
}
