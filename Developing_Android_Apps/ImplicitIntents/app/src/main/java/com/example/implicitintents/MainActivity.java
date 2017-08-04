package com.example.implicitintents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.example.android.implicitintents.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickOpenWebpageButton(View v) {
		// TODO (5) Create a String that contains a URL ( make sure it starts with http:// or https:// )

		// TODO (6) Replace the Toast with a call to openWebPage, passing in the URL String from the previous step
		Toast.makeText(this, "TODO: Open a web page when this button is clicked", Toast.LENGTH_SHORT).show();
	}

	public void onClickOpenAddressButton(View v) {
		Toast.makeText(this, "TODO: Open a map when this button is clicked", Toast.LENGTH_SHORT).show();
	}

	public void onClickShareTextButton(View v) {
		Toast.makeText(this, "TODO: Share text when this is clicked", Toast.LENGTH_LONG).show();
	}

	public void createYourOwn(View v) {
		Toast.makeText(this,
				"TODO: Create Your Own Implicit Intent",
				Toast.LENGTH_SHORT)
				.show();
	}

	// TODO (1) Create a method called openWebPage that accepts a String as a parameter
	// Do steps 2 - 4 within openWebPage

	// TODO (2) Use Uri.parse to parse the String into a Uri

	// TODO (3) Create an Intent with Intent.ACTION_VIEW and the webpage Uri as parameters

	// TODO (4) Verify that this Intent can be launched and then call startActivity
}