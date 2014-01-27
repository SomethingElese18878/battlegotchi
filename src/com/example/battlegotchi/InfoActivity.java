package com.example.battlegotchi;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//get gotchi data from intent and add TextViews to LinearLayout
		Intent intent = getIntent();
		LinearLayout layout = (LinearLayout) findViewById(R.id.info_linearLayout);
		
		TextView tv_health = new TextView(this);
		tv_health.setText("Health: " +String.valueOf(intent.getIntExtra("gotchiHealth", 100)));
		layout.addView(tv_health);
		
		TextView tv_strength = new TextView(this);
		tv_strength.setText("Strength: " +String.valueOf(intent.getIntExtra("gotchiStrength", 1)));
		layout.addView(tv_strength);
		
		TextView tv_poo = new TextView(this);
		tv_poo.setText("Made Poo: " +String.valueOf(intent.getBooleanExtra("gotchiMadePoo", false)));
		layout.addView(tv_poo);
		
		TextView tv_angry = new TextView(this);
		tv_angry.setText("Is Angry: " +String.valueOf(intent.getBooleanExtra("gotchiIsAngry", false)));
		layout.addView(tv_angry);
		
		TextView tv_stage = new TextView(this);
		tv_stage.setText("Stage: " +String.valueOf(intent.getIntExtra("gotchiStage", 1)));
		layout.addView(tv_stage);
		
		TextView tv_age = new TextView(this);
		tv_age.setText("Age: " +String.valueOf(intent.getLongExtra("gotchiAge", 1)));
		layout.addView(tv_age);
		
		TextView tv_weight = new TextView(this);
		tv_weight.setText("Weight: " +String.valueOf(intent.getIntExtra("gotchiWeight", 1)));
		layout.addView(tv_weight);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
