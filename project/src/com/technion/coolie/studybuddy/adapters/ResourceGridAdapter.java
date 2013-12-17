package com.technion.coolie.studybuddy.adapters;

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
import com.technion.coolie.studybuddy.data.DataStore;
import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.StudyItem;
import com.technion.coolie.studybuddy.presenters.CoursePresenter;
import com.technion.coolie.studybuddy.views.ResourceFragment;
import com.technion.coolie.studybuddy.views.StrikeThrowTextView;

public class ResourceGridAdapter extends BaseAdapter
{
	public interface CrossGesture
	{
		public void remove(View view);
	}

	private class FlingDetector extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onFling(	MotionEvent e1,
								MotionEvent e2,
								float velocityX,
								float velocityY)
		{
			boolean usedThisFling = false;

			return (Math.abs(velocityY) >= 2.0 * Math.abs(velocityX));

		}
	}

	// private Context context;
	private LayoutInflater		mInflater;
	private boolean				done;
	private List<String>		items;
	private boolean				notChanged	= true;
	private FlingDetector		listener;
	private View				dragedView;

	private ResourceFragment	fragment;

	private CoursePresenter		presenter;

	/**
	 * @param context
	 */
	public ResourceGridAdapter(ResourceFragment fragment, boolean done,
					String courseId, String resourceName)
	{
		super();
		this.fragment = fragment;
		listener = new FlingDetector();
		mInflater = (LayoutInflater) fragment.getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		this.done = done;
		items = new ArrayList<String>();
		// course = DataStore.coursesById.get(courseId);

		presenter = DataStore.getInstance().getCoursePresenter(courseId);

		if (done)
		{
			items.addAll(presenter.getStudyItemsDone(resourceName));
		} else
		{
			items.addAll(presenter.getStudyItemsRemaining(resourceName));
		}
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
									.setStriked(!((StrikeThrowTextView) v)
													.isStriked());
					// TODO object checked and shoul be treated
				}
			});
			if (done)
			{
				textView.setStriked(true);
			}

			textView.setText(getItem(position).toString());
			convertView = textView;

			if (done)
			{
				convertView.setOnTouchListener(new OnTouchListener()
				{

					@SuppressLint("NewApi")
					@Override
					public boolean onTouch(View v, MotionEvent event)
					{

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

	public void removeItem(View view)
	{
		String val = ((StrikeThrowTextView) view).getText().toString();
		items.remove(val);
		notifyDataSetChanged();
	}

	public void startDraging(View view)
	{
		dragedView = view;
	}

	public void stopDraging()
	{
		dragedView = null;
	}
}
