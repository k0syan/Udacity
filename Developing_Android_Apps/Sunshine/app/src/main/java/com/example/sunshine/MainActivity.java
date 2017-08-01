package com.example.sunshine;

import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.sunshine.data.SunshinePreferences;
import com.example.sunshine.utilities.NetworkUtils;
import com.example.sunshine.utilities.OpenWeatherJsonUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	TextView mWeatherTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);

		mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

		loadWeatherData();
	}

	private void loadWeatherData() {
		String location = SunshinePreferences.getPreferredWeatherLocation(this);
		new FetchWeatherTask().execute(location);
	}

	public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
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

				// String[] r = {"Valod"};
				// r[0] = jsonWeatherResponse;
				return simpleJsonWeatherData;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String[] weatherData) {
			if (weatherData != null) {
				for (String weatherString : weatherData) {
					mWeatherTextView.append((weatherString) + "\n\n\n");
				}
			}
		}
	}
}
