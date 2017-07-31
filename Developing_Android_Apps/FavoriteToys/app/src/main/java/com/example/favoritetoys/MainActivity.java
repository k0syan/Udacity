package com.example.favoritetoys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mToysListTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToysListTextView = findViewById(R.id.tv_toy_names);

		String[] toys = ToyBox.getToyNames();

		for (String toy : toys) {
			mToysListTextView.append(toy + "\n\n\n");
		}
	}
}
