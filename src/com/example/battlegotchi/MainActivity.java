package com.example.battlegotchi;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	final String asd = "asd";

	Gotchi gotchi;
	ImageButton btnFeed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gotchi = new Gotchi();

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

	public void onAction(View view) {
		ImageView gotchiView = (ImageView) findViewById(R.id.imageViewGotchi);

		switch (view.getId()) {
		case R.id.btnInfo:
			// TODO: alter background resource depending on which stage the
			// gotchi
			// currently is
			gotchiView
					.setBackgroundResource(R.drawable.stage1_animationlist_info);
			break;
		case R.id.btnFeed:
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
//			sendInfoSequence
//					.setBackgroundResource(R.drawable.stage1_animationlist_train);
			break;
		case R.id.btnFight:
			// TODO: alter background resource depending on which stage the
			// gotchi
			// currently is
//			sendInfoSequence
//					.setBackgroundResource(R.drawable.stage1_animationlist_fight);
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

	private int getTotalAnimationDuration(AnimationDrawable anim) {
		int duration = 0;

		for (int i = 0; i < anim.getNumberOfFrames(); i++) {
			duration = duration + anim.getDuration(i);
		}

		return duration;
	}

	private void restartMainAnimation() {
		// changes to UI may only be done in main thread, use runOnUiThread
		runOnUiThread(new Runnable() {
			public void run() {
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
		});
	}

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
}
