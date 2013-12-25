package com.technion.coolie.ug.db;

import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd.SubType;

public class CoursesProvider extends OrmLiteSimpleContentProvider<UGDatabaseHelper> {

	@Override
	protected Class<UGDatabaseHelper> getHelperClass() {
		return UGDatabaseHelper.class;
	}

	@Override
	public boolean onCreate() {
		setMatcherController(new MatcherController().add(CourseRow.class)
				.add(SubType.DIRECTORY, "", UGContract.Course.CONTENT_URI_PATTERN_MANY)
				.add(SubType.ITEM, "#", UGContract.Course.CONTENT_URI_PATTERN_ONE));

		return true;
	}

}
