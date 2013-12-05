package com.technion.coolie.ug.gui.searchCourses;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;
import com.technion.coolie.ug.MainActivity;
import com.technion.coolie.ug.Enums.Faculty;
import com.technion.coolie.ug.Enums.SemesterSeason;
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.gui.searchCourses.SearchResultsAdapter.CourseHolder;
import com.technion.coolie.ug.model.Course;
import com.technion.coolie.ug.model.CourseKey;
import com.technion.coolie.ug.model.SearchFilters;
import com.technion.coolie.ug.model.Semester;
import com.tecnion.coolie.ug.utils.NavigationUtils;
import com.tecnion.coolie.ug.utils.SerializeIO;

/**
 * activity for searching courses and finding available courses.
 * 
 * 
 */
public class SearchActivity extends CoolieActivity {

	List<String> courseNameList;
	List<Course> courseList;
	Context context;
	SearchResultsAdapter searchAdapter;
	ArrayAdapter<String> autoCompleteAdapter;
	String lastQuery; // TODO decide on this
	SearchFilters filters;
	final static String LAST_SEARCH_FILE = "com.technion.coolie.ug.files.lastSearch";
	final static String LAST_FILTER = "com.technion.coolie.ug.files.lastFilter";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_screen_layout);
		context = this;

		init();
		updateAutoCompleteDisplay();
		updateCoursesResultsDisplay();
	}

	public void init() {
		// undependent
		initFiles();
		initLayout();
		initAutoComplete();

		// file dependent
		initFilters();

		// filter dependent
		setInitialCourseLists();

		// course dependent
		setInitialAdapters();
	}

	/**
	 * requires filter file to be available to initialize the filters.
	 */
	private void initFilters() {

		// TODO get the saved filter file to initialize

		filters = new SearchFilters(new Semester(2013, SemesterSeason.WINTER),
				false, Faculty.ALL_FACULTIES);

	}

	private void initFiles() {
		try {
			SerializeIO.createFile(this, LAST_SEARCH_FILE,
					(Serializable) Collections.EMPTY_LIST);
		} catch (Exception e) {
			Log.e(MainActivity.DEBUG_TAG, "massive error!", e);
		}

	}

	private void initLayout() {
		AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
		autocompletetextview.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

	}

	private void setInitialAdapters() {
		autoCompleteAdapter = new ArrayAdapter<String>(this,
				R.layout.auto_complete_item_row, courseNameList);

		List<Course> lastSearch = Collections.emptyList();

		try {
			lastSearch = (List<Course>) SerializeIO
					.load(this, LAST_SEARCH_FILE);
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		} catch (ClassNotFoundException e) {
			Log.e(MainActivity.DEBUG_TAG, "load error ", e);
		}

		searchAdapter = new SearchResultsAdapter(this, lastSearch,
				new onClickResult());

	}

	/**
	 * sets the courses list with all the courses that match the search
	 * preferences! Can get the last search in this method and display it.
	 */
	private void setInitialCourseLists() {
		courseList = filters.filter(UGDatabase.INSTANCE.getCourses(), "");
		courseNameList = coursesToNames(courseList);

		Log.d(MainActivity.DEBUG_TAG, "initial courses are of size "
				+ courseList.size());

	}

	private List<String> coursesToNames(List<Course> courses) {
		List<String> names = new ArrayList<String>();
		for (Course course : courses) {
			names.add(course.getName() + " " + course.getCourseNumber());
		}
		return names;

	}

	private void onSearchPressed(String query) {
		lastQuery = query;
		// get all the matching courses matching the query and the filters
		List<Course> queryList = filters.filter(courseList, query);

		Log.d(MainActivity.DEBUG_TAG,
				"results found of size " + queryList.size());

		// set the adapter with the matching courses
		searchAdapter = new SearchResultsAdapter(this, queryList,
				new onClickResult());

		updateCoursesResultsDisplay();

		if (queryList.size() == 1) {
			NavigationUtils.goToCourseDisplay(queryList.get(0).getCourseKey(),
					context);
		}
		// if (queryList.size() == 0)..... TODO do something when no results

	}

	private void updateCoursesResultsDisplay() {
		ListView listCourses = (ListView) findViewById(R.id.search_list_view);
		listCourses.setAdapter(searchAdapter);
	}

	private void updateAutoCompleteDisplay() {

		AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
		autocompletetextview.setAdapter(autoCompleteAdapter);

	}

	/**
	 * sets the auto complete settings
	 */
	public void initAutoComplete() {
		final AutoCompleteTextView autocompletetextview = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
		autocompletetextview.setThreshold(1);

		autocompletetextview
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							Toast.makeText(context, "ON SEARCH handler",
									Toast.LENGTH_SHORT).show();
							autocompletetextview.dismissDropDown();
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
				onSearchPressed(courseString);

				Toast.makeText(context,
						"course " + courseString + " was pressed",
						Toast.LENGTH_LONG).show();

			}
		});
	}

	class onClickResult implements OnClickListener {

		String name;
		String courseNumber; // TODO use these

		@Override
		public void onClick(View v) {
			Toast.makeText(v.getContext(), "view   was pressed",
					Toast.LENGTH_LONG).show();
			CourseHolder holder = (CourseHolder) v.getTag(); // Check this
																// holder theory
																// TODO
			NavigationUtils.goToCourseDisplay(new CourseKey(holder.number
					.getText().toString(), filters.getSemester()), context);

		}
	}

	@Override
	protected void onDestroy() {
		try {
			SerializeIO.save(this, LAST_SEARCH_FILE,
					(Serializable) searchAdapter.results); // save lastQuery?
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "save error ", e);
		}
		super.onDestroy();
	}
}
