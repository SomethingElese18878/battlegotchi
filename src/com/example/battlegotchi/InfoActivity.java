package com.example.battlegotchi;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// hide actionbar
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		setContentView(R.layout.activity_info);
		// Show the Up button in the action bar.
		setupActionBar();

		// get values and set correct backgrounds for imageviews
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		ImageView gotchiHunger = (ImageView) findViewById(R.id.info_hunger_value);
		gotchiHunger.setBackgroundResource(getResources().getIdentifier(
				"hearts_" + extras.getInt("gotchiHunger", 1), "drawable",
				getPackageName()));

		ImageView gotchiStrength = (ImageView) findViewById(R.id.info_strength_value);
		gotchiStrength.setBackgroundResource(getResources().getIdentifier(
				"hearts_" + extras.getInt("gotchiStrength", 1), "drawable",
				getPackageName()));
		
		ImageView gotchiEnergy = (ImageView) findViewById(R.id.info_energy_value);
		gotchiEnergy.setBackgroundResource(getResources().getIdentifier(
				"energy_" + extras.getInt("gotchiEnergy", 1), "drawable",
				getPackageName()));
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME,
					MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("cameFromInfoActivity", true);
			editor.commit();
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
