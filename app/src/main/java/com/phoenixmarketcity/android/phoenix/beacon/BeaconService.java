package com.phoenixmarketcity.android.phoenix.beacon;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.device.DeviceProfile;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.discovery.EventType;
import com.kontakt.sdk.android.ble.discovery.ibeacon.IBeaconDeviceEvent;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.rssi.RssiCalculators;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by neeraj.nayan on 06/08/15.
 */
public class BeaconService extends Service implements ProximityManager.ProximityListener {

    private HandlerThread mThread;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        private ProximityManager mDeviceManager;
        private ScanContext mScanContext;
        private List<EventType> mEventTypes = Arrays.asList(EventType.SPACE_ENTERED,
                EventType.SPACE_ABANDONED,
                EventType.DEVICE_DISCOVERED,
                EventType.DEVICES_UPDATE);

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            mDeviceManager = new ProximityManager(BeaconService.this);
            mDeviceManager.initializeScan(getOrCreateScanContext(), new OnServiceReadyListener() {
                @Override
                public void onServiceReady() {
                    mDeviceManager.attachListener(BeaconService.this);
                }

                @Override
                public void onConnectionFailure() {
                }
            });
        }

        private ScanContext getOrCreateScanContext() {
            if (mScanContext == null) {
                mScanContext = new ScanContext.Builder()
                        .setScanMode(ProximityManager.SCAN_MODE_BALANCED)
                        .setIBeaconScanContext(getIBeaconScanContext())
                        .setEddystoneScanContext(null)
                        .setActivityCheckConfiguration(ActivityCheckConfiguration.DEFAULT)
                        .setForceScanConfiguration(ForceScanConfiguration.DEFAULT)
                        .build();
            }

            return mScanContext;
        }

        private IBeaconScanContext getIBeaconScanContext() {
            return new IBeaconScanContext.Builder()
                    .setEventTypes(mEventTypes) //only specified events we be called on callback
                    .setDevicesUpdateCallbackInterval(TimeUnit.SECONDS.toMillis(2)) //how often DEVICES_UPDATE will be called
                    .setRssiCalculator(RssiCalculators.newLimitedMeanRssiCalculator(5))
                    .build();
        }
    }

    //we will be notified only with events that we added to ScanContext
    @Override
    public void onEvent(BluetoothDeviceEvent event) {
        switch (event.getEventType()) {
            case SPACE_ENTERED:
            case DEVICE_DISCOVERED:
            case DEVICE_LOST:
            case DEVICES_UPDATE:
                DeviceProfile deviceProfile = event.getDeviceProfile();
                if (deviceProfile == DeviceProfile.IBEACON) {
                    final IBeaconDeviceEvent beaconDeviceEvent = (IBeaconDeviceEvent) event;
                    List<IBeaconDevice> remoteDevices = beaconDeviceEvent.getDeviceList();
                    for(IBeaconDevice device : remoteDevices) {
                        Log.d("kontakt", "Name: " + device.getName());
                        Log.d("kontakt", "Address: " + device.getAddress());
                        Log.d("kontakt", "Distance: " + new DecimalFormat("#.##").format(device.getDistance()));
                        Log.d("kontakt", "Major: " + device.getMajor());
                        Log.d("kontakt", "Minor: " + device.getMinor());
                        Log.d("kontakt", "Rssi: " + device.getRssi());
                        Log.d("kontakt", "Tx Power: " + device.getTxPower());
                        Log.d("kontakt", "Proximity: " + device.getProximity().name());
                        Log.d("kontakt", "Firmware: " + device.getFirmwareVersion());
                        Log.d("kontakt", "Beacon Unique Id: " + device.getUniqueId());
                        Log.d("kontakt", "Proximity UUID: " + device.getProximityUUID().toString());
                    }
                }
                break;
        }
    }

    @Override
    public void onScanStart() {

    }

    @Override
    public void onScanStop() {

    }

    @Override
    public void onCreate() {
        // The service is being created
        mThread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        mThread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = mThread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        mThread.quit();
        super.onDestroy();
    }
}
