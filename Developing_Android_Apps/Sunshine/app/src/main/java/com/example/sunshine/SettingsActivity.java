package com.example.sunshine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_settings);
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// TODO (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

		// TODO (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
		}

		return super.onOptionsItemSelected(item);
	}
}
