package com.example.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sunshine.data.SunshinePreferences;
import com.example.sunshine.utilities.NetworkUtils;
import com.example.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

	// Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
	// TODO (47) Create the default constructor (we will pass in parameters in a later lesson)
	// Within ForecastAdapter.java /////////////////////////////////////////////////////////////////

	private RecyclerView mRecyclerView;

	private ForecastAdapter mForecastAdapter;

	private TextView mErrorMessageDisplay;

	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);

		mRecyclerView = findViewById(R.id.recyclerview_forecast);
		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

		mRecyclerView.setLayoutManager(layoutManager);

		mRecyclerView.setHasFixedSize(true);

		mForecastAdapter = new ForecastAdapter();

		mRecyclerView.setAdapter(mForecastAdapter);

		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

		loadWeatherData();
	}

	private void loadWeatherData() {
		showWeatherDataView();

		String location = SunshinePreferences.getPreferredWeatherLocation(this);
		new FetchWeatherTask().execute(location);
	}

	private void showWeatherDataView() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mRecyclerView.setVisibility(View.VISIBLE);
	}

	private void showErrorMessage() {
		mRecyclerView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}

	public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadingIndicator.setVisibility(View.VISIBLE);
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

				String[] simpleJsonWeatherData = OpenWeatherJsonUtils
						.getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

				return simpleJsonWeatherData;

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String[] weatherData) {
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			if (weatherData != null) {
				showWeatherDataView();
				mForecastAdapter.setWeatherData(weatherData);
			} else {
				showErrorMessage();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.forecast, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_refresh) {
			mForecastAdapter.setWeatherData(null);
			loadWeatherData();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}