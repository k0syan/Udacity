package com.example.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.example.android.implicitintents.R;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickOpenWebpageButton(View v) {

		String link = "https://www.udacity.com";

		openWebPage(link);
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

	public void openWebPage(String link) {
		Uri uri = Uri.parse(link);

		Intent openWebBrowser = new Intent(Intent.ACTION_VIEW, uri);

		if (openWebBrowser.resolveActivity(getPackageManager()) != null) {
			startActivity(openWebBrowser);
		}

	}
}