package com.example.githubquery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.githubquery.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	private static final String SEARCH_QUERY_URL_EXTRA = "query";

	private static final String SEARCH_RESULTS_RAW_JSON = "results";

	private EditText mSearchBoxEditText;

	private TextView mUrlDisplayTextView;
	private TextView mSearchResultsTextView;

	private TextView mErrorMessageDisplay;

	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

		mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_result_json);

		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

		// COMPLETED (9) If the savedInstanceState bundle is not null, set the text of the URL and search results TextView respectively
		if (savedInstanceState != null) {
			String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);
			String rawJsonSearchResults = savedInstanceState.getString(SEARCH_RESULTS_RAW_JSON);

			mUrlDisplayTextView.setText(queryUrl);
			mSearchResultsTextView.setText(rawJsonSearchResults);
		}
	}

	private void makeGithubSearchQuery() {
		String githubQuery = mSearchBoxEditText.getText().toString();
		URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
		mUrlDisplayTextView.setText(githubSearchUrl.toString());
		new GithubQueryTask().execute(githubSearchUrl);
	}

	private void showJsonDataView() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mSearchResultsTextView.setVisibility(View.VISIBLE);
	}

	private void showErrorMessage() {
		mSearchResultsTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}

	public class GithubQueryTask extends AsyncTask<URL, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadingIndicator.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(URL... params) {
			URL searchUrl = params[0];
			String githubSearchResults = null;
			try {
				githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return githubSearchResults;
		}

		@Override
		protected void onPostExecute(String githubSearchResults) {
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			if (githubSearchResults != null && !githubSearchResults.equals("")) {
				showJsonDataView();
				mSearchResultsTextView.setText(githubSearchResults);
			} else {
				showErrorMessage();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemThatWasClickedId = item.getItemId();
		if (itemThatWasClickedId == R.id.action_search) {
			makeGithubSearchQuery();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		String queryUrl = mUrlDisplayTextView.getText().toString();

		outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);

		String rawJsonSearchResults = mSearchResultsTextView.getText().toString();

		outState.putString(SEARCH_RESULTS_RAW_JSON, rawJsonSearchResults);
	}
}