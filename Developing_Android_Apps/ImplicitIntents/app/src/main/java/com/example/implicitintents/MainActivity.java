package com.example.implicitintents;

import android.content.Intent;
import android.net.Uri;
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
		String urlAsString = "http://www.udacity.com";
		openWebPage(urlAsString);
	}

	public void onClickOpenAddressButton(View v) {
		String addressString = "1600 Amphitheatre Parkway, CA";

		Uri.Builder builder = new Uri.Builder();
		builder.scheme("geo").path("0, 0").query(addressString);
		Uri addressUri = builder.build();

		showMap(addressUri);
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

	private void openWebPage(String url) {
		Uri webpage = Uri.parse(url);

		Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW, webpage);

		if (openBrowserIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(openBrowserIntent);
		}
	}

	private void showMap(Uri uri) {
		Intent showMapIntent = new Intent(Intent.ACTION_VIEW, uri);

		if (showMapIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(showMapIntent);
		}
	}
}