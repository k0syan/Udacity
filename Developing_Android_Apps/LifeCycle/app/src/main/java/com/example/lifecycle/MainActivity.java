package com.example.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	// TODO (1) Create a key String called LIFECYCLE_CALLBACKS_TEXT_KEY

	private static final String ON_CREATE = "onCreate";
	private static final String ON_START = "onStart";
	private static final String ON_RESUME = "onResume";
	private static final String ON_PAUSE = "onPause";
	private static final String ON_STOP = "onStop";
	private static final String ON_RESTART = "onRestart";
	private static final String ON_DESTROY = "onDestroy";
	private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

	private TextView mLifecycleDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLifecycleDisplay = (TextView) findViewById(R.id.tv_lifecycle_events_display);

		// TODO (6) If savedInstanceState is not null and contains LIFECYCLE_CALLBACKS_TEXT_KEY, set that text on our TextView

		logAndAppend(ON_CREATE);
	}

	@Override
	protected void onStart() {
		super.onStart();

		logAndAppend(ON_START);
	}

	@Override
	protected void onResume() {
		super.onResume();

		logAndAppend(ON_RESUME);
	}

	@Override
	protected void onPause() {
		super.onPause();

		logAndAppend(ON_PAUSE);
	}

	@Override
	protected void onStop() {
		super.onStop();

		logAndAppend(ON_STOP);
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		logAndAppend(ON_RESTART);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		logAndAppend(ON_DESTROY);
	}

	// TODO (2) Override onSaveInstanceState
	// Do steps 3 - 5 within onSaveInstanceState
	// TODO (3) Call super.onSaveInstanceState
	// TODO (4) Call logAndAppend with the ON_SAVE_INSTANCE_STATE String
	// TODO (5) Put the text from the TextView in the outState bundle

	private void logAndAppend(String lifecycleEvent) {
		Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

		mLifecycleDisplay.append(lifecycleEvent + "\n");
	}

	public void resetLifecycleDisplay(View view) {
		mLifecycleDisplay.setText("Lifecycle callbacks:\n");
	}
}