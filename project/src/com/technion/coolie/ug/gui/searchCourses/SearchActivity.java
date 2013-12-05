package com.technion.coolie.ug.gui.searchCourses;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import com.technion.coolie.ug.db.UGDatabase;
import com.technion.coolie.ug.model.Course;
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
	final static String LAST_SEARCH_FILE = "com.technion.coolie.ug.files.lastSearch";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_screen_layout);
		context = this;

		initFiles();
		initLayout();
		initAutoComplete();

		// initPreferences(); TODO

		setInitialCourseList();
		setInitialAdapters();

		updateAutoCompleteDisplay(); // update to all the courses matching the
										// preferences.
		updateCoursesResultsDisplay();

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
				R.layout.ug_auto_complete_item_row, courseNameList);
		// TODO initialize to the last search.
		try {
			searchAdapter = new SearchResultsAdapter(this,
					(List<Course>) SerializeIO.load(this, LAST_SEARCH_FILE),
					new onClickResult());
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "error ", e);
		} catch (ClassNotFoundException e) {
			Log.e(MainActivity.DEBUG_TAG, "error ", e);
		}

	}

	/**
	 * sets the courses list with all the courses that match the search
	 * preferences! Can get the last search in this method and display it.
	 */
	private void setInitialCourseList() {
		// TODO get all the courses names that match the preferences
		courseNameList = UGDatabase.INSTANCE.getCoursesNames();
		// TODO get all the courses that match the pre-set preferences
		courseList = UGDatabase.INSTANCE.getCourses();
	}

	private void onSearchPressed(String query) {
		// get from array all the matching courses matching query
		List<Course> queryList = new ArrayList<Course>(courseList);
		filterIncludingString(queryList, query);

		Log.d(MainActivity.DEBUG_TAG,
				"results found of size " + queryList.size());

		// set the adapter with the courses
		searchAdapter = new SearchResultsAdapter(this, queryList,
				new onClickResult());
		updateCoursesResultsDisplay();

		// TODO if(queryList.size()==1)
		// goToCourse(queryList.get(0));

		// TODO if(queryList.size()==0)
		// emphasize that there are no results

	}

	private void filterIncludingString(List<Course> list, String query) {

		Iterator<Course> iterator = list.iterator();
		while (iterator.hasNext()) {
			Course course = iterator.next();
			if (isSubstring(query, course.getName() + course.getPoints()))
				iterator.remove();
		}
	}

	private boolean isSubstring(String query, String string) {
		return string.indexOf(query) == -1;
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

	static class onClickResult implements OnClickListener {

		String name;
		String courseNumber; // TODO use these

		@Override
		public void onClick(View v) {
			Toast.makeText(v.getContext(), "view   was pressed",
					Toast.LENGTH_LONG).show();

		}
	}

	@Override
	protected void onDestroy() {
		try {
			SerializeIO.save(this, LAST_SEARCH_FILE,
					(Serializable) searchAdapter.results);
		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_TAG, "error ", e);
		}
		super.onDestroy();
	}
}
