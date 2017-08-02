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

	private int mNumberItems;

	public GreenAdapter(int numberOfItems) {
		mNumberItems = numberOfItems;
	}

	@Override
	public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		int layoutIdForListItem = R.layout.number_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;

		View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
		NumberViewHolder viewHolder = new NumberViewHolder(view);

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

	class NumberViewHolder extends RecyclerView.ViewHolder {

		TextView listItemNumberView;

		private NumberViewHolder(View itemView) {
			super(itemView);

			listItemNumberView = itemView.findViewById(R.id.tv_item_number);
		}

		void bind(int listIndex) {
			listItemNumberView.setText(String.valueOf(listIndex));
		}
	}
}