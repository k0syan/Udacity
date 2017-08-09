package com.example.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waitlist.data.WaitlistContract;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

	private Cursor mCursor;
	private Context mContext;

	public GuestListAdapter(Context context, Cursor cursor) {
		this.mContext = context;
		this.mCursor = cursor;
	}

	@Override
	public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.guest_list_item, parent, false);
		return new GuestViewHolder(view);
	}

	@Override
	public void onBindViewHolder(GuestViewHolder holder, int position) {
		if (!mCursor.moveToPosition(position))
			return;

		String name = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
		int partySize = mCursor.getInt(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
		long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));

		holder.nameTextView.setText(name);
		holder.partySizeTextView.setText(String.valueOf(partySize));
		holder.itemView.setTag(id);
	}

	@Override
	public int getItemCount() {
		return mCursor.getCount();
	}

	public void swapCursor(Cursor newCursor) {
		// Always close the previous mCursor first
		if (mCursor != null) mCursor.close();
		mCursor = newCursor;
		if (newCursor != null) {
			// Force the RecyclerView to refresh
			this.notifyDataSetChanged();
		}
	}

	class GuestViewHolder extends RecyclerView.ViewHolder {

		TextView nameTextView;
		TextView partySizeTextView;

		public GuestViewHolder(View itemView) {
			super(itemView);
			nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
			partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
		}
	}
}