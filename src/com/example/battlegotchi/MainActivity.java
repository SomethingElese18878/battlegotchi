package com.example.battlegotchi;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// name of the shared refernece
	final String PREFS_NAME = "gotchidata";
	private Gotchi gotchi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gotchi = new Gotchi();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		gotchi.setHealth(settings.getInt("gotchiHealth", 100));
		gotchi.setStrength(settings.getInt("gotchiStrength", 1));
		gotchi.setIsAngry(settings.getBoolean("gotchiIsAngry", false));
		gotchi.setMadePoo(settings.getBoolean("gotchiMadePoo", false));

		ImageView mainSequence = (ImageView) findViewById(R.id.imageViewGotchi);
		// TODO: alter background resource depending on which stage the gotchi
		// currently is
		mainSequence
				.setBackgroundResource(R.drawable.stage1_animationlist_main);
		AnimationDrawable gotchiAnimation = (AnimationDrawable) mainSequence
				.getBackground();

		gotchiAnimation.setVisible(false, true);
		gotchiAnimation.start();
	}

	/**
	 * called when an action button is pressed
	 * 
	 * @param view
	 *            the view (the button which was pressed)
	 */
	public void onAction(View view) {
		ImageView gotchiView = (ImageView) findViewById(R.id.imageViewGotchi);

		// deactivate action buttons
		changeAllButtonStates(false);

		switch (view.getId()) {
		case R.id.btnInfo:
			// TODO: alter background resource depending on which stage the
			// gotchi
			// currently is
			gotchiView
					.setBackgroundResource(R.drawable.stage1_animationlist_info);
			break;
		case R.id.btnFeed:
			gotchi.setHealth(gotchi.getHealth() + 50);
			// TODO: alter background resource depending on which stage the
			// gotchi
			// currently is
			gotchiView
					.setBackgroundResource(R.drawable.stage1_animationlist_poo);
			break;
		case R.id.btnTrain:
			// TODO: alter background resource depending on which stage the
			// gotchi
			// currently is
			// sendInfoSequence
			// .setBackgroundResource(R.drawable.stage1_animationlist_train);
			break;
		case R.id.btnFight:
			// TODO: alter background resource depending on which stage the
			// gotchi
			// currently is
			// sendInfoSequence
			// .setBackgroundResource(R.drawable.stage1_animationlist_fight);
			clearGotchiData();
			break;
		default:
			break;
		}

		AnimationDrawable gotchiAnimation = (AnimationDrawable) gotchiView
				.getBackground();
		int animationDuration = getTotalAnimationDuration(gotchiAnimation);

		gotchiAnimation.setVisible(false, true);
		gotchiAnimation.start();

		waitUntilAnimationIsFinished(animationDuration);

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
	 * calculates the total duration of an animation
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

		return duration;
	}

	/**
	 * restarts the main (idle) animation and
	 */
	private void restartMainAnimation() {
		// changes to UI may only be done in main thread, use runOnUiThread
		runOnUiThread(new Runnable() {
			public void run() {
				ImageView mainSequence = (ImageView) findViewById(R.id.imageViewGotchi);
				// TODO: alter background resource depending on which stage the
				// gotchi
				// currently is
				mainSequence
						.setBackgroundResource(R.drawable.stage1_animationlist_main);
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
	private void waitUntilAnimationIsFinished(int duration) {
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
	 * saves all gotchi data as shared preferences (persistence)
	 */
	private void saveGotchiData() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("gotchiHealth", gotchi.getHealth());
		editor.putInt("gotchiStrength", gotchi.getStrength());
		editor.putBoolean("gotchiMadePoo", gotchi.getMadePoo());
		editor.putBoolean("gotchiIsAngry", gotchi.getIsAngry());

		editor.commit();
	}

	/**
	 * Clears all gotchi data to reset the game stats
	 */
	private void clearGotchiData() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.clear();
		editor.commit();

		gotchi.setHealth(settings.getInt("gotchiHealth", 100));
		gotchi.setStrength(settings.getInt("gotchiStrength", 1));
		gotchi.setIsAngry(settings.getBoolean("gotchiIsAngry", false));
		gotchi.setMadePoo(settings.getBoolean("gotchiMadePoo", false));
	}

	private void changeAllButtonStates(boolean enabled) {
		((ImageButton) findViewById(R.id.btnInfo)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnFeed)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnTrain)).setEnabled(enabled);
		((ImageButton) findViewById(R.id.btnFight)).setEnabled(enabled);
		// TODO: implement other buttons
		// ((ImageButton) findViewById(R.id.btn)).setEnabled(enabled);
		// ((ImageButton) findViewById(R.id.btn)).setEnabled(enabled);
		// ((ImageButton) findViewById(R.id.btn)).setEnabled(enabled);
		// ((ImageButton) findViewById(R.id.btn)).setEnabled(enabled);
	}
}
