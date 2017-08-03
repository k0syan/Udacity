package com.example.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.android.recyclerview.R;

// TODO (8) Implement GreenAdapter.ListItemClickListener from the MainActivity
public class MainActivity extends AppCompatActivity {

	private static final int NUM_LIST_ITEMS = 100;

	private GreenAdapter mAdapter;
	private RecyclerView mNumbersList;

	// TODO (9) Create a Toast variable called mToast to store the current Toast

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mNumbersList.setLayoutManager(layoutManager);

		mNumbersList.setHasFixedSize(true);

		// TODO (13) Pass in this as the ListItemClickListener to the GreenAdapter constructor
		mAdapter = new GreenAdapter(NUM_LIST_ITEMS);
		mNumbersList.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemId = item.getItemId();

		switch (itemId) {
			case R.id.action_refresh:
				// TODO (14) Pass in this as the ListItemClickListener to the GreenAdapter constructor
				mAdapter = new GreenAdapter(NUM_LIST_ITEMS);
				mNumbersList.setAdapter(mAdapter);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	// TODO (10) Override ListItemClickListener's onListItemClick method
	// TODO (11) In the beginning of the method, cancel the Toast if it isn't null
	// TODO (12) Show a Toast when an item is clicked, displaying that item number that was clicked
}