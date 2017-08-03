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

	final private ListItemClickListener mOnClickListener;
	private static int viewHolderCount;

	private int mNumberItems;

	public interface ListItemClickListener {

		void onListItemClick(int index);

	}

	public GreenAdapter(int numberOfItems, ListItemClickListener listener) {
		mNumberItems = numberOfItems;
		mOnClickListener = listener;
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

	class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView listItemNumberView;
		TextView viewHolderIndex;

		public NumberViewHolder(View itemView) {
			super(itemView);

			listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
			viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
			itemView.setOnClickListener(this);
		}

		void bind(int listIndex) {
			listItemNumberView.setText(String.valueOf(listIndex));
		}

		@Override
		public void onClick(View view) {
			int clickedPosition = getAdapterPosition();
			mOnClickListener.onListItemClick(clickedPosition);
		}
	}
}