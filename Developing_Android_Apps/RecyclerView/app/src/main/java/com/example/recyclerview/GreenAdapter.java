package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.recyclerview.R;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

	private static final String TAG = GreenAdapter.class.getSimpleName();

	// TODO (3) Create a final private ListItemClickListener called mOnClickListener

	private static int viewHolderCount;

	private int mNumberItems;

	// TODO (1) Add an interface called ListItemClickListener
	// TODO (2) Within that interface, define a void method called onListItemClick that takes an int as a parameter

	// TODO (4) Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener

	public GreenAdapter(int numberOfItems) {
		mNumberItems = numberOfItems;
		viewHolderCount = 0;
	}

	@Override
	public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		int layoutIdForListItem = R.layout.number_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;

		View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
		NumberViewHolder viewHolder = new NumberViewHolder(view);

		viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

		int backgroundColorForViewHolder = ColorUtils
				.getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
		viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

		viewHolderCount++;
		Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
				+ viewHolderCount);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(NumberViewHolder holder, int position) {
		Log.d(TAG, "#" + position);
		holder.bind(position);
	}

	@Override
	public int getItemCount() {
		return mNumberItems;
	}

	// TODO (5) Implement OnClickListener in the NumberViewHolder class
	class NumberViewHolder extends RecyclerView.ViewHolder {

		TextView listItemNumberView;
		TextView viewHolderIndex;

		public NumberViewHolder(View itemView) {
			super(itemView);

			listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
			viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
			// TODO (7) Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
		}

		void bind(int listIndex) {
			listItemNumberView.setText(String.valueOf(listIndex));
		}

		// TODO (6) Override onClick, passing the clicked item's position (getAdapterPosition()) to mOnClickListener via its onListItemClick method
	}
}