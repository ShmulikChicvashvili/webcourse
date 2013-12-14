package com.technion.coolie.studybuddy.Views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;
import com.technion.coolie.studybuddy.Adapters.ResourceGridAdapter;

public class ResourceFragment extends SherlockFragment implements CrossGesture
{

	private static final String ResourceTypeArg = "ResourseType";

	private String ResourceType;

	private OnFragmentInteractionListener mListener;

	private NowLayout doneLayout;

	private ResourceGridAdapter doneAdapter;

	private ResourceGridAdapter resourceAdapter;

	/**
	 * Factory Method for new Fragment generation
	 * 
	 * @param resourceType
	 * @return
	 */
	public static ResourceFragment newInstance(String resourceType)
	{
		ResourceFragment fragment = new ResourceFragment();
		Bundle args = new Bundle();
		args.putString(ResourceTypeArg, resourceType);

		fragment.setArguments(args);
		return fragment;
	}

	public ResourceFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			ResourceType = getArguments().getString(ResourceTypeArg);

		}
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.stb_view_resource, container, false);
		((TextView) view.findViewById(R.id.resource_type))
				.setText(ResourceType);
		resourceAdapter = new ResourceGridAdapter(this, true);
		((NowLayout) view.findViewById(R.id.listView1))
				.setAdapter(resourceAdapter);
		doneLayout = (NowLayout) view.findViewById(R.id.done_items);
		doneAdapter = new ResourceGridAdapter(this, false);
		doneLayout.setAdapter(doneAdapter);
		doneLayout.setOnDragListener(new OnDragListener()
		{

			@Override
			public boolean onDrag(View v, DragEvent event)
			{
				switch (event.getAction())
				{
				case DragEvent.ACTION_DRAG_STARTED:
					v.setBackgroundResource(R.drawable.stb_blue_backgroud);
					resourceAdapter.startDraging((View) event.getLocalState());
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					v.setBackgroundResource(R.drawable.stb_red_backgroud);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					v.setBackgroundColor(Color.WHITE);
					break;
				case DragEvent.ACTION_DROP:
					// Dropped, reassign View to ViewGroup
					View view = (View) event.getLocalState();
					resourceAdapter.removeItem(view);
					doneAdapter.addItem(view);
					view.setVisibility(View.VISIBLE);
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					v.setBackgroundColor(Color.WHITE);
					View view1 = (View) event.getLocalState();
					view1.setVisibility(View.VISIBLE);
					resourceAdapter.stopDraging();

				default:
					break;
				}
				return true;
			}
		});
		return view;
	}

	public void onButtonPressed(Uri uri)
	{
		if (mListener != null)
		{
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener
	{
		public void onFragmentInteraction(Uri uri);
	}

	@Override
	public void remove(View view)
	{
		resourceAdapter.removeItem(view);
		doneAdapter.addItem(view);
		view.setVisibility(View.VISIBLE);
	}

}
