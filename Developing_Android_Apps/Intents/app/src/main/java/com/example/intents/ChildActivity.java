package com.example.intents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

	private TextView mChiltTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mChiltTextView = (TextView) findViewById(R.id.tv_display);
		setContentView(R.layout.activity_child);
	}
}
