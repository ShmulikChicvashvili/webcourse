package com.technion.coolie.ug.gui.searchCourses;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gui.searchCourses.SearchResultsAdapter.CourseHolder;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.tecnion.coolie.ug.utils.NavigationUtils;
import com.tecnion.coolie.ug.utils.SerializeIO;

/**
 * activity for searching courses and finding available courses.
 * 
 */
public class SearchFragment extends Fragment {

	List<String> filteredAutoCompleteList;
	final List<Course> allCourses = UGDatabase.INSTANCE.getCourses();
	List<Course> filteredCoursesList;
	FragmentActivity context;
	SearchResultsAdapter searchAdapter;
	ArrayAdapter<String> autoCompleteAdapter;
	// String lastQuery; // TODO decide on this
	SearchFilters filters;
	public final static String ARGUMENT_FILTERS_KEY = "com.technion.coolie.ug.search.argument.filter";
	public final static String ARGUMENT_QUERY_KEY = "com.technion.coolie.ug.search.argument.query";
	final static String LAST_SEARCH = "com.technion.coolie.ug.files.lastSearchQuery";
	final static String LAST_FILTER = "com.technion.coolie.ug.files.lastFilter";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.ug_search_screen_fragment,
				container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		init();
		updateAutoCompleteDisplay();
		updateCoursesResultsDisplay();
		useArguments(getArguments());
	}

	private void useArguments(Bundle bundle) {
		if (bundle != null) {
			String queryToExecute = (String) bundle
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
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		} catch (ClassNotFoundException e) {
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

		Bundle bundle = getArguments();
		if (bundle != null) {
			SearchFilters sentFilters = (SearchFilters) bundle
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
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		} catch (ClassNotFoundException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		}
		filters = new SearchFilters(UGDatabase.INSTANCE.getCurrentSemester(),
				false, Faculty.ALL_FACULTIES);

	}

	private void initFiles() {
		try {
			SerializeIO.createFile(context, LAST_SEARCH,
					(Serializable) new String());
			SerializeIO
					.createFile(context, LAST_FILTER,
							(Serializable) new SearchFilters(
									UGDatabase.INSTANCE.getCurrentSemester(),
									false, Faculty.ALL_FACULTIES));
		} catch (Exception e) {
			Log.e(MainActivity.DEBUG_TAG, "massive error!", e);
		}

	}

	private void initLayout() {
		AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.autocompletetextview);
		// autocompletetextview.requestFocus();
		// InputMethodManager imm = (InputMethodManager) getActivity()
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

		// each touch of the main layout will close any open softkeyboard!.
		OnTouchListener otl = new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(getActivity().INPUT_METHOD_SERVICE);
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

	}

	private void setInitialAdapters() {
		autoCompleteAdapter = new ArrayAdapter<String>(context,
				R.layout.ug_search_auto_complete_item_row,
				filteredAutoCompleteList);
		List<Course> lastSearch = Collections.emptyList();

		searchAdapter = new SearchResultsAdapter(context,
				new ArrayList<Course>(), new onClickResult());

	}

	/**
	 * sets the courses list with all the courses that match the search
	 * preferences! Can get the last search in this method and display it.
	 */
	private void setInitialCourseLists() {
		Log.d(MainActivity.DEBUG_TAG,
				"all courses are of size " + allCourses.size());
		filteredCoursesList = filters.filter(allCourses, "");
		filteredAutoCompleteList = coursesToNames(filteredCoursesList);

		Log.d(MainActivity.DEBUG_TAG, "initial courses are of size "
				+ filteredCoursesList.size());

	}

	private List<String> coursesToNames(List<Course> courses) {
		List<String> names = new ArrayList<String>();
		for (Course course : courses) {
			names.add(course.getName() + " " + course.getCourseNumber());
		}
		return names;

	}

	protected int searchQueryAndUpdate(String query) {

		// TODO this is heavy. use the current courseList unless the filters
		// changed.(boolean for filters changed?)
		filteredCoursesList = filters.filter(allCourses, "");
		// The auto complete is only updated by the filters, not by the query
		filteredAutoCompleteList = coursesToNames(filteredCoursesList);

		// get all the matching courses matching the query and the filters
		List<Course> filteredAndQueriedList = filters.filter(
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
		return filteredAndQueriedList.size();
	}

	/**
	 * calculates from scratch the list of matching courses, considering the
	 * filters and the passed query . updates the list of courses and the
	 * autocomplete textView according to these results.
	 * 
	 * @param query
	 */
	protected int onSearchPressed(String query) {
		int results = searchQueryAndUpdate(query);
		if (results == 1) {
			NavigationUtils.goToCourseDisplay(searchAdapter.results.get(0)
					.getCourseKey(), context);
		}
		return results;
	}

	private void updateCoursesResultsDisplay() {
		ListView listCourses = (ListView) getActivity().findViewById(
				R.id.search_list_view);
		listCourses.setAdapter(searchAdapter);
	}

	private void updateAutoCompleteDisplay() {

		AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
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
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							autocompletetextview.dismissDropDown();
							InputMethodManager imm = (InputMethodManager) getActivity()
									.getSystemService(
											getActivity().INPUT_METHOD_SERVICE);
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
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String courseString = ((TextView) parent.getChildAt(position))
						.getText().toString();

				autocompletetextview.dismissDropDown();
				autocompletetextview.setText("");

				int lastWordIdx = courseString.split(" ").length - 1;
				String courseNumber = courseString.split(" ")[lastWordIdx];
				NavigationUtils.goToCourseDisplay(new CourseKey(courseNumber,
						filters.getSemester()), context);

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
		ArrayAdapter<String> adapterFaculty = new ArrayAdapter<String>(context,
				R.layout.ug_search_spinner_item_row,
				Faculty.AE.getAllFaculties());
		ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(
				context, R.layout.ug_search_spinner_item_row, new String[] {
						SemesterSeason.WINTER.toString(),
						SemesterSeason.SPRING.toString(),
						SemesterSeason.SUMMER.toString() });
		// Specify the layout to use when the list of choices appears
		adapterFaculty
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterSemester
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerFaculty.setAdapter(adapterFaculty);
		spinnerSemester.setAdapter(adapterSemester);

		int idxDefault = adapterFaculty.getPosition(filters.getFaculty()
				.toString());
		spinnerFaculty.setSelection(idxDefault);
		spinnerFaculty.setOnItemSelectedListener(new OnItemSelectedListener() {
			int i = 0;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				String facultyString = spinnerFaculty.getSelectedItem()
						.toString();
				filters.setFaculty(Faculty.valueOf(facultyString));
				// dont invoke search on fragment start
				if (i++ > 0)
					onFiltersUpdate();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		idxDefault = adapterSemester.getPosition(filters.getSemester().getSs()
				.toString());
		spinnerSemester.setSelection(idxDefault);
		spinnerSemester.setOnItemSelectedListener(new OnItemSelectedListener() {

			int i = 0;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				String semesterString = spinnerSemester.getSelectedItem()
						.toString();
				filters.setSemester(UGDatabase.INSTANCE
						.getRelevantSemester(SemesterSeason
								.valueOf(semesterString)));

				System.out.print(filters.getSemester() + " SETTING SEMESTER");
				// dont invoke search on fragment start
				if (i++ > 0)
					onFiltersUpdate();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	protected void onFiltersUpdate() {
		AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.autocompletetextview);
		onSearchPressed(autocompletetextview.getText().toString());

	}

	class onClickResult implements OnClickListener {

		@Override
		public void onClick(final View v) {

			v.setBackgroundColor(Color.LTGRAY);

			CourseHolder holder = (CourseHolder) v.getTag();
			NavigationUtils.goToCourseDisplay(new CourseKey(holder.number
					.getText().toString(), filters.getSemester()), context);

			v.postDelayed(new Runnable() {
				public void run() {
					v.setBackgroundColor(getResources().getColor(
							R.color.ug_search_list_view_color));
				}
			}, 400);

		}
	}

	@Override
	public void onStop() {
		try {
			SerializeIO.save(context, LAST_SEARCH,
					(Serializable) ((AutoCompleteTextView) context
							.findViewById(R.id.autocompletetextview)).getText()
							.toString());
			SerializeIO.save(context, LAST_FILTER, (Serializable) filters);
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "save error ", e);
		}
		super.onStop();
	}

}
