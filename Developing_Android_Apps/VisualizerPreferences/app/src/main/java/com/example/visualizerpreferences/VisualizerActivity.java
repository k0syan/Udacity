package com.example.visualizerpreferences;

import android.Manifest;
import android.content.pm.PackageManager;
import com.example.visualizerpreferences.AudioVisuals.AudioInputReader;
import com.example.visualizerpreferences.AudioVisuals.VisualizerView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class VisualizerActivity extends AppCompatActivity {

	private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 88;
	private VisualizerView mVisualizerView;
	private AudioInputReader mAudioInputReader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizer);
		mVisualizerView = (VisualizerView) findViewById(R.id.activity_visualizer);
		defaultSetup();
		setupPermissions();
	}

	private void defaultSetup() {
		mVisualizerView.setShowBass(true);
		mVisualizerView.setShowMid(true);
		mVisualizerView.setShowTreble(true);
		mVisualizerView.setMinSizeScale(1);
		mVisualizerView.setColor(getString(R.string.pref_color_red_value));
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
				String[] permissionsWeNeed = new String[]{ Manifest.permission.RECORD_AUDIO };
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

	// TODO (1) Create a new Empty Activity named SettingsActivity; make sure to generate the
	// activity_settings.xml layout file as well and add the activity to the manifest

	// TODO (2) Add a new resource folder called menu and create visualizer_menu.xml
	// TODO (3) In visualizer_menu.xml create a menu item with a single item. The id should be
	// "action_settings", title should be saved in strings.xml, the item should never
	// be shown as an action, and orderInCategory should be 100

	// TODO (5) Add the menu to the menu bar
	// TODO (6) When the "Settings" menu item is pressed, open SettingsActivity
}