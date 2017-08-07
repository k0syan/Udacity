package com.example.visualizerpreferences;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.visualizerpreferences.AudioVisuals.AudioInputReader;
import com.example.visualizerpreferences.AudioVisuals.VisualizerView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class VisualizerActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 88;
	private VisualizerView mVisualizerView;
	private AudioInputReader mAudioInputReader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizer);
		mVisualizerView = (VisualizerView) findViewById(R.id.activity_visualizer);
		setupSharedPreferences();
		setupPermissions();
	}


	private void setupSharedPreferences() {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mVisualizerView.setShowBass(sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key), getResources().getBoolean(R.bool.pref_show_bass_default)));
		mVisualizerView.setShowMid(sharedPreferences.getBoolean(getString(R.string.pref_show_mid_key), getResources().getBoolean(R.bool.pref_show_mid_default)));
		mVisualizerView.setShowTreble(sharedPreferences.getBoolean(getString(R.string.pref_show_treble_key), getResources().getBoolean(R.bool.pref_show_treble_default)));

		mVisualizerView.setMinSizeScale(1);
		loadColorFromPreferencies(sharedPreferences);

		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	private void loadColorFromPreferencies(SharedPreferences sharedPreferences) {
		mVisualizerView.setColor(sharedPreferences.getString(getString(R.string.pref_color_key), getString(R.string.pref_color_red_value)));
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mAudioInputReader != null) {
			mAudioInputReader.shutdown(isFinishing());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mAudioInputReader != null) {
			mAudioInputReader.restart();
		}
	}

	private void setupPermissions() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				String[] permissionsWeNeed = new String[]{Manifest.permission.RECORD_AUDIO};
				requestPermissions(permissionsWeNeed, MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE);
			}
		} else {
			mAudioInputReader = new AudioInputReader(mVisualizerView, this);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
																				 @NonNull String permissions[], @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					mAudioInputReader = new AudioInputReader(mVisualizerView, this);

				} else {
					Toast.makeText(this, "Permission for audio not granted. Visualizer can't run.", Toast.LENGTH_LONG).show();
					finish();
				}
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.visualizer_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			Context context = this;
			Class destinationClass = SettingsActivity.class;
			Intent intentToStartDetailActivity = new Intent(context, destinationClass);
			startActivity(intentToStartDetailActivity);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(getString(R.string.pref_show_bass_key))) {
			mVisualizerView.setShowBass(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_bass_default)));
		} else if (key.equals(getString(R.string.pref_show_treble_key))) {
			mVisualizerView.setShowTreble(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_treble_default)));
		} else if (key.equals(getString(R.string.pref_show_mid_key))) {
			mVisualizerView.setShowMid(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_mid_default)));
		} else if (key.equals(getString(R.string.pref_color_key))) {
			loadColorFromPreferencies(sharedPreferences);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
	}
}