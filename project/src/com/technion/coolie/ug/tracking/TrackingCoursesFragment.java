package com.technion.coolie.ug.tracking;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.technion.coolie.R;
import com.technion.coolie.ug.ITrackingCourseTrasferrer;
import com.technion.coolie.ug.Server.client.ServerAsyncCommunication;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.CourseItem;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.UGLoginObject;
import com.technion.coolie.ug.tracking.EnhancedListView.Undoable;
import com.technion.coolie.ug.utils.UGCurrentState;

public class TrackingCoursesFragment extends SherlockFragment {

	TrackingListAdapter trackingCourseListAdapter;
	EnhancedListView listview;
	List<CourseKey> trackingCourses;
	
	RegistrationListAdapter registrationlistAdapter;
	List<CourseItem> coursesIRegistered;
	ListView registeredCoursesView;
	
	
	int lastSelectedTrackedItem = 0;

	MenuItem addTrackingCourseButton;
	MenuItem removeTrackingCourseButton;
	Context context;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		context = inflater.getContext();

		trackingCourses = UGDatabase.getInstance(inflater.getContext())
				.getTrackingCourses();
		Log.i("2", "trackingCourses size : " + trackingCourses.size());
		View view = inflater.inflate(R.layout.ug_tracking_list, container,
				false);
		listview = (EnhancedListView) view.findViewById(R.id.ug_tracking_list);
		trackingCourseListAdapter = new TrackingListAdapter(
				inflater.getContext(), trackingCourses,this);
		
		listview.setAdapter(trackingCourseListAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("2","TRACKING LIST CLICKED");
				lastSelectedTrackedItem = position;
				removeTrackingCourseButton.setVisible(true);
				Toast.makeText(getActivity(),
						String.valueOf(lastSelectedTrackedItem),
						Toast.LENGTH_SHORT).show();
				
				/*CourseKey ck = trackingCourses.get(position);
				UGLoginObject ugLoginObj = UGDatabase.getInstance(context).getCurrentLoginObject();
				ServerAsyncCommunication.registrate(ck.getNumber(), "11", ugLoginObj.getStudentId(), ugLoginObj.getPassword(), context, TrackingCoursesFragment.this);*/
			}
			
			
			
		});
		
		
		coursesIRegistered= UGDatabase.getInstance(inflater.getContext()).getCoursesAndExams();
		registeredCoursesView = (ListView) view.findViewById(R.id.ug_registration_list);
		registrationlistAdapter = new RegistrationListAdapter(context, coursesIRegistered,this);
		registeredCoursesView.setAdapter(registrationlistAdapter);
		registeredCoursesView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("2","registered LIST CLICKED");
				
				
				/*CourseItem ck = coursesIRegistered.get(position);
				UGLoginObject ugLoginObj = UGDatabase.getInstance(context).getCurrentLoginObject();
				ServerAsyncCommunication.unRegistrate(ck.getCourseId(), ugLoginObj.getStudentId(), ugLoginObj.getPassword(), context,TrackingCoursesFragment.this);*/
			}
			
			
			
		});
		
		listview.setDismissCallback(new EnhancedListView.OnDismissCallback() {

			@Override
			public Undoable onDismiss(EnhancedListView listView,
					final int position) {

				final CourseKey item = (CourseKey) trackingCourseListAdapter
						.getItem(position);
				trackingCourseListAdapter.remove(position);
				return new EnhancedListView.Undoable() {
					@Override
					public void undo() {
						trackingCourseListAdapter.insert(position, item);
					}
				};
			}
		});
		
		/*listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> arg0, View view,
                    int position, long id) {
				CourseKey ck = trackingCourses.get(position);
				UGLoginObject ugLoginObj = UGDatabase.getInstance(context).getCurrentLoginObject();
				ServerAsyncCommunication.registrate(ck.getNumber(), "11", ugLoginObj.getStudentId(), ugLoginObj.getPassword(), context, TrackingCoursesFragment.this);
			}
		});*/

		// Set swipe-to-delete configuration.
		listview.setSwipingLayout(R.id.tracking_list_item_layout);
		listview.setUndoStyle(EnhancedListView.UndoStyle.MULTILEVEL_POPUP);
		listview.setUndoHideDelay(3000);
		listview.enableSwipeToDismiss();
		listview.setSwipeDirection(EnhancedListView.SwipeDirection.BOTH);
		listview.setRequireTouchBeforeDismiss(false);
		UGCurrentState.currentOpenFragment = "TrackingCoursesFragment";
		
		 return view;
	}

	@Override
	public void onStop() {
		if (listview != null) {
			listview.discardUndo();
		}
		super.onStop();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
		int addTrackingCourseButtonId = 921;
		int removeTrackingCourseButtonId = 922;

		addTrackingCourseButton = menu.add(0, addTrackingCourseButtonId, 0,
				"new tracking course");
		addTrackingCourseButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		addTrackingCourseButton.setIcon(android.R.drawable.ic_menu_add);
		addTrackingCourseButton
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {

						((ITrackingCourseTrasferrer) context)
								.onAddingTrackingCourseClicked();
						return true;
						
					}
				});

		removeTrackingCourseButton = menu.add(0, removeTrackingCourseButtonId,
				0, "remove tracking course");
		removeTrackingCourseButton
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		removeTrackingCourseButton.setIcon(android.R.drawable.ic_menu_delete);
		removeTrackingCourseButton.setVisible(false);
		removeTrackingCourseButton
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						final CourseKey ck = (CourseKey) listview
								.getItemAtPosition(lastSelectedTrackedItem);
						if (ck == null)
							return false;

						AlertDialog.Builder builderSingle = new AlertDialog.Builder(
								getActivity());
						builderSingle.setIcon(R.drawable.ic_launcher);
						builderSingle.setTitle("Deleting course from tracking");
						builderSingle.setMessage("You are going to delete "
								+ ck.getNumber()
								+ " from tracking. Are you sure?");

						builderSingle.setPositiveButton("yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(getActivity(),
												"deleted", Toast.LENGTH_SHORT)
												.show();
										trackingCourses
												.remove(lastSelectedTrackedItem);
										trackingCourseListAdapter = new TrackingListAdapter(
												getActivity(), trackingCourses,TrackingCoursesFragment.this);
										listview.setAdapter(trackingCourseListAdapter);
										dialog.dismiss();
										removeTrackingCourseButton
												.setVisible(false);
										// update the server
										/*
										 * IUgTracking ugTracking =
										 * UgFactory.getUgTracking();
										 * ugTracking.
										 * removeTrackingStudentFromCourse
										 * (UGDatabase
										 * .INSTANCE.getCurrentLoginObject(),
										 * ck);
										 */
									}
								});

						builderSingle.setNegativeButton("cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});

						builderSingle.show();
						return true;
					}
				});

		super.onCreateOptionsMenu(menu, inflater);
	}
	
	public void onRegistrationSuccessed(CourseKey c)
	{
		
		String courseName="HISTABRUT";//UGDatabase.getInstance(getActivity()).getCourseByKey(c).getName();
		CourseItem ci = new CourseItem(courseName, c.getNumber(), "0", null, null, null);
		registrationlistAdapter.add(ci);
		trackingCourseListAdapter.remove(ci);
		
	}
	
	public void onCancellationSuccessed(int position)
	{
		Log.i("2","onCancellationSuccessed");
		registrationlistAdapter.remove(position);
	}

}