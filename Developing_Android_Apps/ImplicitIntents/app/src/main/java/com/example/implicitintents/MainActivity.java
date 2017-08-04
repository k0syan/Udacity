package com.example.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
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
		builder.scheme("geo")
				.path("0,0")
				.query(addressString);
		Uri addressUri = builder.build();

		showMap(addressUri);
	}

	public void onClickShareTextButton(View v) {
		String textToShare = "Text to share";
		shareText(textToShare);
	}

	public void createYourOwn(View v) {
		Toast.makeText(this,
				"TODO: Create Your Own Implicit Intent",
				Toast.LENGTH_SHORT)
				.show();
	}

	private void openWebPage(String url) {
		Uri webpage = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	private void showMap(Uri geoLocation) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(geoLocation);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	private void shareText(String textToShare) {
		String mimeType = "text/plain";
		String title = "Share title text";

		ShareCompat.IntentBuilder.from(this).setChooserTitle(title).setType(mimeType).setText(textToShare);
	}
}