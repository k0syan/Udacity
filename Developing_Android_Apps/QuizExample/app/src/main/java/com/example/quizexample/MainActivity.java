package com.example.quizexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.udacity.example.droidtermsprovider.DroidTermsExampleContract;

public class MainActivity extends AppCompatActivity {

	private int mCurrentState;

	private Cursor mData;

	private Button mButton;

	private final int STATE_HIDDEN = 0;

	private final int STATE_SHOWN = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButton = (Button) findViewById(R.id.button_next);

		new WordFetchTask().execute();
	}

	public void onButtonClick(View view) {

		switch (mCurrentState) {
			case STATE_HIDDEN:
				showDefinition();
				break;
			case STATE_SHOWN:
				nextWord();
				break;
		}
	}

	public void nextWord() {
		mButton.setText(getString(R.string.show_definition));

		mCurrentState = STATE_HIDDEN;
	}

	public void showDefinition() {
		mButton.setText(getString(R.string.next_word));

		mCurrentState = STATE_SHOWN;
	}

	public class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

		@Override
		protected Cursor doInBackground(Void... params) {
			ContentResolver resolver = getContentResolver();
			Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI,
					null, null, null, null);
			return cursor;
		}

		@Override
		protected void onPostExecute(Cursor cursor) {
			super.onPostExecute(cursor);
			mData = cursor;
		}
	}
}