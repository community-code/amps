package com.communitycode.amps.main;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.HashMap;

public class UsbTracker {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final String ACTION_USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";

    private final BatteryInfoInterface mBatteryInfoInterface;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            //call method to set up device communication
                            UsbConfiguration configuration = device.getConfiguration(0);
                            int maxpower = configuration.getMaxPower();
//                            mBatteryInfoInterface.setUsbMaxPower(maxpower);
                        }
                    }
                    else {
                        Log.d("Debug", "permission denied for device " + device);
                    }
                }
            }
        }
    };
    private final Context mCtx;

    UsbTracker(Context ctx, BatteryInfoInterface batteryInfoInterface) {
        mBatteryInfoInterface = batteryInfoInterface;
        mCtx = ctx;
    }

    public void start() {
        Intent intent = mCtx.registerReceiver(mUsbReceiver, new IntentFilter(ACTION_USB_ATTACHED));
    }

    public void stop() {
        mCtx.unregisterReceiver(mUsbReceiver);
    }
    
    public void checkAllDevices() {
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(mCtx, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        mCtx.registerReceiver(mUsbReceiver, filter);

        UsbManager manager = (UsbManager) mCtx.getSystemService(Context.USB_SERVICE);
        if (manager != null) {
            HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
            Log.d("acodd", "device list size " + deviceList.size());
            for (UsbDevice device : deviceList.values()) {
                manager.requestPermission(device, mPermissionIntent);
            }
        }
    }
}
