package com.example.sunshine;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

	TextView mDetailTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		mDetailTextView = findViewById(R.id.tv_weather_detail);
		Intent parentIntent = getIntent();

		if (parentIntent.hasExtra(Intent.EXTRA_TEXT)) {
			String enteredText = parentIntent.getStringExtra(Intent.EXTRA_TEXT);
			mDetailTextView.setText(enteredText);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.share_weather_details) {
			String mimeType = "text/plain";
			String title = "Share title text";

			ShareCompat.IntentBuilder.from(this).setChooserTitle(title).setType(mimeType).setText(mDetailTextView.getText().toString());
		}

		if (id == R.id.actions_settings) {
			Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
			startActivity(startSettingsActivity);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
