package com.example.githubquery;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubquery.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	private EditText mSearchBoxEditText;

	private TextView mUrlDisplayTextView;
	private TextView mSearchResultsTextView;

	private TextView errorMessageTextView;

	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

		mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_result_json);

		errorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
	}

	void makeGithubSearchQuery() {
		String queryText = mSearchBoxEditText.getText().toString();
		URL githubSearchUrl = NetworkUtils.buildUrl(queryText);
		mUrlDisplayTextView.setText(githubSearchUrl.toString());
		String githubSearchResults = null;
		new GithubQueryTask().execute(githubSearchUrl);
	}

	void showJsonDateView() {
		errorMessageTextView.setVisibility(View.INVISIBLE);
		mSearchResultsTextView.setVisibility(View.VISIBLE);
	}

	void showErrorMessage() {
		errorMessageTextView.setVisibility(View.VISIBLE);
		mSearchResultsTextView.setVisibility(View.INVISIBLE);
	}

	public class GithubQueryTask extends AsyncTask<URL, Void, String> {

		@Override
		protected void onPreExecute() {
			mLoadingIndicator.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(URL... urls) {
			URL searchUrl = urls[0];
			String githubSearchResults = null;

			try {
				githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return githubSearchResults;
		}

		@Override
		protected void onPostExecute(String s) {
			mLoadingIndicator.setVisibility(View.INVISIBLE);

			if (s != null && !s.equals("")) {
				showJsonDateView();
				mSearchResultsTextView.setText(s);
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
		int selectedItemID = item.getItemId();
		Context context = MainActivity.this;
		if (selectedItemID == R.id.action_search) {
			makeGithubSearchQuery();
		}

		return super.onOptionsItemSelected(item);
	}
}
