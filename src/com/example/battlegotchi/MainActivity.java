package com.example.battlegotchi;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	// name of the shared reference
	public final static String PREFS_NAME = "gotchidata";
	
	// time in seconds for gotchi to poo, when user doesn't interact
	final int POO_TIME = 10;
	private Gotchi gotchi;
	FightConnection fightConnection;
	ArrayAdapter<String> mArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.out.println("mannn");

		// hide actionbar
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();

		gotchi = new Gotchi();

		// if the app is run for the first time, set a timestamp (needed to
		// calculate gotchi age)
		long firstRunTimestamp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
				.getLong("firstRunTimestamp", 0);
		if (firstRunTimestamp == 0) {
			getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
					.putLong("firstRunTimestamp", System.currentTimeMillis())
					.commit();
		}

		loadGotchiData();

		ImageView mainSequence = (ImageView) findViewById(R.id.imageViewGotchi);
		setPooAnimation(mainSequence);
		AnimationDrawable gotchiAnimation = (AnimationDrawable) mainSequence
				.getBackground();

		gotchiAnimation.setVisible(false, true);
		gotchiAnimation.start();
	}

	@Override
	protected void onPause() {
		super.onPause();

		saveGotchiData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		changeAllButtonStates(true);

		// check if user came from info activity. if so -> don't change main
		// animation!
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		if (!settings.getBoolean("cameFromInfoActivity", false)) {
			ImageView mainSequence = (ImageView) findViewById(R.id.imageViewGotchi);
			setPooAnimation(mainSequence);
			AnimationDrawable gotchiAnimation = (AnimationDrawable) mainSequence
					.getBackground();

			gotchiAnimation.setVisible(false, true);
			gotchiAnimation.start();
		}

		// reset cameFromInfoActivity flag
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("cameFromInfoActivity", false);
		editor.commit();
	}

	/**
	 * called when an action button is pressed
	 * 
	 * @param view
	 *            the view (the button which was pressed)
	 */
	public void onAction(View view) {
		ImageView gotchiView = (ImageView) findViewById(R.id.imageViewGotchi);
		changeAllButtonStates(false);
		
		int resId;
		switch (view.getId()) {
		case R.id.btnInfo:
			// TODO: deactivate action buttons BEFORE switch (for now it has to
			// be in "case" for testing purposes)

			// deactivate action buttons

			// opens new activity to display gotchi info
			Intent intent = new Intent(this, InfoActivity.class);

			// put gotchi data as extras (maybe solution with "parcelables" is
			// better?)
			intent.putExtra("gotchiHunger", gotchi.getHunger());
			intent.putExtra("gotchiStrength", gotchi.getStrength());
			intent.putExtra("gotchiIsAngry", gotchi.getIsAngry());
			intent.putExtra("gotchiMadePoo", gotchi.getMadePoo());
			intent.putExtra("gotchiStage", gotchi.getStage());
			intent.putExtra("gotchiAge", gotchi.getAge(getSharedPreferences(
					PREFS_NAME, MODE_PRIVATE)));
			intent.putExtra("gotchiWeight", gotchi.getWeight());
			intent.putExtra("gotchiEnergy", gotchi.getEnergy());

			startActivity(intent);
			break;
		case R.id.btnFeed:
			changeAllButtonStates(false);
			if (gotchi.getHunger() < 4) {
				gotchi.setHunger(gotchi.getHunger() + 1);
				gotchi.setWeight(gotchi.getWeight() +1);
				
				// alter background resource depending on which stage the
				// gotchi currently is
				resId = getResources().getIdentifier(
						"stage" + gotchi.getStage() + "_animationlist_eat",
						"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			} else {
				resId = getResources().getIdentifier(
						"stage" + gotchi.getStage() + "_animationlist_no",
						"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			}
			break;
		case R.id.btnVitamin:
			changeAllButtonStates(false);
			if (gotchi.getStrength() < 4) {
				gotchi.setStrength(gotchi.getStrength() + 1);

			// alter background resource depending on which stage the
			// gotchi currently is
			resId = getResources().getIdentifier(
					"stage" + gotchi.getStage() + "_animationlist_vitamin",
					"drawable", getPackageName());
			gotchiView.setBackgroundResource(resId);
			} else {
				resId = getResources().getIdentifier(
						"stage" + gotchi.getStage() + "_animationlist_no",
						"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			}
			break;
		case R.id.btnVitamin:
			if (gotchi.getStrength() < 4) {
				gotchi.setStrength(gotchi.getStrength() + 1);
			}

			// alter background resource depending on which stage the
			// gotchi currently is
			resId = getResources().getIdentifier(
					"stage" + gotchi.getStage() + "_animationlist_vitamin",
					"drawable", getPackageName());
			gotchiView.setBackgroundResource(resId);
			break;
		case R.id.btnTrain:
			changeAllButtonStates(false);
			// starts training animation and increases gotchi experience, if
			// projectile hits enemy

			// create random booleans for shooting up or down (own projectile
			// and enemy projectile)
			Random randomGen = new Random();
			boolean enemyShootUp = randomGen.nextBoolean();
			boolean selfShootUp = randomGen.nextBoolean();

			if (enemyShootUp && selfShootUp) {
				resId = getResources()
						.getIdentifier(
								"stage" + gotchi.getStage()
										+ "_animationlist_train_uu",
								"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			}
			if (enemyShootUp && !selfShootUp) {
				gotchi.setExperience(gotchi.getExperience() + 1);
				resId = getResources()
						.getIdentifier(
								"stage" + gotchi.getStage()
										+ "_animationlist_train_ud",
								"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			}
			if (!enemyShootUp && selfShootUp) {
				gotchi.setExperience(gotchi.getExperience() + 1);
				resId = getResources()
						.getIdentifier(
								"stage" + gotchi.getStage()
										+ "_animationlist_train_du",
								"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			}
			if (!enemyShootUp && !selfShootUp) {
				resId = getResources()
						.getIdentifier(
								"stage" + gotchi.getStage()
										+ "_animationlist_train_dd",
								"drawable", getPackageName());
				gotchiView.setBackgroundResource(resId);
			}

			break;
		case R.id.btnFight:
			changeAllButtonStates(false);
			resId = getResources().getIdentifier("battle", "drawable",
					getPackageName());
			gotchiView.setBackgroundResource(resId);
			break;
		case R.id.btnHeal:
			changeAllButtonStates(false);
			resId = getResources().getIdentifier(
					"stage" + gotchi.getStage() + "_animationlist_no",
					"drawable", getPackageName());
			gotchiView.setBackgroundResource(resId);
			break;
		case R.id.btnPoo:
			if (gotchi.madePoo) {
				gotchi.setMadePoo(false);
				restartMainAnimation();
				// onImageViewClick(gotchiView);
			}
			break;
		case R.id.btnLight:
			if(gotchi.getStage() == 1){
				gotchi.setStage(2);
			} else {
				gotchi.setStage(1);
			}
			restartMainAnimation();
			break;
		default:
			break;
		}

		if (view.getId() != R.id.btnInfo && view.getId() != R.id.btnPoo && view.getId() != R.id.btnLight) {

			AnimationDrawable gotchiAnimation = (AnimationDrawable) gotchiView
					.getBackground();
			int animationDuration = getTotalAnimationDuration(gotchiAnimation);

			gotchiAnimation.setVisible(false, true);
			gotchiAnimation.start();

			waitUntilAnimationIsFinished(animationDuration);
		}

	}


	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		Log.d(TAG, "onActivityResult");
	    switch (requestCode) {
	    case FightConnection.REQUEST_ENABLE_BT:
	        if (resultCode == Activity.RESULT_OK) {
	        	Log.d(TAG, "RESULT OK");
	        	fightConnection.findDevice();
	        } else if(resultCode == Activity.RESULT_CANCELED) {

	        } else {
	            // User did not enable Bluetooth or an error occurred
	        }
	    break;
	    
	    default:
	        break;
	    }
	}
	

	/**
	 * called when the ImageView itself gets clicked
	 * 
	 * @param view
	 *            the ImageView
	 */
	public void onImageViewClick(View view) {
		// ImageView gotchiView = (ImageView)
		// findViewById(R.id.imageViewGotchi);
		if (gotchi.madePoo) {
			// alter background resource depending on which stage the
			// gotchi currently is

			int resId = getResources().getIdentifier(
					"stage" + gotchi.getStage() + "_animationlist_main",
					"drawable", getPackageName());
			view.setBackgroundResource(resId);
			AnimationDrawable gotchiAnimation = (AnimationDrawable) view
					.getBackground();

			gotchiAnimation.setVisible(false, true);
			gotchiAnimation.start();

			gotchi.setMadePoo(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();

		saveGotchiData();
	}

	/**
	 * calculates the total duration of an animation 100ms are substracted from
	 * total time, to make shure, that animation doesn't restart for a short
	 * time
	 * 
	 * @param anim
	 *            the animation
	 * @return the total duration of the animation
	 */
	private int getTotalAnimationDuration(AnimationDrawable anim) {
		int duration = 0;

		for (int i = 0; i < anim.getNumberOfFrames(); i++) {
			duration = duration + anim.getDuration(i);
		}

		return duration - 100;
	}

	/**
	 * restarts the main (idle) animation and
	 */
	public void restartMainAnimation() {
		// changes to UI may only be done in main thread, use runOnUiThread
		runOnUiThread(new Runnable() {
			public void run() {
				ImageView mainSequence = (ImageView) findViewById(R.id.imageViewGotchi);
				// TODO: alter background resource depending on which stage the
				// gotchi
				// currently is
				int resId = getResources().getIdentifier(
						"stage" + gotchi.getStage() + "_animationlist_main",
						"drawable", getPackageName());
				mainSequence.setBackgroundResource(resId);
				AnimationDrawable gotchiAnimation = (AnimationDrawable) mainSequence
						.getBackground();

				gotchiAnimation.setVisible(false, true);
				gotchiAnimation.start();

				// reactivate action buttons
				changeAllButtonStates(true);
			}
		});
	}

	/**
	 * waits until an animation is finished
	 * 
	 * @param duration
	 *            the duration of the animation
	 */
	public void waitUntilAnimationIsFinished(int duration) {
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				restartMainAnimation();
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, duration);
	}

	/**
	 * Sets the poo animation, if user wasn't interacting with gotchi for a
	 * certain time. Otherwise the standard animation is set.
	 * 
	 * @param mainSequence
	 *            the main image view
	 */
	public void setPooAnimation(ImageView mainSequence) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		long timeSinceLastInteraction = System.currentTimeMillis()
				- settings.getLong("lastTimePlayed", 0);
		if (gotchi.madePoo || timeSinceLastInteraction >= (POO_TIME * 1000)) {
			// determine how much poo the gotchi made
			String pooSize = "small";
			if (timeSinceLastInteraction <= (POO_TIME * 2 * 1000)) {
				pooSize = "small";
				updateHunger(1);
			}

			else if ((timeSinceLastInteraction > (POO_TIME * 2 * 1000))
					&& (timeSinceLastInteraction < (POO_TIME * 3 * 1000))) {
				pooSize = "medium";
				updateHunger(2);
			}

			else if (timeSinceLastInteraction >= (POO_TIME * 3 * 1000)) {
				pooSize = "big";
				updateHunger(3);
			}

			// alter background resource depending on which stage the
			// gotchi currently is and how much poo it made
			int resId = getResources().getIdentifier(
					"stage" + gotchi.getStage() + "_animationlist_poo_"
							+ pooSize, "drawable", getPackageName());
			mainSequence.setBackgroundResource(resId);
			gotchi.setMadePoo(true);
		} else {
			// alter background resource depending on which stage the
			// gotchi currently is
			int resId = getResources().getIdentifier(
					"stage" + gotchi.getStage() + "_animationlist_main",
					"drawable", getPackageName());
			mainSequence.setBackgroundResource(resId);
		}
	}
	
	/**
	 * sets new hunger value
	 * 
	 * @param hearts to substract from current hunger value
	 */
	private void updateHunger(int hearts){
		gotchi.setHunger(gotchi.getHunger() - hearts);
		
		//if hunger value is below 0, set hunger to 0
		if(gotchi.getHunger() < 0){
			gotchi.setHunger(0);
		}
	}

	/**
	 * saves all gotchi data as shared preferences (persistence)
	 */
	public void saveGotchiData() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("gotchiHunger", gotchi.getHunger());
		editor.putInt("gotchiStrength", gotchi.getStrength());
		editor.putBoolean("gotchiMadePoo", gotchi.getMadePoo());
		editor.putBoolean("gotchiIsAngry", gotchi.getIsAngry());
		editor.putInt("gotchiStage", gotchi.getStage());
		editor.putInt("gotchiWeight", gotchi.getWeight());
		editor.putInt("gotchiExperience", gotchi.getExperience());
		// time stamp to determine when game was played the last time
		editor.putLong("lastTimePlayed", System.currentTimeMillis());

		editor.commit();
	}

	/**
	 * loads and sets the last saved gotchi data
	 */
	public void loadGotchiData() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		gotchi.setHunger(settings.getInt("gotchiHunger", 1));
		gotchi.setStrength(settings.getInt("gotchiStrength", 1));
		gotchi.setIsAngry(settings.getBoolean("gotchiIsAngry", false));
		gotchi.setMadePoo(settings.getBoolean("gotchiMadePoo", false));
		gotchi.setStage(settings.getInt("gotchiStage", 1));
		gotchi.setWeight(settings.getInt("gotchiWeight", 1));
		gotchi.setExperience(settings.getInt("gotchiExperience", 1));
	}

	/**
	 * Clears all gotchi data to reset the game stats
	 */
	public void clearGotchiData() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.clear();
		editor.commit();

		gotchi.setHunger(settings.getInt("gotchiHunger", 1));
		gotchi.setStrength(settings.getInt("gotchiStrength", 1));
		gotchi.setIsAngry(settings.getBoolean("gotchiIsAngry", false));
		gotchi.setMadePoo(settings.getBoolean("gotchiMadePoo", false));
		gotchi.setStage(settings.getInt("gotchiStage", 1));
		gotchi.setWeight(settings.getInt("gotchiWeight", 1));
	}

	/**
	 * enabled or disables all action buttons
	 * 
	 * @param enabled
	 *            whether the buttons shall be enabled or disabled
	 */
	public void changeAllButtonStates(boolean enabled) {
		((ImageButton) findViewById(R.id.btnInfo)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnFeed)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnTrain)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnFight)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnVitamin)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnHeal)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnLight)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnPoo)).setEnabled(enabled);
	}
}
