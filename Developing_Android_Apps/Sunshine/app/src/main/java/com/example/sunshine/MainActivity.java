package com.example.sunshine;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.sunshine.data.SunshinePreferences;
import com.example.sunshine.utilities.NetworkUtils;
import com.example.sunshine.utilities.OpenWeatherJsonUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	private TextView mWeatherTextView;

	private TextView mErrorTextView;

	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);

		mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

		mErrorTextView = (TextView) findViewById(R.id.tv_error_message_display);

		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

		loadWeatherData();
	}

	private void loadWeatherData() {
		showWeatherDataView();
		String location = SunshinePreferences.getPreferredWeatherLocation(this);
		new FetchWeatherTask().execute(location);
	}

	private void showWeatherDataView() {
		mWeatherTextView.setVisibility(View.VISIBLE);
		mErrorTextView.setVisibility(View.INVISIBLE);
	}

	private void showErrorMessage() {
		mWeatherTextView.setVisibility(View.INVISIBLE);
		mErrorTextView.setVisibility(View.VISIBLE);
	}

	public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
		@Override
		protected void onPreExecute() {
			mLoadingIndicator.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(String... params) {

			if (params.length == 0) {
				return null;
			}

			String location = params[0];
			URL weatherRequestUrl = NetworkUtils.buildUrl(location);

			try {
				String jsonWeatherResponse = NetworkUtils
						.getResponseFromHttpUrl(weatherRequestUrl);

				String[] simpleJsonWeatherData = null;
				try {
					simpleJsonWeatherData = OpenWeatherJsonUtils
							.getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}

				return simpleJsonWeatherData;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String[] weatherData) {

			mLoadingIndicator.setVisibility(View.INVISIBLE);

			if (weatherData != null) {
				showWeatherDataView();
				for (String weatherString : weatherData) {
					mWeatherTextView.append((weatherString) + "\n\n\n");
				}
			} else {
				showErrorMessage();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.forecast, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int selectedItemID = item.getItemId();
		if (selectedItemID == R.id.action_refresh) {
			loadWeatherData();
		}
		return super.onOptionsItemSelected(item);
	}
}
