package com.battery;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity implements MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private static final String BATTERY_CHANNEL = "com.battery/battery";
    private static final String BLUETOOTH_STATUS_CHANNEL = "com.battery/bluetooth";
    private String bluetoothState = "Unknown";
    private BroadcastReceiver bluetoothReceiver;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), BATTERY_CHANNEL).setMethodCallHandler(this);
        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), BLUETOOTH_STATUS_CHANNEL).setStreamHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("getBatteryLevel")) {
            int batteryLevel = getBatteryLevel();

            if (batteryLevel != -1) {
                result.success(batteryLevel);
            } else {
                result.error("UNAVAILABLE", "Battery level not available.", null);
            }
        } else {
            result.notImplemented();
        }
    }

    private int getBatteryLevel() {
        int batteryLevel = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(getApplicationContext()).
                    registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }
        return batteryLevel;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        events.success("bluetoothState");
        Map<Object, Runnable> listeners = new HashMap<>();
        bluetoothReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();

                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                    switch (state) {
                        case BluetoothAdapter.STATE_OFF:
                            Log.d("Android-Flutter", "STATE_OFF");
                            bluetoothState = "OFF";
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.d("Android-Flutter", "STATE_TURNING_OFF");
                            bluetoothState = "TURING OFF";
                            break;
                        case BluetoothAdapter.STATE_ON:
                            Log.d("Android-Flutter", "STATE_ON");
                            bluetoothState = "ON";
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.d("Android-Flutter", "STATE_TURNING_ON");
                            bluetoothState = "TURING ON";
                            break;
                    }
//                    listeners.put(arguments, new Runnable() {
//                        @Override
//                        public void run() {
//                            if (listeners.containsKey(arguments)) {
//                                // Send some value to callback
//                                events.success(bluetoothState);
//                                Log.d("Android-Flutter", "SUCCESS");
//                            }
//                        }
//                    });
                    events.success(bluetoothState);
                    Log.d("Android-Flutter", "SUCCESS");
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver, filter);
    }

    @Override
    public void onCancel(Object arguments) {
        Log.d("Android-Flutter", "DISCONNECT!");
        unregisterReceiver(bluetoothReceiver);
        bluetoothReceiver = null;
    }
}
