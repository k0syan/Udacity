package com.example.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

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

		// TODO (1) Use logAndAppend within onCreate
	}

	// TODO (2) Override onStart, call super.onStart, and call logAndAppend with ON_START

	// TODO (3) Override onResume, call super.onResume, and call logAndAppend with ON_RESUME

	// TODO (4) Override onPause, call super.onPause, and call logAndAppend with ON_PAUSE

	// TODO (5) Override onStop, call super.onStop, and call logAndAppend with ON_STOP

	// TODO (6) Override onRestart, call super.onRestart, and call logAndAppend with ON_RESTART

	// TODO (7) Override onDestroy, call super.onDestroy, and call logAndAppend with ON_DESTROY

	private void logAndAppend(String lifecycleEvent) {
		Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

		mLifecycleDisplay.append(lifecycleEvent + "\n");
	}

	public void resetLifecycleDisplay(View view) {
		mLifecycleDisplay.setText("Lifecycle callbacks:\n");
	}
}