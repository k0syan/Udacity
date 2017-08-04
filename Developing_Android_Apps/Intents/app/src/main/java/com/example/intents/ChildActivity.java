package com.example.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

	private TextView mChiltTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);

		mChiltTextView = (TextView) findViewById(R.id.tv_display);

		Intent parentIntent = getIntent();

		if (parentIntent.hasExtra(Intent.EXTRA_TEXT)) {
			String enteredText = parentIntent.getStringExtra(Intent.EXTRA_TEXT);
			mChiltTextView.setText(enteredText);
		}
	}
}
