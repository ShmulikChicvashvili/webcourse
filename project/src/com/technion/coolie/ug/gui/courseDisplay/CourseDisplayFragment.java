package com.technion.coolie.ug.gui.courseDisplay;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Meeting;
import com.technion.coolie.ug.model.RegistrationGroup;

//TODO onClick radio group
//TODO kdamim
//TODO add to maakav
//TODO selectable group list view

/**
 * activity for searching courses and finding available courses. must supply
 * course key in EXTRAS_COURSE_KEY before calling this activity/fragment.
 * 
 * 
 */
public class CourseDisplayFragment extends Fragment {

	Course courseToView;
	Context context;
	CourseGroupsAdapter groupAdapter;
	LinearLayout groupsView;

	public final static String ARGUMENTS_COURSE_KEY = "course";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ug_course_screen_layout, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		context = getActivity();
		groupsView = (LinearLayout) getActivity().findViewById(
				R.id.course_screen_groups_list);
		recieveCourse(getArguments());
		initLayout();
		updateCourseDisplay();
		super.onActivityCreated(savedInstanceState);
	}

	private void initLayout() {
		/*
		 * RadioGroup b = (RadioGroup) getActivity().findViewById(
		 * R.id.course_screen_semester_radio_group); b.setVisibility(View.GONE);
		 * // TODO set the names of the semesters in order.
		 * b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(RadioGroup group, int
		 * checkedId) { // find out what semster responds to that semster
		 * index(in // UGdatabase). // get course from new courseKey // if
		 * course is available call update coursedisplay // if course is not
		 * available replace with blank screen with not // learned at that
		 * semester text.
		 * 
		 * } });
		 */
	}

	private void updateCourseDisplay() {
		final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		TextView nameTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_name);
		nameTextView.setText(courseToView.getName());
		TextView pointsTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_points);
		pointsTextView.setText("" + courseToView.getPoints());
		TextView numberTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_number);
		numberTextView.setText("" + courseToView.getCourseNumber());

		TextView facultyTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_faculty);
		facultyTextView.setText("" + courseToView.getFaculty().toString());

		TextView descTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_description);
		descTextView.setText(courseToView.getDescription());

		TextView examATextView = (TextView) getActivity().findViewById(
				R.id.course_screen_exam_a);
		examATextView.setText(df.format(courseToView.getMoedA().getTime()));

		TextView examBTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_exam_b);
		examBTextView.setText(df.format(courseToView.getMoedB().getTime()));

		makeGroupsHeader();

		if (courseToView.getRegistrationGroups() != null) {
			addSeperatorLine();
			for (RegistrationGroup group : courseToView.getRegistrationGroups()) {
				addRegistrationGroup(group);
			}
		}

		fixEndOfGroups();

		// groupAdapter = new CourseGroupsAdapter(context,
		// courseToView.getRegistrationGroups(), new onClickGroup());

	}

	private void addSeperatorLine() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.ug_course_display_line_seperator,
				groupsView);
	}

	private void makeGroupsHeader() {

		MeetingDisplay explanationHeader = new MeetingDisplay("ID", "Type",
				"Lecturer", "location", "start Time", "end Time",
				"Avl. places", "day");
		View v = addMeeting(explanationHeader);
		v.setBackgroundResource(R.drawable.ug_course_label_text_container);

	}

	private void fixEndOfGroups() {
		addMeeting(new MeetingDisplay());// empty to fix the table
	}

	private void addRegistrationGroup(RegistrationGroup group) {

		MeetingDisplay header = new MeetingDisplay();
		header.freeSpace = "" + group.getFreePlaces();
		header.number = "" + group.getGroupId();
		View v = addMeeting(header);
		v.setBackgroundColor(Color.LTGRAY);

		// do all meetings
		if (group.getLectures() != null)
			for (Meeting meeting : group.getLectures()) {
				addMeeting(new MeetingDisplay(meeting, "lecture"));
			}
		if (group.getTutorials() != null)
			for (Meeting meeting : group.getTutorials()) {
				addMeeting(new MeetingDisplay(meeting, "tutorial"));
			}
		addSeperatorLine();
	}

	private View addMeeting(MeetingDisplay meeting) {

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.ug_course_screen_group_item_row,
				groupsView, false);
		((TextView) view.findViewById(R.id.ug_course_display_group_number))
				.setText(meeting.number);
		((TextView) view
				.findViewById(R.id.ug_course_display_group_meeting_type))
				.setText(meeting.meetingType);
		((TextView) view.findViewById(R.id.ug_course_display_group_location))
				.setText(meeting.location);
		((TextView) view.findViewById(R.id.ug_course_display_group_lecturer))
				.setText(meeting.lecturer);
		((TextView) view.findViewById(R.id.ug_course_display_group_hour_start))
				.setText(meeting.hourStart);
		((TextView) view.findViewById(R.id.ug_course_display_group_hour_end))
				.setText(meeting.hourEnd);
		((TextView) view.findViewById(R.id.ug_course_display_group_free_spaces))
				.setText(meeting.freeSpace);
		((TextView) view.findViewById(R.id.ug_course_display_group_day))
				.setText(meeting.day);
		groupsView.addView(view);
		inflater.inflate(R.layout.ug_course_display_line, groupsView);
		return view;

	}

	private void recieveCourse(Bundle bundle) {

		CourseKey key = null;
		if (bundle == null) {
			Log.e(MainActivity.DEBUG_TAG, "CANT FIND COURSE EXTRAS , exisiting");
			throw new NullPointerException();
		}
		key = (CourseKey) bundle.getSerializable(ARGUMENTS_COURSE_KEY);
		courseToView = UGDatabase.INSTANCE.getCourseByKey(key);
		if (courseToView == null) {
			Log.e(MainActivity.DEBUG_TAG,
					"CANT FIND COURSEKEY IN DB, exisiting");
			throw new NullPointerException();
		}

	}

	static class onClickGroup implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {

		}

	}

	class MeetingDisplay {

		String number = "";
		String meetingType = "";
		String lecturer = "";
		String location = "";
		String hourStart = "";
		String hourEnd = "";
		String freeSpace = "";
		String day = "";

		public MeetingDisplay() {

		}

		public MeetingDisplay(Meeting meeting, String _meetingType) {
			final SimpleDateFormat df = new SimpleDateFormat("HH:m");
			meetingType = _meetingType;
			lecturer = meeting.getLecturerName();
			location = meeting.getPlace();
			if (meeting.getStartingHour() != null)
				hourStart = df.format(meeting.getStartingHour());
			if (meeting.getEndingHour() != null)
				hourEnd = df.format(meeting.getEndingHour());
			day = meeting.getDay().toSingleLetter();
		}

		public MeetingDisplay(String number, String meetingType,
				String lecturer, String location, String hourStart,
				String hourEnd, String freeSpace, String day) {
			super();
			this.number = number;
			this.meetingType = meetingType;
			this.lecturer = lecturer;
			this.location = location;
			this.hourStart = hourStart;
			this.hourEnd = hourEnd;
			this.freeSpace = freeSpace;
			this.day = day;
		}
	}

}