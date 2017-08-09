package com.example.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.waitlist.data.WaitlistContract;
import com.example.waitlist.data.WaitlistDbHelper;


public class MainActivity extends AppCompatActivity {

	private GuestListAdapter mAdapter;
	private SQLiteDatabase mDb;
	private EditText mNewGuestNameEditText;
	private EditText mNewPartySizeEditText;
	private final static String LOG_TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RecyclerView waitlistRecyclerView;

		waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
		mNewGuestNameEditText = (EditText) this.findViewById(R.id.person_name_edit_text);
		mNewPartySizeEditText = (EditText) this.findViewById(R.id.party_count_edit_text);

		waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);

		mDb = dbHelper.getWritableDatabase();

		Cursor cursor = getAllGuests();

		mAdapter = new GuestListAdapter(this, cursor);

		waitlistRecyclerView.setAdapter(mAdapter);

		new ItemTouchHelper(
				new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
					@Override
					public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
						return false;
					}

					@Override
					public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
						long id = (long) viewHolder.itemView.getTag();
						removeGuest(id);
						mAdapter.swapCursor(getAllGuests());
					}
				}
		).attachToRecyclerView(waitlistRecyclerView);
	}

	public void addToWaitlist(View view) {
		if (mNewGuestNameEditText.getText().length() == 0 ||
				mNewPartySizeEditText.getText().length() == 0) {
			return;
		}

		int partySize = 1;
		try {
			partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
		} catch (NumberFormatException ex) {
			Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
		}

		addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);

		mAdapter.swapCursor(getAllGuests());

		mNewPartySizeEditText.clearFocus();
		mNewGuestNameEditText.getText().clear();
		mNewPartySizeEditText.getText().clear();
	}

	private Cursor getAllGuests() {
		return mDb.query(
				WaitlistContract.WaitlistEntry.TABLE_NAME,
				null,
				null,
				null,
				null,
				null,
				WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
		);
	}

	private long addNewGuest(String name, int partySize) {
		ContentValues cv = new ContentValues();
		cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
		cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
		return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
	}

	private boolean removeGuest(long id) {
		return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME,
				WaitlistContract.WaitlistEntry._ID + " = " + id, null) > 0;
	}
}
