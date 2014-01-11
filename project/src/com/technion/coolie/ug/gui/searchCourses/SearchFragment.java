package com.technion.coolie.ug.gui.searchCourses;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.actionbarsherlock.view.Window;
import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.TransparentActivity;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gui.courseDisplay.CourseDisplayFragment;
import com.technion.coolie.ug.gui.searchCourses.SearchFilters.DateRange;
import com.technion.coolie.ug.gui.searchCourses.SearchResultsAdapter.CourseHolder;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.Faculty;
import com.technion.coolie.ug.utils.SerializeIO;
import com.technion.coolie.ug.utils.UGCurrentState;

//add option to add to tracking!
//mark course if were registered to it!
/**
 * activity for searching courses and finding available courses.
 * 
 */

public class SearchFragment extends Fragment {

	List<String> filteredAutoCompleteList;
	List<Course> allCourses;
	List<Course> filteredCoursesList;
	FragmentActivity context;
	SearchResultsAdapter searchAdapter;
	ArrayAdapter<String> autoCompleteAdapter;
	String lastQuery = "";
	SearchFilters filters;
	public final static String ARGUMENT_FILTERS_KEY = "com.technion.coolie.ug.search.argument.filter";
	public final static String ARGUMENT_QUERY_KEY = "com.technion.coolie.ug.search.argument.query";
	final static String LAST_SEARCH = "com.technion.coolie.ug.files.lastSearchQuery";
	final static String LAST_FILTER = "com.technion.coolie.ug.files.lastFilter";

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = inflater.inflate(R.layout.ug_search_screen_fragment,
				container, false);
		UGCurrentState.currentOpenFragment = "GradesSheetFragment";
		return view;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		init();
		updateAutoCompleteDisplay();
		updateCoursesResultsDisplay();
		useArguments(getArguments());
	}

	private void useArguments(final Bundle bundle) {
		if (bundle != null) {
			final String queryToExecute = (String) bundle
					.getSerializable(ARGUMENT_QUERY_KEY);
			if (queryToExecute != null) {
				onSearchPressed(queryToExecute);
				((AutoCompleteTextView) context
						.findViewById(R.id.autocompletetextview))
						.setText(queryToExecute);
				return;
			}

		}

		// use last search if no bundle
		String lastSearch = "";
		try {
			lastSearch = (String) SerializeIO.load(context, LAST_SEARCH);
			searchQueryAndUpdate(lastSearch);
		} catch (final IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		} catch (final ClassNotFoundException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		}

	}

	public void init() {
		// undependent
		initFiles();
		initAutoComplete();

		// file dependent
		initFilters();

		// filter dependent
		initLayout();
		setInitialCourseLists();

		// course dependent
		setInitialAdapters();
	}

	/**
	 * requires filter file to be available to initialize the filters.
	 */
	private void initFilters() {

		final Bundle bundle = getArguments();
		if (bundle != null) {
			final SearchFilters sentFilters = (SearchFilters) bundle
					.getSerializable(ARGUMENT_FILTERS_KEY);
			if (sentFilters != null) {
				filters = sentFilters;
				return;
			}
		}
		// if no filters in bundle
		try {
			filters = (SearchFilters) SerializeIO.load(context, LAST_FILTER);
			return;
		} catch (final IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		} catch (final ClassNotFoundException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		}
		// if no filters in memory
		filters = new SearchFilters(UGDatabase.getInstance(getActivity())
				.getCurrentSemester(), false, Faculty.ALL_FACULTIES);

	}

	private void initFiles() {
		try {
			SerializeIO.createFile(context, LAST_SEARCH, new String());
			SerializeIO.createFile(context, LAST_FILTER, new SearchFilters(
					UGDatabase.getInstance(getActivity()).getCurrentSemester(),
					false, Faculty.ALL_FACULTIES));
		} catch (final Exception e) {
			Log.e(MainActivity.DEBUG_TAG, "massive error!", e);
		}

	}

	private void initLayout() {
		// AutoCompleteTextView autocompletetextview = (AutoCompleteTextView)
		// getActivity()
		// .findViewById(R.id.autocompletetextview);

		// each touch of the main layout will close any open softkeyboard!.
		final OnTouchListener otl = new OnTouchListener() {

			@Override
			public boolean onTouch(final View v, final MotionEvent event) {
				getActivity();
				final InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), 0);
				return false; // necessary for not consuming the event
			}
		};

		getActivity().findViewById(R.id.search_screen_main).setOnTouchListener(
				otl);
		getActivity().findViewById(R.id.search_list_view).setOnTouchListener(
				otl);
		if (getActivity().findViewById(R.id.main_screen_layout_below_bar) != null)
			getActivity().findViewById(R.id.main_screen_layout_below_bar)
					.setOnTouchListener(otl);

		initSpinnerLayout();
		initAdvancedButton();

	}

	private void initAdvancedButton() {
		Button advancedButton = (Button) context
				.findViewById(R.id.ug_search_screen_advanced_button);
		advancedButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openAdvancedFilterDialog(filters);

			}

			private void openAdvancedFilterDialog(SearchFilters filters) {
				ExpandTextDialog dialog = new ExpandTextDialog(context,
						filters, 5);
				dialog.show();
			}

		});

	}

	private void setInitialAdapters() {
		autoCompleteAdapter = new ArrayAdapter<String>(context,
				R.layout.ug_search_auto_complete_item_row,
				filteredAutoCompleteList);
		// final List<Course> lastSearch = Collections.emptyList();

		searchAdapter = new SearchResultsAdapter(context,
				new ArrayList<Course>(), new onClickResult());

	}

	/**
	 * sets the courses list with all the courses that match the search
	 * preferences! Can get the last search in this method and display it.
	 */
	private void setInitialCourseLists() {
		allCourses = UGDatabase.getInstance(getActivity()).getCourses();
		Log.d(MainActivity.DEBUG_TAG,
				"all courses are of size " + allCourses.size());
		filteredCoursesList = filters.filter(allCourses, "");
		filteredAutoCompleteList = coursesToNames(filteredCoursesList);

		Log.d(MainActivity.DEBUG_TAG, "initial courses are of size "
				+ filteredCoursesList.size());

	}

	private List<String> coursesToNames(final List<Course> courses) {
		final List<String> names = new ArrayList<String>();
		for (final Course course : courses)
			names.add(course.getName() + " " + course.getCourseNumber());
		return names;

	}

	protected int searchQueryAndUpdate(final String query) {

		// TODO this is heavy. use the current courseList unless the filters
		// changed.(boolean for filters changed?)
		filteredCoursesList = filters.filter(allCourses, "");
		// The auto complete is only updated by the filters, not by the query
		filteredAutoCompleteList = coursesToNames(filteredCoursesList);

		// get all the matching courses matching the query and the filters
		final List<Course> filteredAndQueriedList = filters.filter(
				filteredCoursesList, query);

		Log.d(MainActivity.DEBUG_TAG, "results found of size "
				+ filteredAndQueriedList.size());

		// set the adapters with the matching courses
		searchAdapter = new SearchResultsAdapter(context,
				filteredAndQueriedList, new onClickResult());
		autoCompleteAdapter = new ArrayAdapter<String>(context,
				R.layout.ug_search_auto_complete_item_row,
				filteredAutoCompleteList);

		updateCoursesResultsDisplay();
		updateAutoCompleteDisplay();

		lastQuery = query;
		return filteredAndQueriedList.size();
	}

	/**
	 * calculates from scratch the list of matching courses, considering the
	 * filters and the passed query . updates the list of courses and the
	 * autocomplete textView according to these results.
	 * 
	 * @param query
	 */
	protected int onSearchPressed(final String query) {
		final int results = searchQueryAndUpdate(query);
		if (results == 1)
			goToCourseDisplay(searchAdapter.results.get(0).getCourseKey(),
					context);
		return results;
	}

	private void updateCoursesResultsDisplay() {
		final ListView listCourses = (ListView) getActivity().findViewById(
				R.id.search_list_view);
		listCourses.setAdapter(searchAdapter);
	}

	private void updateAutoCompleteDisplay() {

		final AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.autocompletetextview);
		autocompletetextview.setAdapter(autoCompleteAdapter);

	}

	/**
	 * sets the auto complete settings
	 */
	public void initAutoComplete() {
		final AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.autocompletetextview);
		autocompletetextview.setThreshold(1);
		autocompletetextview.setTextColor(getResources().getColor(
				R.color.abs__background_holo_dark));

		// int width = getActivity().getWindowManager().getDefaultDisplay()
		// .getWidth();
		// autocompletetextview.setDropDownWidth(width);

		autocompletetextview
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(final TextView v,
							final int actionId, final KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							autocompletetextview.dismissDropDown();
							getActivity();
							final InputMethodManager imm = (InputMethodManager) getActivity()
									.getSystemService(
											Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(getActivity()
									.getCurrentFocus().getWindowToken(), 0);
							onSearchPressed(v.getText().toString());
							return true;
						}
						return false;
					}
				});

		autocompletetextview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {

				final String courseString = ((TextView) parent
						.getChildAt(position)).getText().toString();

				autocompletetextview.dismissDropDown();
				autocompletetextview.setText("");

				final int lastWordIdx = courseString.split(" ").length - 1;
				final String courseNumber = courseString.split(" ")[lastWordIdx];
				goToCourseDisplay(
						new CourseKey(courseNumber, filters.getSemester()),
						context);

			}
		});
	}

	private void initSpinnerLayout() {
		final Spinner spinnerFaculty = (Spinner) getActivity().findViewById(
				R.id.ug_search_faculty_spinner);
		final Spinner spinnerSemester = (Spinner) getActivity().findViewById(
				R.id.ug_search_semester_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		final ArrayAdapter<String> adapterFaculty = new ArrayAdapter<String>(
				context, R.layout.ug_search_spinner_item_row,
				Faculty.AE.getAllFaculties(context));
		final ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(
				context, R.layout.ug_search_spinner_item_row, new String[] {
						SemesterSeason.WINTER.getName(context),
						SemesterSeason.SPRING.getName(context),
						SemesterSeason.SUMMER.getName(context) });
		// Specify the layout to use when the list of choices appears
		adapterFaculty
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterSemester
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerFaculty.setAdapter(adapterFaculty);
		spinnerSemester.setAdapter(adapterSemester);

		int idxDefault = adapterFaculty.getPosition(filters.getFaculty()
				.getName(context));
		spinnerFaculty.setSelection(idxDefault);
		final Context mcontext = this.context;
		spinnerFaculty.setOnItemSelectedListener(new OnItemSelectedListener() {
			int i = 0;

			@Override
			public void onItemSelected(final AdapterView<?> parent,
					final View view, final int position, final long id) {

				final String facultyString = spinnerFaculty.getSelectedItem()
						.toString();
				filters.setFaculty(Faculty.AE.valueByName(facultyString,
						mcontext));
				// dont invoke search on fragment start
				if (i++ > 0)
					onFiltersUpdate();

			}

			@Override
			public void onNothingSelected(final AdapterView<?> parent) {

			}
		});
		idxDefault = adapterSemester.getPosition(filters.getSemester().getSs()
				.getName(mcontext));
		spinnerSemester.setSelection(idxDefault);
		spinnerSemester.setOnItemSelectedListener(new OnItemSelectedListener() {

			int i = 0;

			@Override
			public void onItemSelected(final AdapterView<?> parent,
					final View view, final int position, final long id) {

				final String semesterString = spinnerSemester.getSelectedItem()
						.toString();
				filters.setSemester(UGDatabase.getInstance(getActivity())
						.getRelevantSemester(
								SemesterSeason.valueByName(semesterString,
										mcontext)));

				System.out.print(filters.getSemester() + " SETTING SEMESTER");
				// dont invoke search on fragment start
				if (i++ > 0)
					onFiltersUpdate();
			}

			@Override
			public void onNothingSelected(final AdapterView<?> parent) {

			}
		});

	}

	protected void onFiltersUpdate() {
		final AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.autocompletetextview);
		onSearchPressed(autocompletetextview.getText().toString());

	}

	class onClickResult implements OnClickListener {

		@Override
		public void onClick(final View v) {

			v.setBackgroundColor(Color.LTGRAY);

			final CourseHolder holder = (CourseHolder) v.getTag();
			v.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (isAdded())
						v.setBackgroundColor(getResources().getColor(
								R.color.ug_search_list_view_color));
				}
			}, 100);

			goToCourseDisplay(new CourseKey(holder.number.getText().toString(),
					filters.getSemester()), context);

		}
	}

	@Override
	public void onStop() {
		try {
			SerializeIO.save(context, LAST_SEARCH, lastQuery);
			SerializeIO.save(context, LAST_FILTER, filters);
		} catch (final IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "save error ", e);
		}
		super.onStop();
	}

	/**
	 * opens courseDisplay activity with the specified course key
	 * 
	 * @param key
	 * @param context
	 */
	public void goToCourseDisplay(final CourseKey key,
			final FragmentActivity activity) {

		final Bundle bundle = new Bundle();
		bundle.putSerializable(CourseDisplayFragment.ARGUMENTS_COURSE_KEY, key);

		activity.getResources().getConfiguration();
		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			final int containerId = R.id.detail_container;
			final Fragment newFragment = new CourseDisplayFragment();
			newFragment.setArguments(bundle);
			final FragmentTransaction transaction = activity
					.getSupportFragmentManager().beginTransaction();
			transaction.replace(containerId, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();

		} else {
			final Intent intent = new Intent(activity,
					TransparentActivity.class);
			bundle.putString("key", CourseDisplayFragment.class.toString());
			intent.putExtras(bundle);
			activity.startActivity(intent);
			return;

		}

	}

	public void goToSearchDisplay(final String query,
			final SearchFilters filters, final FragmentActivity activity) {
		final Bundle bundle = new Bundle();
		final Intent intent = new Intent(activity, TransparentActivity.class);
		bundle.putString("key", SearchFragment.class.toString());
		bundle.putSerializable(SearchFragment.ARGUMENT_QUERY_KEY, query);
		bundle.putSerializable(SearchFragment.ARGUMENT_FILTERS_KEY, filters);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		return;

	}

	private final String inputFormat = "HH:mm";
	private final SimpleDateFormat inputParser = new SimpleDateFormat(
			inputFormat, Locale.US);
	private final String dateInputFormat = "dd/MM/yyyy";
	private final SimpleDateFormat dateInputParser = new SimpleDateFormat(
			dateInputFormat, Locale.US);

	class ExpandTextDialog extends Dialog {

		String[] dayLetter = { getString(R.string.ug_course_group_day_1),
				getString(R.string.ug_course_group_day_2),
				getString(R.string.ug_course_group_day_3),
				getString(R.string.ug_course_group_day_4),
				getString(R.string.ug_course_group_day_5),
				getString(R.string.ug_course_group_day_6),
				getString(R.string.ug_course_group_day_7) };

		protected ExpandTextDialog(final Context context,
				final SearchFilters filters, final int offsetFromTop) {
			super(context);

			this.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
			final WindowManager.LayoutParams wmlp = this.getWindow()
					.getAttributes();
			wmlp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
			// wmlp.y = coords[1];
			setContentView(R.layout.ug_search_screen_advanced_dialog);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

			final Button mPickDate = (Button) findViewById(R.id.myDatePickerButton);
			mPickDate.setOnClickListener(new onClickDateChoose());

			final Button mPickDateEnd = (Button) findViewById(R.id.myDatePickerButtonEnd);
			mPickDateEnd.setOnClickListener(new onClickDateChoose());

			final Button mPickTime = (Button) findViewById(R.id.myDatePickerButtonTime);
			mPickTime.setOnClickListener(new onClickTimeChoose());

			final Button mPickTimeEnd = (Button) findViewById(R.id.myDatePickerButtonEndTime);
			mPickTimeEnd.setOnClickListener(new onClickTimeChoose());

			final Spinner daySpinner = (Spinner) findViewById(R.id.myDatePickerSpinner);
			initSpinner(daySpinner);

			final Button save = (Button) findViewById(R.id.myDatePickerSaveSettings);
			final Dialog thisDialog = this;
			save.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					filters.setExamADateRange(getDatePairResult(mPickDate,
							mPickDateEnd, daySpinner, dateInputParser,
							R.id.chec_kbox_date));
					filters.setMeetingDateRange(getDatePairResult(mPickTime,
							mPickTimeEnd, daySpinner, inputParser,
							R.id.chec_kbox_time));
					thisDialog.dismiss();
					onFiltersUpdate();
				}

			});

			initCheckBox(filters, mPickDate, mPickDateEnd, dateInputParser,
					R.id.chec_kbox_date, filters.getExamARange());
			initCheckBox(filters, mPickTime, mPickTimeEnd, inputParser,
					R.id.chec_kbox_time, filters.getMeetingRange());

		}

		private void initSpinner(Spinner spinner) {
			final ArrayAdapter<String> adapterDayInWeek = new ArrayAdapter<String>(
					context, R.layout.ug_search_spinner_item_row, dayLetter);
			// Specify the layout to use when the list of choices appears
			adapterDayInWeek
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// Apply the adapter to the spinner
			spinner.setAdapter(adapterDayInWeek);

			if (filters.getMeetingRange() != null) {
				int idxDefault = adapterDayInWeek.getPosition(filters
						.getMeetingRange().dayInWeek);
				spinner.setSelection(idxDefault);
			}

		}

		// /**
		// * this is called on clicking the save button in the search dialog.
		// sets
		// * the filters with the chosen filters.
		// *
		// * @param newFilter
		// */
		// private void onCloseAdvancedButton(SearchFilters newFilter) {
		//
		// }

		private DateRange getDatePairResult(Button mPickDate,
				Button mPickDateEnd, Spinner spinner,
				SimpleDateFormat dateInputParser, int id) {
			CheckBox checkDate = (CheckBox) findViewById(id);

			if (!checkDate.isChecked())
				return null;

			try {
				return new DateRange(dateInputParser.parse((String) mPickDate
						.getText()),
						dateInputParser.parse((String) mPickDateEnd.getText()),
						spinner.getSelectedItem().toString());
			} catch (ParseException e) {
				return null;
			}

		}

		private void initCheckBox(final SearchFilters filters,
				final Button mPickDate, final Button mPickDateEnd,
				SimpleDateFormat parser, int id, DateRange range) {
			CheckBox checkDate = (CheckBox) findViewById(id);
			checkDate.setChecked((range != null));
			checkDate.setOnCheckedChangeListener(new onCheckBox(mPickDate,
					mPickDateEnd));
			mPickDate.setEnabled(false);
			mPickDateEnd.setEnabled(false);
			if (checkDate.isChecked()) {
				// put the dates in the buttons
				mPickDate.setText(parser.format(range.first));
				mPickDate.setEnabled(true);
				mPickDateEnd.setText(parser.format(range.second));
				mPickDateEnd.setEnabled(true);

			}
		}
	}

	class onClickTimeChoose implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			TimePickerDialog dpd = new TimePickerDialog(context,
					new TimePickerToString(v), 8, 30, true);
			dpd.show();
		}

		class TimePickerToString implements TimePickerDialog.OnTimeSetListener {
			private Button button;

			public TimePickerToString(View v) {
				this.button = (Button) v;
			}

			@Override
			public void onTimeSet(TimePicker arg0, int hour, int minutes) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, minutes);
				// cal.set(Calendar.MONTH, monthOfYear);
				button.setText(inputParser.format(cal.getTime()));
			}

		}

	}

	class onClickDateChoose implements View.OnClickListener {
		public void onClick(View v) {
			Calendar cal = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(context,
					new DatePickerToString(v), cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			dpd.show();
		}

		class DatePickerToString implements DatePickerDialog.OnDateSetListener {
			private Button button;

			// private static final String inputFormat = "HH:mm";

			public DatePickerToString(View v) {
				this.button = (Button) v;
			}

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				cal.set(Calendar.MONTH, monthOfYear);
				button.setText(dateInputParser.format(cal.getTime()));
			}
		}

	}

	class onCheckBox implements OnCheckedChangeListener {

		private View v;
		private View v2;

		public onCheckBox(View v, View v2) {
			this.v = v;
			this.v2 = v2;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				v.setEnabled(true);
				v2.setEnabled(true);
			} else {
				v.setEnabled(false);
				v2.setEnabled(false);
			}
		}
	}

}
