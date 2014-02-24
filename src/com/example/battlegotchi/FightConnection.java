package com.example.battlegotchi;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.ArrayAdapter;

public class FightConnection{
	
	private static final String TAG = FightConnection.class.getSimpleName();

	MainActivity mainActivity;
	BluetoothAdapter mBluetoothAdapter;
	ArrayAdapter<String> mArrayAdapter;
	static final int REQUEST_ENABLE_BT = 1;

	public FightConnection(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	public void enableBluetooth(){
		//Setting Up Bluetooth
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
		}
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    mainActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		//Bluetooth ist active - go on!
		findDevice();
		
	}
	
	public void findDevice(){
		Log.d(TAG, "findDevice");
		
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mainActivity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

//		//Querying paired devices
//		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//		// If there are paired devices
//		if (pairedDevices.size() > 0) {
//			Log.d(TAG, "pairedDevices:");
//			System.out.println(pairedDevices.size());
//		    // Loop through paired devices
//		    for (BluetoothDevice device : pairedDevices) {
//		        // Add the name and address to an array adapter to show in a ListView
//		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//		    }
//		}
		
		boolean deviceDiscovered = mBluetoothAdapter.startDiscovery();
		
		if(deviceDiscovered){
			Log.d(TAG, "deviceDiscovered");
		}else{
			Log.d(TAG, "else: makeDiscoverable");
			// Make device discoverable.
			Intent discoverableIntent = new
					Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
					mainActivity.startActivity(discoverableIntent);
		}
	}
	
	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	        	Log.d(TAG, "DEVICE FOUND");
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a ListView
	            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	        }
	    }
	};
	
}
