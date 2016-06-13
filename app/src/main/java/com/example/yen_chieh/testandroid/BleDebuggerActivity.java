package com.example.yen_chieh.testandroid;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yen-chieh on 6/11/16.
 */
public class BleDebuggerActivity extends Activity {

    int REQUEST_ENABLE_BT = 1;
    //    TextView foundText;
    BluetoothAdapter mBluetoothAdapter;
    String newDeviceFound = "";
    List<BluetoothDevice> deviceList = new ArrayList<>();
    ListView deviceView;
    private volatile boolean discoveryFinished;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_debugger);

//        foundText = (TextView) findViewById(R.id.foundText);
        deviceView = (ListView) findViewById(R.id.deviceListView);
        deviceView.setAdapter(new CustomAdapter(this, deviceList));
        handler = new Handler();

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        ensureDiscoverable();

    }

    private void ensureDiscoverable() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        String deviceName = "";
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceName += " : " + device.getName();
            }

//            foundText.setText(deviceName);
        } else {
//            foundText.setText("No pared Device");
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                discoveryFinished = true;
                mBluetoothAdapter.stopLeScan(leScanCallback);
            }
        }, 25000);

        discoveryFinished = false;
        mBluetoothAdapter.startLeScan(leScanCallback);


    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {

            if (bluetoothDevice != null) {

                if(bluetoothDevice.getName() != null){
                    if(bluetoothDevice.getName().equals("Keyfobdemo")){
                        Log.d("On Le Scan", "_______________________________");
                        Log.d("state", String.valueOf(bluetoothDevice.getBondState()));
                        Log.d("Find", bluetoothDevice.toString());
                        Log.d("Name", bluetoothDevice.getName());

                        deviceList.add(bluetoothDevice);
                        BluetoothGatt bluetoothGatt = bluetoothDevice.connectGatt(BleDebuggerActivity.this, false, btleGattCallback);


/*                        if(bluetoothDevice.getUuids() !=null && bluetoothDevice.getUuids().length > 0){
                            for (ParcelUuid uuid : bluetoothDevice.getUuids()) {

                                Log.d("Uuid", uuid.getUuid().toString());
                            }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    deviceList.add(bluetoothDevice);
                                    deviceView.invalidate();
                                }
                            });
                        }*/
                    }
                }
            }
        }

    };

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            // this will get called anytime you perform a read or write characteristic operation
            Log.d("Characteristic", characteristic.toString());
        }

        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
            // this will get called when a device connects or disconnects
            Log.d("Connection Status", String.valueOf(newState));
            if(newState == BluetoothProfile.STATE_CONNECTED){
                Log.d("connection Status", "Connected");
                gatt.discoverServices();
            }
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
            // this will get called after the client initiates a 			BluetoothGatt.discoverServices() call

            for (BluetoothGattService service : gatt.getServices()) {
                Log.d("Service UUID", service.getUuid().toString());

                for(BluetoothGattCharacteristic characteristic: service.getCharacteristics()){
                    Log.d("Character UUID", characteristic.getUuid().toString());

                }
            }





        }
    };

}
