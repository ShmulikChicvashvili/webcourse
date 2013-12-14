package com.technion.coolie.studybuddy.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Views.ResourceFragment;
import com.technion.coolie.studybuddy.Views.StrikeThrowTextView;

public class ResourceGridAdapter extends BaseAdapter
{
	// private Context context;
	private LayoutInflater mInflater;
	private boolean mode;
	private List<String> items;
	private boolean notChanged = true;
	private FlingDetector listener;
	private View dragedView;
	private ResourceFragment fragment;

	/**
	 * @param context
	 */
	public ResourceGridAdapter(ResourceFragment fragment, boolean mode)
	{
		super();
		this.fragment = fragment;
		this.listener = new FlingDetector();
		mInflater = (LayoutInflater) fragment.getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		this.mode = mode;
		items = new ArrayList<String>();
		if (mode)
		{
			for (int i = 0; i < 14; i++)
				items.add(String.valueOf(i));
		} else
			items.add("example");
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem(int position)
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		StrikeThrowTextView textView = null;
		if (convertView == null)
		{
			textView = (StrikeThrowTextView) mInflater.inflate(
					R.layout.stb_view_single_resource, null);

			textView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					((StrikeThrowTextView) v)
							.setStriked(!((StrikeThrowTextView) v).isStriked());
				}
			});
			if (!mode)
				textView.setStriked(true);

			textView.setText(getItem(position).toString());
			convertView = textView;

			if (mode)
			{
				// convertView.setOnTouchListener(new swipeListener());
				// convertView.setOnLongClickListener(new OnLongClickListener()
				// {
				//
				// @Override
				// public boolean onLongClick(View v)
				// {
				// ClipData data = ClipData.newPlainText("", "");
				// DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
				// v);
				// v.startDrag(data, shadowBuilder, v, 0);
				// v.setVisibility(View.INVISIBLE);
				// return true;
				// }
				//
				// });
				convertView.setOnTouchListener(new OnTouchListener()
				{

					@SuppressLint("NewApi")
					@Override
					public boolean onTouch(View v, MotionEvent event)
					{

//						if (listener.ont)
//						{
//							fragment.remove(dragedView);
//							return true;
//						}
						ClipData data = ClipData.newPlainText("", "");
						DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
								v);
						v.startDrag(data, shadowBuilder, v, 0);
						v.setVisibility(View.INVISIBLE);
						return true;
					}
				});
			}
		} else
		{
			textView = (StrikeThrowTextView) convertView;
			textView.setText(getItem(position).toString());

		}

		return convertView;
	}

	public void addItem(View view)
	{
		String val = ((StrikeThrowTextView) view).getText().toString();
		items.add(val);
		if (notChanged)
		{
			items.remove("example");
			notChanged = false;
		}

		notifyDataSetChanged();

	}

	public void removeItem(View view)
	{
		String val = ((StrikeThrowTextView) view).getText().toString();
		items.remove(val);
		notifyDataSetChanged();
	}

	public interface CrossGesture
	{
		public void remove(View view);
	}

	public void startDraging(View view)
	{
		this.dragedView = view;
	}

	public void stopDraging()
	{
		this.dragedView = null;
	}

	private class FlingDetector extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY)
		{
			boolean usedThisFling = false;

			return (Math.abs(velocityY) >= 2.0 * Math.abs(velocityX));

		}
	}
}
