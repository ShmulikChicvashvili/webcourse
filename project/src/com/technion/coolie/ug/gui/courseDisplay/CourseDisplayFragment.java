package com.technion.coolie.ug.gui.courseDisplay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Meeting;
import com.technion.coolie.ug.model.RegistrationGroup;
import com.technion.coolie.ug.model.Semester;

//TODO kdamim
//TODO add to maakav

/**
 * activity for searching courses and finding available courses. must supply
 * course key in EXTRAS_COURSE_KEY before calling this activity/fragment.
 * 
 * 
 */
public class CourseDisplayFragment extends Fragment {

	Course chosenCourse;
	Context context;
	CourseGroupsAdapter groupAdapter;
	LinearLayout groupsView;

	public final static String ARGUMENTS_COURSE_KEY = "course";

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ug_course_screen_layout, container,
				false);
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {

		context = getActivity();
		groupsView = (LinearLayout) getActivity().findViewById(
				R.id.course_screen_groups_list);
		Course courseToView = recieveCourse(getArguments());
		updateCourseDisplay(courseToView);
		setRadioButtons();
		super.onActivityCreated(savedInstanceState);
	}

	private void setRadioButtons() {
		RadioGroup rg = (RadioGroup) getActivity().findViewById(
				R.id.course_screen_semester_radio_group);

		final RadioButton rbWinter = (RadioButton) getActivity().findViewById(
				R.id.course_screen_lastsemester);

		final RadioButton rbSpring = (RadioButton) getActivity().findViewById(
				R.id.course_screen_secondlastsemester);
		final RadioButton rbSummer = (RadioButton) getActivity().findViewById(
				R.id.course_screen_threelastsemester);

		SemesterSeason ss = UGDatabase.getInstance(context)
				.getCurrentSemester().getSs();
		if (ss == SemesterSeason.WINTER)
			rg.check(rbWinter.getId());
		if (ss == SemesterSeason.SPRING)
			rg.check(rbSpring.getId());
		if (ss == SemesterSeason.SUMMER)
			rg.check(rbSummer.getId());

		final CourseDisplayFragment fragment = this;
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (rbWinter.getId() == checkedId)
					fragment.onClickWinter(null);
				if (rbSpring.getId() == checkedId)
					fragment.onClickSpring(null);
				if (rbSummer.getId() == checkedId)
					fragment.onClickSummer(null);
			}
		});
	}

	private Course recieveCourse(final Bundle bundle) {

		CourseKey key = null;
		if (bundle == null) {
			Log.e(MainActivity.DEBUG_TAG, "CANT FIND COURSE EXTRAS , exisiting");
			throw new NullPointerException();
		}
		key = (CourseKey) bundle.getSerializable(ARGUMENTS_COURSE_KEY);
		Course course = UGDatabase.getInstance(getActivity()).getCourseByKey(
				key);
		if (course == null) {
			Log.e(MainActivity.DEBUG_TAG, "CANT FIND COURSEKEY IN DB");
			return new Course(key); // partial course display
		}
		return course;

	}

	private void updateCourseDisplay(Course courseToView) {
		final TextView notAvailTextView = (TextView) getActivity()
				.findViewById(R.id.ug_course_screen_not_available_course);
		notAvailTextView.setVisibility(View.GONE);

		groupsView.removeAllViews();
		final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",
				Locale.getDefault());

		if (courseToView.getName() == null || courseToView.getName().isEmpty())
			notAvailTextView.setVisibility(View.VISIBLE);

		final TextView nameTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_name);
		nameTextView.setText("");
		if (courseToView.getName() != null)
			nameTextView.setText(courseToView.getName());

		final TextView pointsTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_points);
		pointsTextView.setText("" + courseToView.getPoints());

		final TextView numberTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_number);
		numberTextView.setText("");
		if (courseToView.getCourseNumber() != null)
			numberTextView.setText("" + courseToView.getCourseNumber());

		final TextView facultyTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_faculty);
		facultyTextView.setText("");
		if (courseToView.getFaculty() != null)
			facultyTextView.setText(""
					+ courseToView.getFaculty().getName(context));

		final TextView descTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_description);
		descTextView.setText("");
		if (courseToView.getDescription() != null)
			descTextView.setText(courseToView.getDescription());

		final TextView examATextView = (TextView) getActivity().findViewById(
				R.id.course_screen_exam_a);
		examATextView.setText("");
		if (courseToView.getMoedA() != null)
			examATextView.setText(df.format(courseToView.getMoedA().getTime()));

		final TextView examBTextView = (TextView) getActivity().findViewById(
				R.id.course_screen_exam_b);
		examBTextView.setText("");
		if (courseToView.getMoedB() != null)
			examBTextView.setText(df.format(courseToView.getMoedB().getTime()));

		makeKdamim(courseToView);

		makeGroupsHeader();

		if (courseToView.getRegistrationGroups() != null) {
			addSeperatorLine();
			for (final RegistrationGroup group : courseToView
					.getRegistrationGroups())
				addRegistrationGroup(group);
		}

		fixEndOfGroups();
		chosenCourse = courseToView;
	}

	private void makeKdamim(Course courseToView2) {

	}

	private void addSeperatorLine() {
		final LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.ug_course_display_line_seperator, groupsView);
	}

	private void makeGroupsHeader() {

		final MeetingDisplay explanationHeader = new MeetingDisplay(
				getString(R.string.ug_course_group_id),
				getString(R.string.ug_course_group_meeting_type),
				getString(R.string.ug_course_group_lecturer),
				getString(R.string.ug_course_group_location),
				getString(R.string.ug_course_group_hour_start),
				getString(R.string.ug_course_group_hour_end),
				getString(R.string.ug_course_group_free),
				getString(R.string.ug_course_group_day));
		final View v = addMeeting(explanationHeader);
		v.setBackgroundResource(R.drawable.ug_course_label_text_container);

	}

	private void fixEndOfGroups() {
		// addMeeting(new MeetingDisplay());// empty to fix the table
	}

	private void addRegistrationGroup(final RegistrationGroup group) {

		final MeetingDisplay header = new MeetingDisplay();
		header.freeSpace = "" + group.getFreePlaces();
		header.number = "" + group.getGroupId();
		final View v = addMeeting(header);
		v.setBackgroundColor(Color.LTGRAY);
		v.setBackgroundResource(R.drawable.ug_course_group_view);

		// do all meetings
		if (group.getLectures() != null)
			for (final Meeting meeting : group.getLectures())
				addMeeting(new MeetingDisplay(
						meeting,
						getString(R.string.ug_course_group_meeting_type_lecture)));
		if (group.getTutorials() != null)
			for (final Meeting meeting : group.getTutorials())
				addMeeting(new MeetingDisplay(
						meeting,
						getString(R.string.ug_course_group_meeting_type_tutorial)));
		addSeperatorLine();
	}

	private View addMeeting(final MeetingDisplay meeting) {

		final LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(
				R.layout.ug_course_screen_group_item_row, groupsView, false);
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
		view.setBackgroundResource(R.drawable.ug_course_group_view);
		groupsView.addView(view);
		inflater.inflate(R.layout.ug_course_display_line, groupsView);
		return view;

	}

	static class onClickGroup implements android.view.View.OnClickListener {

		@Override
		public void onClick(final View v) {

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

		String[] dayLetter = { getString(R.string.ug_course_group_day_1),
				getString(R.string.ug_course_group_day_2),
				getString(R.string.ug_course_group_day_3),
				getString(R.string.ug_course_group_day_4),
				getString(R.string.ug_course_group_day_5),
				getString(R.string.ug_course_group_day_6),
				getString(R.string.ug_course_group_day_7) };

		public MeetingDisplay() {

		}

		public MeetingDisplay(final Meeting meeting, final String _meetingType) {
			final SimpleDateFormat df = new SimpleDateFormat("HH:mm",
					Locale.getDefault());
			meetingType = _meetingType;
			lecturer = meeting.getLecturerName();
			location = meeting.getPlace();
			if (meeting.getStartingHour() != null)
				hourStart = df.format(meeting.getStartingHour());
			if (meeting.getEndingHour() != null)
				hourEnd = df.format(meeting.getEndingHour());
			Calendar cal = Calendar.getInstance();
			cal.setTime(meeting.getStartingHour());
			day = dayLetter[cal.get(Calendar.DAY_OF_WEEK) - 1];
		}

		public MeetingDisplay(final String number, final String meetingType,
				final String lecturer, final String location,
				final String hourStart, final String hourEnd,
				final String freeSpace, final String day) {
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

	public void onClickWinter(View v) {
		Course course = getCourseOfSemester(SemesterSeason.WINTER);
		updateCourseDisplay(course);
	}

	public void onClickSpring(View v) {
		Course course = getCourseOfSemester(SemesterSeason.SPRING);
		updateCourseDisplay(course);
	}

	public void onClickSummer(View v) {
		Course course = getCourseOfSemester(SemesterSeason.SUMMER);
		updateCourseDisplay(course);
	}

	private Course getCourseOfSemester(SemesterSeason ss) {
		CourseKey newKey = new CourseKey(chosenCourse.getCourseNumber(),
				new Semester(UGDatabase.getInstance(context)
						.getRelevantSemester(ss).getYear(), ss));
		Course course = UGDatabase.getInstance(context).getCourseByKey(newKey);
		if (course == null)
			course = new Course(newKey);
		return course;
	}

}