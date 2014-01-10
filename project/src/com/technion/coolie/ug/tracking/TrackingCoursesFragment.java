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
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
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
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.tracking.EnhancedListView.Undoable;
import com.technion.coolie.ug.utils.UGCurrentState;

public class TrackingCoursesFragment extends SherlockFragment {

	TrackingListAdapter trackingCourseListAdapter;
	EnhancedListView listview;
	List<CourseKey> trackingCourses;
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
		Log.i("1", "trackingCourses size : " + trackingCourses.size());
		View view = inflater.inflate(R.layout.ug_tracking_list, container,
				false);
		listview = (EnhancedListView) view.findViewById(R.id.ug_tracking_list);
		trackingCourseListAdapter = new TrackingListAdapter(
				inflater.getContext(), trackingCourses);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lastSelectedTrackedItem = position;
				removeTrackingCourseButton.setVisible(true);
				Toast.makeText(getActivity(),
						String.valueOf(lastSelectedTrackedItem),
						Toast.LENGTH_SHORT).show();
			}
		});
		listview.setAdapter(trackingCourseListAdapter);

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

		// Set swipe-to-delete configuration.
		listview.setSwipingLayout(R.id.tracking_list_item_layout);
		listview.setUndoStyle(EnhancedListView.UndoStyle.MULTILEVEL_POPUP);
		listview.setUndoHideDelay(3000);
		listview.enableSwipeToDismiss();
		listview.setSwipeDirection(EnhancedListView.SwipeDirection.BOTH);
		listview.setRequireTouchBeforeDismiss(false);
		UGCurrentState.currentOpenFragment = "TrackingCoursesFragment";
		
		 ImageButton registerBtn = (ImageButton) getActivity().findViewById(R.id.ug_trackinglist_item_rishum_btn);
		 
		 
		 ServerAsyncCommunication.registrate("094412", "11", "1636", "11111100",getActivity(),this);
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
						/*
						 * AlertDialog.Builder builderSingle = new
						 * AlertDialog.Builder(getActivity());
						 * builderSingle.setIcon(R.drawable.ic_launcher);
						 * builderSingle.setTitle("Add course to tracking");
						 * 
						 * class CourseToTrack { String courseNumber; String
						 * courseName; Semester semester;
						 * 
						 * public CourseToTrack(String number, String name,
						 * Semester s) { courseNumber = number; courseName =
						 * name; semester = s; }
						 * 
						 * @Override public String toString() { return
						 * courseNumber + " " + courseName; } }
						 * 
						 * final ArrayList<CourseToTrack> list = new
						 * ArrayList<CourseToTrack>(); for (Course c :
						 * UGDatabase.getInstance(context).getCourses()) { if
						 * (!trackingCourses.contains(c.getCourseKey())) // if
						 * is not already tracking { list.add(new
						 * CourseToTrack(c.getCourseNumber(),
						 * c.getName(),c.getSemester())); }
						 * 
						 * }
						 * 
						 * final ArrayAdapter<CourseToTrack> adapter = new
						 * ArrayAdapter<CourseToTrack>(getActivity(),
						 * android.R.layout.simple_list_item_1, list); // final
						 * TrackingListAdapter adapter = new //
						 * TrackingListAdapter(context, trackingCoursesList);
						 * 
						 * builderSingle.setNegativeButton("cancel", new
						 * DialogInterface.OnClickListener() {
						 * 
						 * @Override public void onClick(DialogInterface dialog,
						 * int which) { dialog.dismiss(); } });
						 * 
						 * builderSingle.setAdapter(adapter, new
						 * DialogInterface.OnClickListener() {
						 * 
						 * @Override public void onClick(DialogInterface dialog,
						 * int which) { CourseToTrack x =
						 * adapter.getItem(which); CourseKey ck = new
						 * CourseKey(x.courseNumber, x.semester);
						 * trackingCourses.add(ck);
						 * 
						 * trackingCourseListAdapter = new
						 * TrackingListAdapter(getActivity(), trackingCourses);
						 * listview.setAdapter(trackingCourseListAdapter);
						 * dialog.dismiss(); // send update to server
						 * /*IUgTracking ugTracking = UgFactory.getUgTracking();
						 * ugTracking.addTrackingStudent(UGDatabase.INSTANCE.
						 * getCurrentLoginObject(), ck);
						 * 
						 * } }); builderSingle.show(); return true;
						 */
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
												getActivity(), trackingCourses);
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
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setMessage("onRegistrationSuccessed");
		alertDialogBuilder.show();
	}

}