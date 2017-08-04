package com.example.intents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	/* Fields that will store our EditText and Button */
	private EditText mNameEntry;
	private Button mDoSomethingCoolButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDoSomethingCoolButton = (Button) findViewById(R.id.b_do_something_cool);
		mNameEntry = (EditText) findViewById(R.id.et_text_entry);

		mDoSomethingCoolButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = MainActivity.this;

				Class destinationActivity = ChildActivity.class;

				Intent intent = new Intent(context, destinationActivity);
				startActivity(intent);
			}
		});
	}
}
