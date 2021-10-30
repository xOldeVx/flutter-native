//package com.battery;
//
//import android.bluetooth.BluetoothAdapter;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//public class BluetoothReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        final String action = intent.getAction();
//
//        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
//            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
//            switch (state) {
//                case BluetoothAdapter.STATE_OFF:
//                    Log.d("Android-Flutter", "STATE_OFF");
//                    break;
//                case BluetoothAdapter.STATE_TURNING_OFF:
//                    Log.d("Android-Flutter", "STATE_TURNING_OFF");
//                    break;
//                case BluetoothAdapter.STATE_ON:
//                    Log.d("Android-Flutter", "STATE_ON");
//                    break;
//                case BluetoothAdapter.STATE_TURNING_ON:
//                    Log.d("Android-Flutter", "STATE_TURNING_ON");
//                    break;
//            }
//        }
//    }
//}
