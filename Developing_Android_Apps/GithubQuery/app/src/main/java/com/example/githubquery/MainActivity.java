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

// TODO (1) implement LoaderManager.LoaderCallbacks<String> on MainActivity
public class MainActivity extends AppCompatActivity {

	private static final String SEARCH_QUERY_URL_EXTRA = "query";

	// TODO (28) Remove the key for storing the search results JSON
	private static final String SEARCH_RESULTS_RAW_JSON = "results";

	// TODO (2) Create a constant int to uniquely identify your loader. Call it GITHUB_SEARCH_LOADER

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
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

		if (savedInstanceState != null) {
			String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);

			// TODO (26) Remove the code that retrieves the JSON
			String rawJsonSearchResults = savedInstanceState.getString(SEARCH_RESULTS_RAW_JSON);

			mUrlDisplayTextView.setText(queryUrl);
			// TODO (25) Remove the code that displays the JSON
			mSearchResultsTextView.setText(rawJsonSearchResults);
		}

		// TODO (24) Initialize the loader with GITHUB_SEARCH_LOADER as the ID, null for the bundle, and this for the context
	}

	private void makeGithubSearchQuery() {
		String githubQuery = mSearchBoxEditText.getText().toString();

		// TODO (17) If no search was entered, indicate that there isn't anything to search for and return

		URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
		mUrlDisplayTextView.setText(githubSearchUrl.toString());

		// TODO (18) Remove the call to execute the AsyncTask
		new GithubQueryTask().execute(githubSearchUrl);

		// TODO (19) Create a bundle called queryBundle
		// TODO (20) Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value

		// TODO (21) Call getSupportLoaderManager and store it in a LoaderManager variable
		// TODO (22) Get our Loader by calling getLoader and passing the ID we specified
		// TODO (23) If the Loader was null, initialize it. Else, restart it.
	}

	private void showJsonDataView() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mSearchResultsTextView.setVisibility(View.VISIBLE);
	}

	private void showErrorMessage() {
		mSearchResultsTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}

	// TODO (3) Override onCreateLoader
	// Within onCreateLoader
	// TODO (4) Return a new AsyncTaskLoader<String> as an anonymous inner class with this as the constructor's parameter
	// TODO (5) Override onStartLoading
	// Within onStartLoading

	// TODO (6) If args is null, return.

	// TODO (7) Show the loading indicator

	// TODO (8) Force a load
	// END - onStartLoading

	// TODO (9) Override loadInBackground

	// Within loadInBackground
	// TODO (10) Get the String for our URL from the bundle passed to onCreateLoader

	// TODO (11) If the URL is null or empty, return null

	// TODO (12) Copy the try / catch block from the AsyncTask's doInBackground method
	// END - loadInBackground

	// TODO (13) Override onLoadFinished

	// Within onLoadFinished
	// TODO (14) Hide the loading indicator

	// TODO (15) Use the same logic used in onPostExecute to show the data or the error message
	// END - onLoadFinished

	// TODO (16) Override onLoaderReset as it is part of the interface we implement, but don't do anything in this method

	// TODO (29) Delete the AsyncTask class
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

		// TODO (27) Remove the code that persists the JSON
		String rawJsonSearchResults = mSearchResultsTextView.getText().toString();
		outState.putString(SEARCH_RESULTS_RAW_JSON, rawJsonSearchResults);
	}
}