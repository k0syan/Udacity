package com.example.githubquery;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubquery.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

	EditText mSearchBoxEditText;

	TextView mUrlDisplayTextView;
	TextView mSearchResultsTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

		mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_result_json);
	}

	void makeGithubSearchQuery() {
		String queryText = mSearchBoxEditText.getText().toString();
		URL githubSearchUrl = NetworkUtils.buildUrl(queryText);
		mUrlDisplayTextView.setText(githubSearchUrl.toString());
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
