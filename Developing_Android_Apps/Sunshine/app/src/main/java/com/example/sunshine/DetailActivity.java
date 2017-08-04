package com.example.sunshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
